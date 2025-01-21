package kr.allcll.seatfinder.pin;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PinService {

    private final PinRepository pinRepository;
    private final SubjectRepository subjectRepository;

    public void addPinAtSubject(Long subjectId, String userToken) {
        List<Pin> userPins = pinRepository.findAllByTokenId(userToken);
        validatePinCount(userPins);
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목 입니다."));
        pinRepository.save(new Pin(userToken, subject));
    }

    private void validatePinCount(List<Pin> userPins) {
        if (userPins.size() >= 5) {
            throw new IllegalArgumentException("이미 5개의 핀을 등록했습니다.");
        }
    }
}
