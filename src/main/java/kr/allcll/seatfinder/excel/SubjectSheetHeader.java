package kr.allcll.seatfinder.excel;

import java.util.Arrays;
import java.util.function.BiConsumer;
import kr.allcll.seatfinder.excel.ExcelSubject.ExcelSubjectBuilder;

public enum SubjectSheetHeader {

    OFFERING_UNIVERSITY("개설대학", ExcelSubjectBuilder::offeringUniversity),
    OFFERING_DEPARTMENT("개설학과전공", ExcelSubjectBuilder::offeringDepartment),
    SUBJECT_NUMBER("학수번호", ExcelSubjectBuilder::subjectNumber),
    CLASS_DIVISION("분반", ExcelSubjectBuilder::classDivision),
    SUBJECT_NAME("교과목명", ExcelSubjectBuilder::subjectName),
    SUBJECT_TYPE("이수구분", ExcelSubjectBuilder::subjectType),
    ELECTIVE_AREA("선택영역", ExcelSubjectBuilder::electiveArea),
    ACADEMIC_YEAR_SEMESTER("학년\n(학기)", ExcelSubjectBuilder::academicYearSemester),
    CREDIT("학점", ExcelSubjectBuilder::credit),
    THEORY_HOURS("이론", ExcelSubjectBuilder::theoryHours),
    PRACTICE_HOURS("실습", ExcelSubjectBuilder::practiceHours),
    CLASS_TYPE("수업\n유형", ExcelSubjectBuilder::classType),
    EXCHANGE_ELIGIBLE("학점교류\n수강가능", ExcelSubjectBuilder::exchangeEligible),
    SCHEDULE("요일 및 강의시간", ExcelSubjectBuilder::schedule),
    LECTURE_ROOM("강의실", ExcelSubjectBuilder::lectureRoom),
    MAIN_PROFESSOR("메인\n교수명", ExcelSubjectBuilder::mainProfessor),
    SUPERVISING_DEPARTMENT("주관학과", ExcelSubjectBuilder::supervisingDepartment),
    NOTES_AND_RESTRICTIONS("수강대상및유의사항", ExcelSubjectBuilder::notesAndRestrictions),
    CLASS_CATEGORY("강좌유형", ExcelSubjectBuilder::classCategory),
    ONLINE_LECTURE("사이버강좌", ExcelSubjectBuilder::onlineLecture),
    LECTURE_LANGUAGE("강의언어", ExcelSubjectBuilder::lectureLanguage),
    FOREIGNER_ONLY("외국인\n전용", ExcelSubjectBuilder::foreignerOnly),
    DOMESTIC_ONLY("내국인\n전용", ExcelSubjectBuilder::domesticOnly),
    ;

    private final String headerName;
    private final BiConsumer<ExcelSubjectBuilder, String> setter;

    SubjectSheetHeader(String headerName, BiConsumer<ExcelSubjectBuilder, String> setter) {
        this.headerName = headerName;
        this.setter = setter;
    }

    public static SubjectSheetHeader from(String headerName) {
        return Arrays.stream(values())
            .filter(header -> header.headerName.equals(headerName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 엑셀 컬럼: " + headerName));
    }

    public void setField(ExcelSubjectBuilder builder, String value) {
        this.setter.accept(builder, value);
    }
}
