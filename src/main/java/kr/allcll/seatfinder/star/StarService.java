package kr.allcll.seatfinder.star;

import java.util.List;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.pin.dto.SubjectIdResponse;
import kr.allcll.seatfinder.pin.dto.SubjectIdsResponse;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StarService {

    private static final int MAX_STAR_NUMBER = 10;

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
        if (userStars.size() >= MAX_STAR_NUMBER) {
            throw new AllcllException(AllcllErrorCode.STAR_LIMIT_EXCEEDED, MAX_STAR_NUMBER);
        }
        if (starRepository.existsBySubjectAndToken(subject, token)) {
            throw new AllcllException(AllcllErrorCode.DUPLICATE_STAR, subject.getCuriNm());
        }
    }

    @Transactional
    public void deleteStarOnSubject(Long subjectId, String token) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
        Star star = starRepository.findBySubjectAndToken(subject, token)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.STAR_SUBJECT_MISMATCH));
        starRepository.deleteById(star.getId());
    }

    public SubjectIdsResponse retrieveStars(String token) {
        List<Star> stars = starRepository.findAllByToken(token);
        return new SubjectIdsResponse(stars.stream()
            .map(star -> new SubjectIdResponse(star.getSubject().getId()))
            .toList());
    }
}
