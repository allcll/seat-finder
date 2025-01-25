package kr.allcll.seatfinder.pin;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PinService {

    private static final int MAX_PIN_NUMBER = 5;

    private final PinRepository pinRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void addPinOnSubject(Long subjectId, String token) {
        List<Pin> userPins = pinRepository.findAllByToken(token);
        validatePinsCount(userPins);
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목 입니다."));
        pinRepository.save(new Pin(token, subject));
    }

    private void validatePinsCount(List<Pin> userPins) {
        if (userPins.size() >= MAX_PIN_NUMBER) {
            throw new IllegalArgumentException("이미 " + MAX_PIN_NUMBER + "개의 핀을 등록했습니다.");
        }
    }
}
