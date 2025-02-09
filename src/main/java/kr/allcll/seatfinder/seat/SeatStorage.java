package kr.allcll.seatfinder.seat;

import java.util.ArrayList;
import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
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

    public List<Seat> getSeats(List<Subject> subjects) {
        List<Seat> result = new ArrayList<>();
        for (Subject subject : subjects) {
            for (Seat seat : seats) {
                if (seat.getSubject().getId().equals(subject.getId())) {
                    result.add(seat);
                    break;
                }
            }
        }
        return result;
    }

    public void add(Seat seat) {
        seats.add(seat);
    }

    public void addAll(List<Seat> seats) {
        this.seats.addAll(seats);
    }
}
