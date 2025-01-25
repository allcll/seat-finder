package kr.allcll.seatfinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import kr.allcll.seatfinder.pin.PinApi;
import kr.allcll.seatfinder.pin.PinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PinApi.class)
class AuthInterceptorTest {

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_VALIDATE_API_URL = "/api/pin?subjectId=1";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PinService pinService;

    @Test
    @DisplayName("쿠키에 토큰이 존재하지 않을 때 토큰의 생성을 확인한다.")
    void addPinOnSubject() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post(TOKEN_VALIDATE_API_URL))
            .andReturn()
            .getResponse();
        assertThat(response.getCookie(TOKEN_KEY)).isNotNull();
    }
}
