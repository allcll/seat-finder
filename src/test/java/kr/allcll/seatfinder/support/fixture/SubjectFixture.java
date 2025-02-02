package kr.allcll.seatfinder.support.fixture;

import kr.allcll.seatfinder.subject.Subject;

public class SubjectFixture {

    public static Subject createSubject(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(subjectId, "", "", subjectCode, classCode, subjectName, "", "", "", "", "", "", "", "", "",
            "", professorName, "", "", "", "", "", "", "", "3210");
    }

    public static Subject createNonMajorSubject(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(subjectId, "대양휴머니티칼리지", "대양휴머니티칼리지", subjectCode, classCode, subjectName, "", "", "", "", "",
            "", "", "", "",
            "", professorName, "", "", "", "", "", "", "", "3210");
    }

    public static Subject createMajorSubject(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(subjectId, "소프트웨어융합대학", "컴퓨터공학과", subjectCode, classCode, subjectName, "", "", "", "", "",
            "", "", "", "",
            "", professorName, "", "", "", "", "", "", "","3210");
    }
}
