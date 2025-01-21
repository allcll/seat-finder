package kr.allcll.seatfinder.pin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PinApi.class)
class PinApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PinService pinService;

    private static final String TOKEN_KEY = "token";

    @Test
    @DisplayName("쿠키에 토큰이 존재하지 않을 때 토큰의 생성을 확인한다.")
    void pinSubject() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/pin?subjectId=1"))
            .andReturn()
            .getResponse();
        assertThat(response.getCookie(TOKEN_KEY)).isNotNull();
    }

    void mockPinService() {
        doNothing().when(pinService).addPinAtSubject(any(), any());
    }
}