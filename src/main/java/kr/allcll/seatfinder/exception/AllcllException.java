package kr.allcll.seatfinder.exception;

import lombok.Getter;

@Getter
public class AllcllException extends RuntimeException {

    private final String errorCode;

    public AllcllException(AllcllErrorCode e) {
        super(e.getMessage());
        this.errorCode = e.name();
    }

    public AllcllException(AllcllErrorCode e, Object... args) {
        super(String.format(e.getMessage(), args));
        this.errorCode = e.name();
    }
}
