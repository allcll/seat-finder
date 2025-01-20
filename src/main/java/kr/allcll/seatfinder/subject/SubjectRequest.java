package kr.allcll.seatfinder.subject;

import kr.allcll.seatfinder.excel.ExcelSubject;

public record SubjectRequest(
    String offeringUniversity,
    String offeringDepartment,
    String subjectNumber,
    String classDivision,
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
    String mainProfessor,
    String supervisingDepartment,
    String notesAndRestrictions,
    String classCategory,
    String onlineLecture,
    String lectureLanguage,
    String foreignerOnly,
    String domesticOnly
) {

    public static SubjectRequest from(ExcelSubject subject) {
        return new SubjectRequest(
            subject.offeringUniversity(),
            subject.offeringDepartment(),
            subject.subjectNumber(),
            subject.classDivision(),
            subject.subjectName(),
            subject.subjectType(),
            subject.electiveArea(),
            subject.academicYearSemester(),
            subject.credit(),
            subject.theoryHours(),
            subject.practiceHours(),
            subject.classType(),
            subject.exchangeEligible(),
            subject.schedule(),
            subject.lectureRoom(),
            subject.mainProfessor(),
            subject.supervisingDepartment(),
            subject.notesAndRestrictions(),
            subject.classCategory(),
            subject.onlineLecture(),
            subject.lectureLanguage(),
            subject.foreignerOnly(),
            subject.domesticOnly()
        );
    }

    public Subject toEntity() {
        return new Subject(
            null,
            offeringUniversity,
            offeringDepartment,
            subjectNumber,
            classDivision,
            subjectName,
            subjectType,
            electiveArea,
            academicYearSemester,
            credit,
            theoryHours,
            practiceHours,
            classType,
            exchangeEligible,
            schedule,
            lectureRoom,
            mainProfessor,
            supervisingDepartment,
            notesAndRestrictions,
            classCategory,
            onlineLecture,
            lectureLanguage,
            foreignerOnly,
            domesticOnly
        );
    }
}
