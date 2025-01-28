package kr.allcll.seatfinder.exception;

import lombok.Getter;

@Getter
public class AllcllException extends RuntimeException {

    private final String errorCode;

    public AllcllException(AllcllErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.name();
    }

    public AllcllException(String message, Throwable cause, AllcllErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode.name();
    }

    public AllcllException(AllcllErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode.name();
    }
}
