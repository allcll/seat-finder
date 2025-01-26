package kr.allcll.seatfinder.sse;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Service
@RequiredArgsConstructor
public class SseService {

    private static final long SSE_TIMEOUT_MILLIS = 60 * 1000;

    private final SseEmitterStorage sseEmitterStorage;

    public SseEmitter connect() {
        SseEmitter sseEmitter = createSseEmitter();
        sseEmitterStorage.add(sseEmitter);
        sendInitialEvent(sseEmitter);
        return sseEmitter;
    }

    protected SseEmitter createSseEmitter() {
        return new SseEmitter(SSE_TIMEOUT_MILLIS);
    }

    private void sendInitialEvent(SseEmitter sseEmitter) {
        try {
            sseEmitter.send(initialEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SseEventBuilder initialEvent() {
        return SseEmitter.event()
            .name("connection")
            .data("success");
    }
}
