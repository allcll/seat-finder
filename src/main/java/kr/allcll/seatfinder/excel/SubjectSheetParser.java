package kr.allcll.seatfinder.excel;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class SubjectSheetParser {

    private static final int TARGET_SHEET_INDEX = 0;
    private static final int HEADER_ROW_INDEX = 0;
    private static final String FILE_FORMAT_XLSX = "xlsx";
    private static final String FILE_FORMAT_XLS = "xls";

    public SubjectsParsingResponse parse(MultipartFile file) throws IOException {
        Workbook workbook = getWorkbook(file);
        Sheet sheet = workbook.getSheetAt(TARGET_SHEET_INDEX);
        Row headerRow = sheet.getRow(HEADER_ROW_INDEX);
        SubjectSheetHeaderMapper headerMapper = new SubjectSheetHeaderMapper(headerRow);
        return parseRows(sheet, headerMapper);
    }

    private Workbook getWorkbook(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (FILE_FORMAT_XLSX.equals(extension)) {
            return new XSSFWorkbook(file.getInputStream());
        } else if (FILE_FORMAT_XLS.equals(extension)) {
            return new HSSFWorkbook(file.getInputStream());
        }
        throw new IOException("지원하지 않는 파일 형식입니다. 입력한 파일 형식: " + extension);
    }

    private SubjectsParsingResponse parseRows(Sheet worksheet, SubjectSheetHeaderMapper headerMapper) {
        log.info("입력된 엑셀 데이터 행 길이 (헤더 포함): {}", worksheet.getPhysicalNumberOfRows());
        List<ExcelSubject> excelSubjects = IntStream.range(1, worksheet.getPhysicalNumberOfRows())
            .mapToObj(worksheet::getRow)
            .map(headerMapper::mapRowToSubject)
            .toList();
        return new SubjectsParsingResponse(excelSubjects);
    }
}
