package kr.allcll.seatfinder.config;

import jakarta.annotation.PostConstruct;
import kr.allcll.seatfinder.sse.SseClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseClientConfig {

    private final SseClientService sseClientService;

    @PostConstruct
    public void init() {
        sseClientService.getSseData();
    }
}
