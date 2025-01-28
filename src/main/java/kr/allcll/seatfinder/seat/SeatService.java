package kr.allcll.seatfinder.seat;

import java.util.List;
import kr.allcll.seatfinder.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private static final String NON_MAJOR_SEATS_EVENT_NAME = "nonMajorSeats";
    private static final int NON_MAJOR_SUBJECT_QUERY_LIMIT = 10;

    private final SseService sseService;
    private final SeatStorage seatStorage;

    @Scheduled(fixedRate = 1000)
    public void sendNonMajorSeats() {
        List<Seat> nonMajorSeats = seatStorage.getNonMajorSeats(NON_MAJOR_SUBJECT_QUERY_LIMIT);
        sseService.propagate(NON_MAJOR_SEATS_EVENT_NAME, nonMajorSeats);
    }
}
