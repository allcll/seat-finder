package kr.allcll.seatfinder.excel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import kr.allcll.seatfinder.support.fixture.SubjectExcelFileFixture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubjectSheetHeaderMapperTest {

    @DisplayName("엑셀의 행을 과목 데이터로 변환한다.")
    @Test
    void mapRowToSubjectTest() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("개설강좌");
        Row headerRow = SubjectExcelFileFixture.createHeaderRow(sheet);
        Row dataRow = SubjectExcelFileFixture.createDataRow(sheet);

        SubjectSheetHeaderMapper subjectSheetHeaderMapper = new SubjectSheetHeaderMapper(headerRow);
        ExcelSubject excelSubject = subjectSheetHeaderMapper.mapRowToSubject(dataRow);

        assertAll(() -> assertThat(excelSubject.offeringUniversity()).isEqualTo("대양휴머니티칼리지"),
            () -> assertThat(excelSubject.offeringDepartment()).isEqualTo("대양휴머니티칼리지"),
            () -> assertThat(excelSubject.subjectNumber()).isEqualTo("009352"),
            () -> assertThat(excelSubject.classDivision()).isEqualTo("001"),
            () -> assertThat(excelSubject.subjectName()).isEqualTo("사고와표현1"),
            () -> assertThat(excelSubject.subjectType()).isEqualTo("공통교양필수"),
            () -> assertThat(excelSubject.electiveArea()).isEqualTo("학문기초"),
            () -> assertThat(excelSubject.academicYearSemester()).isEqualTo("1"),
            () -> assertThat(excelSubject.credit()).isEqualTo("3.0"),
            () -> assertThat(excelSubject.theoryHours()).isEqualTo("3"),
            () -> assertThat(excelSubject.practiceHours()).isEqualTo("0"),
            () -> assertThat(excelSubject.classType()).isEqualTo("이론"),
            () -> assertThat(excelSubject.exchangeEligible()).isEqualTo(""),
            () -> assertThat(excelSubject.schedule()).isEqualTo("화 목 09:00~10:30"),
            () -> assertThat(excelSubject.lectureRoom()).isEqualTo("세101"),
            () -> assertThat(excelSubject.mainProfessor()).isEqualTo("노지현"),
            () -> assertThat(excelSubject.supervisingDepartment()).isEqualTo("국어국문학과"),
            () -> assertThat(excelSubject.notesAndRestrictions()).isEqualTo("외국인대상과목, 기초(Beginner)"),
            () -> assertThat(excelSubject.classCategory()).isEqualTo(""),
            () -> assertThat(excelSubject.onlineLecture()).isEqualTo(""),
            () -> assertThat(excelSubject.lectureLanguage()).isEqualTo("영어"),
            () -> assertThat(excelSubject.foreignerOnly()).isEqualTo("Y"),
            () -> assertThat(excelSubject.domesticOnly()).isEqualTo(""));
    }
}
