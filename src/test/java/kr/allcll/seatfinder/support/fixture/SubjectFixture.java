package kr.allcll.seatfinder.support.fixture;

import kr.allcll.seatfinder.subject.Subject;

public class SubjectFixture {

    public static Subject createSubject(
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject("", "", "", "",
            subjectCode, subjectName,
            "", "", "", "", "", "", "", "",
            professorName, "", "", "", "", "",
            "", "",
            classCode, "", "", "", "", "",
            "", "", "", "", "");
    }

    public static Subject createNonMajorSubject(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(subjectId,
            "", "", "", "",
            subjectCode, subjectName, "", "", "", "", "", "",
            "", "",
            professorName, "", "", "", "", "3210",
            "",
            "대양휴머니티칼리지", classCode, "", "", "",
            "", "", "", "", "",
            "", "");
    }

    public static Subject createMajorSubject(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(subjectId,
            "", "", "", "",
            subjectCode, subjectName, "", "", "", "", "", "",
            "", "",
            professorName, "", "", "", "", "3210",
            "",
            "컴퓨터공학과", classCode, "", "", "",
            "", "", "", "", "",
            "", "");
//        return new Subject(subjectId, "소프트웨어융합대학", "컴퓨터공학과", subjectCode, classCode, subjectName, "", "", "", "", "",
//            "", "", "", "",
//            "", professorName, "", "", "", "", "", "", "", "3210");
    }
}
