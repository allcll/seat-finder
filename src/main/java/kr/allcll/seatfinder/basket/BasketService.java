package kr.allcll.seatfinder.basket;

import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import kr.allcll.seatfinder.basket.dto.EachSubjectBasket;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final SubjectRepository subjectRepository;

    public BasketsResponse getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        List<EachSubjectBasket> result = subjects.stream()
            .map(subject -> {
                List<Basket> basketsBySubject = basketRepository.findBySubjectId(subject.getId());
                return EachSubjectBasket.from(subject, basketsBySubject);
            })
            .toList();
        return new BasketsResponse(result);
    }
}
