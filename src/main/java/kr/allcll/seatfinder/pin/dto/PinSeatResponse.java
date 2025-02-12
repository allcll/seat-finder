package kr.allcll.seatfinder.pin.dto;

import java.time.LocalDateTime;
import kr.allcll.seatfinder.seat.Seat;

public record PinSeatResponse(
    Long subjectId,
    Integer seatCount,
    LocalDateTime queryTime
) {

    public static PinSeatResponse from(Seat seat) {
        return new PinSeatResponse(
            seat.getSubject().getId(),
            seat.getSeatCount(),
            seat.getQueryTime()
        );
    }
}
