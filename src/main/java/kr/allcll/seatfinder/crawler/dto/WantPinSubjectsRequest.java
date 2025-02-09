package kr.allcll.seatfinder.crawler.dto;

import java.util.List;

public record WantPinSubjectsRequest(
    List<WantPinSubject> wantPinSubjects
) {

    public static WantPinSubjectsRequest from(List<WantPinSubject> wantPinSubjects) {
        return new WantPinSubjectsRequest(wantPinSubjects);
    }
}
