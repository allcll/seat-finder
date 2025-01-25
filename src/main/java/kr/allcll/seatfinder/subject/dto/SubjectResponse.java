package kr.allcll.seatfinder.subject.dto;

import kr.allcll.seatfinder.subject.Subject;

public record SubjectResponse(
    Long subjectId,
    String offeringUniversity,
    String offeringDepartment,
    String subjectCode,
    String classCode,
    String subjectName,
    String subjectType,
    String electiveArea,
    String academicYearSemester,
    String credit,
    String theoryHours,
    String practiceHours,
    String classType,
    String exchangeEligible,
    String schedule,
    String lectureRoom,
    String professorName,
    String supervisingDepartment,
    String notesAndRestrictions,
    String classCategory,
    String onlineLecture,
    String lectureLanguage,
    String foreignerOnly,
    String domesticOnly
) {

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
            subject.getId(),
            subject.getOfferingUniversity(),
            subject.getOfferingDepartment(),
            subject.getSubjectCode(),
            subject.getClassCode(),
            subject.getSubjectName(),
            subject.getSubjectType(),
            subject.getElectiveArea(),
            subject.getAcademicYearSemester(),
            subject.getCredit(),
            subject.getTheoryHours(),
            subject.getPracticeHours(),
            subject.getClassType(),
            subject.getExchangeEligible(),
            subject.getSchedule(),
            subject.getLectureRoom(),
            subject.getProfessorName(),
            subject.getSupervisingDepartment(),
            subject.getNotesAndRestrictions(),
            subject.getClassCategory(),
            subject.getOnlineLecture(),
            subject.getLectureLanguage(),
            subject.getForeignerOnly(),
            subject.getDomesticOnly()
        );
    }
}
