package kr.allcll.seatfinder.seat;

import java.time.LocalDateTime;
import kr.allcll.seatfinder.subject.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Seat {

    private Subject subject;
    private int seatCount;
    private LocalDateTime queryTime;
}
