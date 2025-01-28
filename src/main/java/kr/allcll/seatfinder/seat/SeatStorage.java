package kr.allcll.seatfinder.seat;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SeatStorage {

    private final List<Seat> seats;

    public SeatStorage() {
        this.seats = new ArrayList<>();
    }

    public List<Seat> getNonMajorSeats(int limit) {
        return seats.stream()
            .filter(seat -> seat.getSubject().isNonMajor())
            .sorted((s1, s2) -> s2.getSeatCount() - s1.getSeatCount())
            .limit(limit)
            .toList();
    }
}
