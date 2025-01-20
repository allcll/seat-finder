package kr.allcll.seatfinder.subject.dto;

import java.util.List;
import kr.allcll.seatfinder.excel.ExcelSubject;
import kr.allcll.seatfinder.excel.SubjectsParsingResponse;
import kr.allcll.seatfinder.subject.Subject;

public record SubjectsRequest(
    List<SubjectRequest> subjectRequests
) {

    public static SubjectsRequest from(SubjectsParsingResponse subjectsParsingResponse) {
        List<ExcelSubject> subjectsRequest = subjectsParsingResponse.excelSubjects();
        return new SubjectsRequest(subjectsRequest.stream()
            .map(SubjectRequest::from)
            .toList());
    }

    public List<Subject> toEntity() {
        return subjectRequests.stream()
            .map(SubjectRequest::toEntity)
            .toList();
    }
}
