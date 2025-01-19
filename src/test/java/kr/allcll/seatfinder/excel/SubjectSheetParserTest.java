package kr.allcll.seatfinder.excel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import kr.allcll.seatfinder.support.fixture.SubjectExcelFileFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

class SubjectSheetParserTest {

    @DisplayName("과목 엑셀 파일을 파싱한다.")
    @Test
    void subjectSheetParserTest() throws IOException {
        MultipartFile mockSubjectExcelFile = SubjectExcelFileFixture.createMockFile();
        SubjectSheetParser subjectSheetParser = new SubjectSheetParser();

        SubjectsParsingResponse subjectsParsingResponse = subjectSheetParser.parse(mockSubjectExcelFile);

        assertThat(subjectsParsingResponse.excelSubjects()).hasSize(1);
        ExcelSubject parsedSubject = subjectsParsingResponse.excelSubjects().getFirst();
        assertAll(
            () -> assertThat(parsedSubject.offeringUniversity()).isEqualTo("대양휴머니티칼리지"),
            () -> assertThat(parsedSubject.offeringDepartment()).isEqualTo("대양휴머니티칼리지"),
            () -> assertThat(parsedSubject.subjectNumber()).isEqualTo("009352"),
            () -> assertThat(parsedSubject.classDivision()).isEqualTo("001"),
            () -> assertThat(parsedSubject.subjectName()).isEqualTo("사고와표현1"),
            () -> assertThat(parsedSubject.subjectType()).isEqualTo("공통교양필수"),
            () -> assertThat(parsedSubject.electiveArea()).isEqualTo("학문기초"),
            () -> assertThat(parsedSubject.academicYearSemester()).isEqualTo("1"),
            () -> assertThat(parsedSubject.credit()).isEqualTo("3.0"),
            () -> assertThat(parsedSubject.theoryHours()).isEqualTo("3"),
            () -> assertThat(parsedSubject.practiceHours()).isEqualTo("0"),
            () -> assertThat(parsedSubject.classType()).isEqualTo("이론"),
            () -> assertThat(parsedSubject.exchangeEligible()).isEqualTo(""),
            () -> assertThat(parsedSubject.schedule()).isEqualTo("화 목 09:00~10:30"),
            () -> assertThat(parsedSubject.lectureRoom()).isEqualTo("세101"),
            () -> assertThat(parsedSubject.mainProfessor()).isEqualTo("노지현"),
            () -> assertThat(parsedSubject.supervisingDepartment()).isEqualTo("국어국문학과"),
            () -> assertThat(parsedSubject.notesAndRestrictions()).isEqualTo("외국인대상과목, 기초(Beginner)"),
            () -> assertThat(parsedSubject.classCategory()).isEqualTo(""),
            () -> assertThat(parsedSubject.onlineLecture()).isEqualTo(""),
            () -> assertThat(parsedSubject.lectureLanguage()).isEqualTo("영어"),
            () -> assertThat(parsedSubject.foreignerOnly()).isEqualTo("Y"),
            () -> assertThat(parsedSubject.domesticOnly()).isEqualTo("")
        );
    }

    @DisplayName("과목 엑셀 파일을 파싱할 수 없는 경우 예외를 던진다.")
    @Test
    void subjectSheetParserTestWithWrongExtension() {
        MultipartFile mockSubjectExcelFile = SubjectExcelFileFixture.createMockFileWithWrongExtension();
        SubjectSheetParser subjectSheetParser = new SubjectSheetParser();

        assertThatThrownBy(() -> subjectSheetParser.parse(mockSubjectExcelFile))
            .isInstanceOf(IOException.class)
            .hasMessage("지원하지 않는 파일 형식입니다. 입력한 파일 형식: txt");
    }
}
