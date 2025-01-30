package kr.allcll.seatfinder.cart;

import java.util.List;
import kr.allcll.seatfinder.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String CART_EVENT_NAME = "현재 관심 과목 담기 정보";

    private final SseService sseService;
    private final CartRepository cartRepository;

    @Scheduled(fixedRate = 1000)
    public void sendCartInformations() {
        List<Cart> sendingCartInformation = cartRepository.findTop2411ByOrderByQueryTimeDesc();
        sseService.propagate(CART_EVENT_NAME, CartResponse.from(sendingCartInformation));
    }
}
