package kr.allcll.seatfinder.exception;

public record ErrorResponse(
    String code,
    String message,
    String status
) {

}
