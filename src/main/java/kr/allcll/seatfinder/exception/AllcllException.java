package kr.allcll.seatfinder.exception;

import lombok.Getter;

@Getter
public class AllcllException extends RuntimeException {

    private final String errorCode;

    public AllcllException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static AllcllException from(ExceptionMessage e) {
        return new AllcllException(e.getMessage(), e.name());
    }

    public static AllcllException of(int number, ExceptionMessage e) {
        String exceptionMessage = String.format(e.getMessage(), number);
        return new AllcllException(exceptionMessage, e.name());
    }
}
