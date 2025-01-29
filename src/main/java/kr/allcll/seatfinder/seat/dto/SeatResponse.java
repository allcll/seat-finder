package kr.allcll.seatfinder.seat.dto;

import java.time.LocalDateTime;
import kr.allcll.seatfinder.seat.Seat;

public record SeatResponse(
    Long subjectId,
    Integer seatCount,
    LocalDateTime queryTime
) {

    public static SeatResponse from(Seat seat) {
        return new SeatResponse(
            seat.getSubject().getId(),
            seat.getSeatCount(),
            seat.getQueryTime()
        );
    }
}
