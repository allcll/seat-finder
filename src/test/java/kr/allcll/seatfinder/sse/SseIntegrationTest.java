package kr.allcll.seatfinder.sse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(SseIntegrationTest.class);

    @MockitoSpyBean
    private SseService sseService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("SSE를 연결하고, 최초 메시지를 받는다.")
    @Test
    void sseConnectionTest() {
        long mockSseEmitterTimeout = 500L;
        when(sseService.createSseEmitter()).thenReturn(new SseEmitter(mockSseEmitterTimeout));

        // when
        Response response = RestAssured.given()
            .accept("text/event-stream")
            .when()
            .get("/api/connect")
            .then()
            .statusCode(200)
            .extract()
            .response();

        // then
        SseTestHelper.assertResponseContainsMessage(response, "success");
    }

    private static class SseTestHelper {

        private static final long TIMEOUT = 1000;

        public static void assertResponseContainsMessage(Response response, String message) {
            String body = response.getBody().asString();
            try (BufferedReader reader = new BufferedReader(new StringReader(body))) {
                String eventReceived = readResponse(reader, message);
                assertThat(eventReceived).contains(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static String readResponse(BufferedReader reader, String message) throws IOException {
            StringBuilder lines = new StringBuilder();
            String line;
            long startTime = System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
                log.info("Received: {}", line);
                lines.append(line).append("\n");
                if (line.startsWith(message)) {
                    break;
                }
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    break;
                }
            }
            return lines.toString();
        }
    }
}
