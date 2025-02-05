package kr.allcll.seatfinder.basket;

import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsEachSubject;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
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
        List<BasketsEachSubject> result = subjects.stream()
            .map(subject -> {
                List<Basket> basketsBySubject = basketRepository.findBySubjectId(subject.getId());
                return BasketsEachSubject.from(subject, basketsBySubject);
            })
            .toList();
        return new BasketsResponse(result);
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
}
