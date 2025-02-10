package kr.allcll.seatfinder.crawler.dto;

import java.util.List;

public record PinSubjectsRequest(
    List<PinSubject> pinSubjects
) {

    public static PinSubjectsRequest from(List<PinSubject> pinSubjects) {
        return new PinSubjectsRequest(pinSubjects);
    }
}
