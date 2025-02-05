package kr.allcll.seatfinder.basket;

import java.util.ArrayList;
import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsEachSubject;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import kr.allcll.seatfinder.basket.dto.SubjectDepartmentRegisters;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import kr.allcll.seatfinder.subject.SubjectSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final SubjectRepository subjectRepository;

    public BasketsResponse findBasketsByCondition(
        String departmentCode,
        String professorName,
        String subjectName
    ) {
        Specification<Subject> condition = getCondition(departmentCode, professorName, subjectName);
        List<Subject> subjects = subjectRepository.findAll(condition);
        List<BasketsEachSubject> result = getBasketsEachSubject(subjects);
        return new BasketsResponse(result);
    }

    private List<BasketsEachSubject> getBasketsEachSubject(List<Subject> subjects) {
        List<BasketsEachSubject> result = new ArrayList<>();
        for (Subject subject : subjects) {
            List<Basket> baskets = basketRepository.findBySubjectId(subject.getId());
            if (baskets.isEmpty()) {
                result.add(BasketsEachSubject.fromEmptyBasket(subject));
                continue;
            }
            result.add(BasketsEachSubject.from(subject, baskets));
        }
        return result;
    }

    private Specification<Subject> getCondition(
        String departmentCode,
        String professorName,
        String subjectName
    ) {
        return Specification.where(
            SubjectSpecifications.hasDepartmentCode(departmentCode)
                .and(SubjectSpecifications.hasProfessorName(professorName))
                .and(SubjectSpecifications.hasSubjectName(subjectName))
        );
    }

    public SubjectDepartmentRegisters getEachSubjectBaskets(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
        List<Basket> baskets = basketRepository.findBySubjectId(subject.getId());
        return SubjectDepartmentRegisters.from(baskets);
    }
}
