package kr.allcll.seatfinder.crawler;

import kr.allcll.seatfinder.crawler.dto.WantNonMajorRequest;
import kr.allcll.seatfinder.crawler.dto.WantPinSubjectsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CrawlerClient {

    private final RestClient restClient;
    private static final String URI = "/api/";

    // TODO: 어떤식으로 응답이 오는지, 어떤 HTTP method로 받는지에 따라 수정 필요. 응답으로 SUCCESS 보내줬음 좋겠음.
    public ResponseEntity<String> retrieveNonMajor(WantNonMajorRequest request) {
        return restClient.post()
            .uri(URI)
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }

    public ResponseEntity<String> retrieveWantPinSubjectsRequest(WantPinSubjectsRequest request) {
        return restClient.post()
            .uri(URI)
            .body(request)
            .retrieve()
            .toEntity(String.class);
    }
}
