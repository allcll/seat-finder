package kr.allcll.seatfinder.crawler;

import kr.allcll.seatfinder.crawler.dto.NonMajorRequest;
import kr.allcll.seatfinder.crawler.dto.PinSubjectsRequest;
import kr.allcll.seatfinder.sse.SseClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CrawlerClient {

    private final RestClient restClient;
    private final SseClientService sseClientService;
    private static final String URI = "/api/";

    // TODO: 어떤식으로 응답이 오는지, 어떤 HTTP method로 받는지에 따라 수정 필요. 응답으로 SUCCESS 보내줬음 좋겠음.
    public ResponseEntity<String> retrieveNonMajor(NonMajorRequest request) {
        return restClient.post()
            .uri(URI)
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }

    public ResponseEntity<String> retrieveWantPinSubjectsRequest(PinSubjectsRequest request) {
        return restClient.post()
            .uri(URI)
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }

    // 주기적으로 크롤러에게 SSE 달라고 요청 후 열기를 의도함
    @Scheduled(fixedDelay = 1000 * 60)
    public void requestSseConnection() {
        restClient.post()
            .uri(URI)
            .retrieve()
            .toEntity(String.class);
        sseClientService.getSseData();
    }
}
