package kr.allcll.seatfinder.subject.dto;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;

public record SubjectsResponse(
    List<SubjectResponse> subjectResponses
) {

    public static SubjectsResponse from(List<Subject> subjects) {
        return new SubjectsResponse(subjects.stream()
            .map(SubjectResponse::from)
            .toList());
    }
}
