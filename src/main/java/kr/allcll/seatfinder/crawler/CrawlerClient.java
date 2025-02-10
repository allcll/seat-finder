package kr.allcll.seatfinder.crawler;

import kr.allcll.seatfinder.crawler.dto.NonMajorRequest;
import kr.allcll.seatfinder.crawler.dto.PinSubjectsRequest;
import kr.allcll.seatfinder.sse.SseClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(ExternalProperties.class)
public class CrawlerClient {

    private final RestClient restClient;
    private final SseClientService sseClientService;
    private final ExternalProperties externalProperties;

    // TODO: 어떤식으로 응답이 오는지, 어떤 HTTP method로 받는지에 따라 수정 필요. 응답으로 SUCCESS 보내줬음 좋겠음.
    public ResponseEntity<String> retrieveNonMajor(NonMajorRequest request) {
        return restClient.post()
            .uri(externalProperties.host() + externalProperties.nonMajorPath())
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }

    public ResponseEntity<String> retrieveWantPinSubjectsRequest(PinSubjectsRequest request) {
        return restClient.post()
            .uri(externalProperties.host() + externalProperties.pinPath())
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void requestSseConnection() {
        sseClientService.getSseData();
    }
}
