package kr.allcll.seatfinder.subject.dto;

import kr.allcll.seatfinder.subject.Subject;

public record SubjectResponse(
    Long subjectId,
    String subjectName,
    String subjectCode,
    String classCode,
    String professorName,
    String deptCd
) {

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
            subject.getId(),
            subject.getCuriNm(),
            subject.getCuriNo(),
            subject.getClassName(),
            subject.getLesnEmp(),
            subject.getDeptCd()
        );
    }
}
