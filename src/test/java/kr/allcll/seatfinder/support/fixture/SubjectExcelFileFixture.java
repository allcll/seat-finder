package kr.allcll.seatfinder.support.fixture;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class SubjectExcelFileFixture {

    private static final String[] SUBJECT_HEADERS = {"순번", "개설대학", "개설학과전공", "학수번호", "분반", "교과목명", "이수구분", "선택영역",
        "학년\n(학기)", "학점", "이론", "실습", "수업\n유형", "학점교류\n수강가능", "요일 및 강의시간", "강의실", "메인\n교수명", "주관학과", "수강대상및유의사항",
        "강좌유형", "사이버강좌", "강의언어", "외국인\n전용", "내국인\n전용"};

    private static final String[] SUBJECT_DATA = {"1", "대양휴머니티칼리지", "대양휴머니티칼리지", "009352", "001", "사고와표현1",
        "공통교양필수", "학문기초", "1", "3.0", "3", "0", "이론", "", "화 목 09:00~10:30", "세101", "노지현", "국어국문학과",
        "외국인대상과목, 기초(Beginner)", "", "", "영어", "Y", ""};


    public static MultipartFile createMockFile() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("개설강좌");
        createHeaderRow(sheet);
        createDataRow(sheet);
        InputStream excelInputStream = createInputStream(workbook);
        return new MockMultipartFile(
            "2024-2강의시간표(한국어)_20240809",
            "2024-2강의시간표(한국어)_20240809.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            excelInputStream
        );
    }

    public static Row createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, SUBJECT_HEADERS.length)
            .forEach(i -> headerRow.createCell(i).setCellValue(SUBJECT_HEADERS[i]));
        return headerRow;
    }

    public static Row createDataRow(XSSFSheet sheet) {
        Row dataRow = sheet.createRow(1);
        IntStream.range(0, SUBJECT_DATA.length)
            .forEach(i -> dataRow.createCell(i).setCellValue(SUBJECT_DATA[i]));
        return dataRow;
    }

    private static InputStream createInputStream(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public static MultipartFile createMockFileWithWrongExtension() {
        return new MockMultipartFile(
            "file",
            "subjects.txt",
            "application/vnd.ms-excel",
            "test".getBytes()
        );
    }
}
