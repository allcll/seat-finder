package kr.allcll.seatfinder.exception;

public enum AllcllErrorCode {

    MAX_PIN_EXCEPTION("이미 %d개의 핀을 등록했습니다."),
    EXIST_PIN_EXCEPTION("이미 핀 등록된 과목입니다."),
    PIN_AND_SUBJECT_NOT_MATCH("핀에 등록된 과목이 아닙니다."),
    NOT_EXIST_SUBJECT("존재하지 않는 과목 입니다.");

    private String message;

    AllcllErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
