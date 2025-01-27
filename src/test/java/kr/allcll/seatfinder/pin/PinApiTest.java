package kr.allcll.seatfinder.pin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PinApi.class)
class PinApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PinService pinService;

    private static final String BASE_URL = "/api/pin";

    @DisplayName("핀 등록을 할 때에 요청과 응답을 확인한다.")
    @Test
    void addPinOnSubjectWhenPinNotExist() throws Exception {
        // when, then
        mockMvc.perform(post(BASE_URL)
                .param("subjectId", "1"))
            .andExpect(status().isOk());
    }

    @DisplayName("핀을 삭제할 때 요청과 응답을 확인한다.")
    @Test
    void deletePinOnSubject() throws Exception {
        // when, then
        mockMvc.perform(delete(BASE_URL + "/{subjectId}", 1L))
            .andExpect(status().isOk());
    }
}
