package kr.allcll.seatfinder.cart;

import kr.allcll.seatfinder.ThreadLocalHolder;
import kr.allcll.seatfinder.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class CartApi {

    private final SseService sseService;

    @GetMapping(value = "/api/cart/sse-connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> getServerSentEventConnectionAtCart() {
        String token = ThreadLocalHolder.SHARED_TOKEN.get();
        return ResponseEntity.ok(sseService.connect(token));
    }
}
