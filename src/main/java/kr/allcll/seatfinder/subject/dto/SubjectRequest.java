//package kr.allcll.seatfinder.subject.dto;
//
//import kr.allcll.seatfinder.excel.ExcelSubject;
//import kr.allcll.seatfinder.subject.Subject;
//
//public record SubjectRequest(
//    String offeringUniversity,
//    String offeringDepartment,
//    String subjectCode,
//    String classCode,
//    String subjectName,
//    String subjectType,
//    String electiveArea,
//    String academicYearSemester,
//    String credit,
//    String theoryHours,
//    String practiceHours,
//    String classType,
//    String exchangeEligible,
//    String schedule,
//    String lectureRoom,
//    String professorName,
//    String supervisingDepartment,
//    String notesAndRestrictions,
//    String classCategory,
//    String onlineLecture,
//    String lectureLanguage,
//    String foreignerOnly,
//    String domesticOnly
//) {
//
//    public static SubjectRequest from(ExcelSubject subject) {
//        return new SubjectRequest(
//            subject.offeringUniversity(),
//            subject.offeringDepartment(),
//            subject.subjectNumber(),
//            subject.classDivision(),
//            subject.subjectName(),
//            subject.subjectType(),
//            subject.electiveArea(),
//            subject.academicYearSemester(),
//            subject.credit(),
//            subject.theoryHours(),
//            subject.practiceHours(),
//            subject.classType(),
//            subject.exchangeEligible(),
//            subject.schedule(),
//            subject.lectureRoom(),
//            subject.mainProfessor(),
//            subject.supervisingDepartment(),
//            subject.notesAndRestrictions(),
//            subject.classCategory(),
//            subject.onlineLecture(),
//            subject.lectureLanguage(),
//            subject.foreignerOnly(),
//            subject.domesticOnly()
//        );
//    }
//
//    public Subject toEntity() {
//        return new Subject(
//            null,
//            offeringUniversity,
//            offeringDepartment,
//            subjectCode,
//            classCode,
//            subjectName,
//            subjectType,
//            electiveArea,
//            academicYearSemester,
//            credit,
//            theoryHours,
//            practiceHours,
//            classType,
//            exchangeEligible,
//            schedule,
//            lectureRoom,
//            professorName,
//            supervisingDepartment,
//            notesAndRestrictions,
//            classCategory,
//            onlineLecture,
//            lectureLanguage,
//            foreignerOnly,
//            domesticOnly
//        );
//    }
//}
