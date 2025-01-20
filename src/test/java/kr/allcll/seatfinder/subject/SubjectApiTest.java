package kr.allcll.seatfinder.subject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.allcll.seatfinder.excel.SubjectSheetParser;
import kr.allcll.seatfinder.excel.SubjectsParsingResponse;
import kr.allcll.seatfinder.subject.dto.SubjectsRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(SubjectApi.class)
class SubjectApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubjectSheetParser subjectSheetParser;

    @MockitoBean
    private SubjectService subjectService;

    @DisplayName("과목 엑셀 파일을 업로드한다.")
    @Test
    void uploadSubjectsApiTest() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
            "file",
            "subjects.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "dummy-content".getBytes()
        );
        SubjectsParsingResponse mockResponse = new SubjectsParsingResponse(List.of());
        given(subjectSheetParser.parse(any(MultipartFile.class))).willReturn(mockResponse);

        mockMvc.perform(multipart("/api/subject/upload").file(mockFile))
            .andExpect(status().isOk())
            .andExpect(content().string("업로드에 성공했습니다."));

        then(subjectSheetParser).should().parse(any(MultipartFile.class));
        then(subjectService).should().save(any(SubjectsRequest.class));
    }
}
