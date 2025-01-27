package kr.allcll.seatfinder.pin;

import java.util.List;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.exception.ExceptionMessage;
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
            .orElseThrow(() -> AllcllException.from(ExceptionMessage.NOT_EXIST_SUBJECT));
        validateCanAddPin(userPins, subject, token);
        pinRepository.save(new Pin(token, subject));
    }

    private void validateCanAddPin(List<Pin> userPins, Subject subject, String token) {
        if (userPins.size() >= MAX_PIN_NUMBER) {
            throw AllcllException.of(MAX_PIN_NUMBER, ExceptionMessage.MAX_PIN_EXCEPTION);
        }
        if (pinRepository.findBySubjectAndToken(subject, token).isPresent()) {
            throw AllcllException.from(ExceptionMessage.EXIST_PIN_EXCEPTION);
        }
    }

    @Transactional
    public void deletePinOnSubject(Long subjectId, String token) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> AllcllException.from(ExceptionMessage.NOT_EXIST_SUBJECT));
        Pin pin = pinRepository.findBySubjectAndToken(subject, token)
            .orElseThrow(() -> AllcllException.from(ExceptionMessage.PIN_AND_SUBJECT_NOT_MATCH));
        pinRepository.deleteById(pin.getId());
    }
}
