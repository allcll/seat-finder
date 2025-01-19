package kr.allcll.seatfinder.excel;

import java.util.HashMap;
import java.util.Map;
import kr.allcll.seatfinder.excel.ExcelSubject.ExcelSubjectBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
public class SubjectSheetHeaderMapper {

    private final Map<Integer, SubjectSheetHeader> headerMap;

    public SubjectSheetHeaderMapper(Row headerRow) {
        this.headerMap = parseExcelHeaders(headerRow);
    }

    private Map<Integer, SubjectSheetHeader> parseExcelHeaders(Row headerRow) {
        Map<Integer, SubjectSheetHeader> headers = new HashMap<>();
        for (Cell cell : headerRow) {
            String headerName = cell.getStringCellValue();
            try {
                SubjectSheetHeader header = SubjectSheetHeader.from(headerName);
                headers.put(cell.getColumnIndex(), header);
            } catch (IllegalArgumentException e) {
                log.warn("정의되지 않은 과목 엑셀 헤더: {}", e.getMessage());
            }
        }
        return headers;
    }

    public ExcelSubject mapRowToSubject(Row row) {
        ExcelSubjectBuilder subjectBuilder = ExcelSubject.builder();
        for (Map.Entry<Integer, SubjectSheetHeader> entry : headerMap.entrySet()) {
            int columnIndex = entry.getKey();
            SubjectSheetHeader subjectHeader = entry.getValue();
            Cell cell = row.getCell(columnIndex);
            String cellValue = (cell == null) ? null : cell.getStringCellValue();
            subjectHeader.setField(subjectBuilder, cellValue);
        }
        return subjectBuilder.build();
    }
}
