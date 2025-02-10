package kr.allcll.seatfinder.crawler.dto;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;

public record WantNonMajorRequest(
    List<Long> wantNonMajorSubjectIds
) {

    public static WantNonMajorRequest from(List<Subject> subjects) {
        List<Long> subjectIds = subjects.stream()
            .map(Subject::getId)
            .toList();
        return new WantNonMajorRequest(subjectIds);
    }
}
