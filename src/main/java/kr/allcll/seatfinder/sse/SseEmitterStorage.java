package kr.allcll.seatfinder.sse;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseEmitterStorage {

    /*
        ExecutorService로 변경이 필요할 수 있습니다.
     */
    private final List<SseEmitter> emitters;

    public SseEmitterStorage() {
        this.emitters = new ArrayList<>();
    }

    public void add(SseEmitter sseEmitter) {
        emitters.add(sseEmitter);
    }
}
