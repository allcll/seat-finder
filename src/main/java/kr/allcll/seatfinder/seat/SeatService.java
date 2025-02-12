package kr.allcll.seatfinder.seat;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import kr.allcll.seatfinder.pin.Pin;
import kr.allcll.seatfinder.pin.PinRepository;
import kr.allcll.seatfinder.pin.dto.PinSeatsResponse;
import kr.allcll.seatfinder.seat.dto.SeatsResponse;
import kr.allcll.seatfinder.sse.SseService;
import kr.allcll.seatfinder.subject.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatService {

    private static final String NON_MAJOR_SEATS_EVENT_NAME = "nonMajorSeats";
    private static final int NON_MAJOR_SUBJECT_QUERY_LIMIT = 20;
    private static final String PIN_EVENT_NAME = "pinSeats";
    private static final int TASK_DURATION = 2000;
    private static final int TASK_PERIOD = 60000;

    private final SseService sseService;
    private final SeatStorage seatStorage;
    private final PinRepository pinRepository;
    private final ThreadPoolTaskScheduler scheduler;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 1000)
    public void sendNonMajorSeats() {
        List<Seat> nonMajorSeats = seatStorage.getNonMajorSeats(NON_MAJOR_SUBJECT_QUERY_LIMIT);
        sseService.propagate(NON_MAJOR_SEATS_EVENT_NAME, SeatsResponse.from(nonMajorSeats));
    }

    public void sendPinSeatsInformation(String token) {
        if (scheduledTasks.containsKey(token)) {
            log.info("토큰 {} 에 대해 이미 스케줄된 작업이 존재합니다.", token);
            return;
        }

        Runnable task = () -> {
            long start = System.currentTimeMillis();
            List<Pin> pins = pinRepository.findAllByToken(token);
            log.info("token: {}ms - {}: 핀 조회 결과 {}개 조회", System.currentTimeMillis() - start, token, pins.size());
            start = System.currentTimeMillis();
            List<Subject> subjects = pins.stream()
                .map(Pin::getSubject)
                .toList();
            log.info("token: {}ms - {}: 과목 조회 결과 {}개 조회", System.currentTimeMillis() - start, token, subjects.size());
            start = System.currentTimeMillis();
            List<Seat> pinSeats = seatStorage.getSeats(subjects);
            log.info("token: {}ms - {}: 좌석 조회 결과 {}개 조회", System.currentTimeMillis() - start, token, pinSeats.size());
            start = System.currentTimeMillis();
            sseService.propagate(PIN_EVENT_NAME, PinSeatsResponse.from(pinSeats));
            log.info("token: {}ms - {}: SSE 전송 완료", System.currentTimeMillis() - start, token);
        };

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(task, Duration.ofMillis(TASK_DURATION));
        scheduledTasks.put(token, scheduledFuture);

        scheduler.schedule(() -> {
                log.info("토큰 {}: 태스크 종료", token);
                scheduledFuture.cancel(true);
                scheduledTasks.remove(token);
            },
            new Date(System.currentTimeMillis() + TASK_PERIOD));
    }

}
