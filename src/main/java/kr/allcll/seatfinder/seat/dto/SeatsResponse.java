package kr.allcll.seatfinder.seat.dto;

import java.util.List;
import kr.allcll.seatfinder.seat.Seat;

public record SeatsResponse(
    List<SeatResponse> seatResponses
) {

    public static SeatsResponse from(List<Seat> seats) {
        return new SeatsResponse(seats.stream()
            .map(SeatResponse::from)
            .toList());
    }
}
