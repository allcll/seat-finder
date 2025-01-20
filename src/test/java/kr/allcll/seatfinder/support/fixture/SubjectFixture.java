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
            "", professorName, "", "", "", "", "", "", "");
    }
}
