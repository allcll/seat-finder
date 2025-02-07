package kr.allcll.seatfinder.star;

import java.util.List;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StarService {

    private static final int MAX_PIN_NUMBER = 5;

    private final StarRepository starRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void addStarOnSubject(Long subjectId, String token) {
        List<Star> starsByToken = starRepository.findAllByToken(token);
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
        validateCanAddStar(starsByToken, subject, token);
        starRepository.save(new Star(token, subject));
    }

    private void validateCanAddStar(List<Star> userStars, Subject subject, String token) {
        if (userStars.size() >= MAX_PIN_NUMBER) {
            throw new AllcllException(AllcllErrorCode.STAR_LIMIT_EXCEEDED, MAX_PIN_NUMBER);
        }
        if (starRepository.existsBySubjectAndToken(subject, token)) {
            throw new AllcllException(AllcllErrorCode.DUPLICATE_STAR, subject.getCuriNm());
        }
    }
}
