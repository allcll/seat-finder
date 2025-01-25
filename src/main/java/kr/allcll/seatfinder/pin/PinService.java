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
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목 입니다."));
        validateCanAddPin(userPins, subject, token);
        pinRepository.save(new Pin(token, subject));
    }

    private void validateCanAddPin(List<Pin> userPins, Subject subject, String token) {
        if (userPins.size() >= MAX_PIN_NUMBER) {
            throw new IllegalArgumentException("이미 " + MAX_PIN_NUMBER + "개의 핀을 등록했습니다.");
        }
        if (pinRepository.findBySubjectAndToken(subject, token).isPresent()) {
            throw new IllegalArgumentException("이미 핀 등록된 과목 입니다.");
        }
    }

    @Transactional
    public void deletePinOnSubject(Long subjectId, String token) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목 입니다."));
        Pin pin = pinRepository.findBySubjectAndToken(subject, token)
            .orElseThrow(() -> new IllegalArgumentException("핀에 등록된 과목이 아닙니다."));
        pinRepository.deleteById(pin.getId());
    }
}
