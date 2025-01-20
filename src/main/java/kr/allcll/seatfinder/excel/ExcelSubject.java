package kr.allcll.seatfinder.excel;

import lombok.Builder;

@Builder
public record ExcelSubject(
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

}
