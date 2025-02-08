package kr.allcll.seatfinder.pin;

import java.util.ArrayList;
import java.util.List;
import kr.allcll.seatfinder.ThreadLocalHolder;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.pin.dto.PinSeatsResponse;
import kr.allcll.seatfinder.pin.dto.SubjectIdResponse;
import kr.allcll.seatfinder.pin.dto.SubjectIdsResponse;
import kr.allcll.seatfinder.seat.Seat;
import kr.allcll.seatfinder.seat.SeatStorage;
import kr.allcll.seatfinder.sse.SseService;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PinService {

    private static final int MAX_PIN_NUMBER = 5;
    private static final String PIN_EVENT_NAME = "사용자의 핀 여석 정보";

    private final PinRepository pinRepository;
    private final SubjectRepository subjectRepository;
    private final SeatStorage seatStorage;
    private final SseService sseService;

    @Transactional
    public void addPinOnSubject(Long subjectId, String token) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
        validateCanAddPin(subject, token);
        pinRepository.save(new Pin(token, subject));
    }

    private void validateCanAddPin(Subject subject, String token) {
        Long pinCount = pinRepository.countAllByToken(token);
        if (pinCount >= MAX_PIN_NUMBER) {
            throw new AllcllException(AllcllErrorCode.PIN_LIMIT_EXCEEDED, MAX_PIN_NUMBER);
        }
        if (pinRepository.existsBySubjectAndToken(subject, token)) {
            throw new AllcllException(AllcllErrorCode.DUPLICATE_PIN, subject.getCuriNm());
        }
    }

    @Transactional
    public void deletePinOnSubject(Long subjectId, String token) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
        Pin pin = pinRepository.findBySubjectAndToken(subject, token)
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.PIN_SUBJECT_MISMATCH));
        pinRepository.deleteById(pin.getId());
    }

    public SubjectIdsResponse retrievePins(String token) {
        List<Pin> pins = pinRepository.findAllByToken(token);
        return new SubjectIdsResponse(pins.stream()
            .map(pin -> new SubjectIdResponse(pin.getSubject().getId()))
            .toList());
    }

    @Scheduled(fixedRate = 1000)
    public void sendPinSeatsInformation() {
        List<Pin> allByToken = pinRepository.findAllByToken(ThreadLocalHolder.SHARED_TOKEN.get());
        List<Subject> result = new ArrayList<>();
        for (Pin pin : allByToken) {
            result.add(pin.getSubject());
        }
        List<Seat> pinSeats = seatStorage.getPinSeats(result);
        sseService.propagate(PIN_EVENT_NAME, PinSeatsResponse.from(pinSeats));
    }
}
