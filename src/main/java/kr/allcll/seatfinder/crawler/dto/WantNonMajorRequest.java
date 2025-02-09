package kr.allcll.seatfinder.crawler.dto;

import java.util.List;

public record WantNonMajorRequest(
    List<Long> wantNonMajorSubjectIds
) {

    public static WantNonMajorRequest from(List<Long> subjectIds) {
        return new WantNonMajorRequest(subjectIds);
    }
}
