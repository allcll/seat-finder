package kr.allcll.seatfinder.pin.dto;

import java.util.List;
import kr.allcll.seatfinder.seat.Seat;

public record PinSeatsResponse(
    List<PinSeatResponse> seatResponses
) {

    public static PinSeatsResponse from(List<Seat> seats) {
        return new PinSeatsResponse(seats.stream()
            .map(PinSeatResponse::from)
            .toList());
    }
}
