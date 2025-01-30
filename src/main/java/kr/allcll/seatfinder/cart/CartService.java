package kr.allcll.seatfinder.cart;

import java.util.List;
import kr.allcll.seatfinder.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final SseService sseService;
    private final CartRepository cartRepository;

    @Scheduled(fixedRate = 1000)
    public void sendCartInformations() {
        List<Cart> sendingCartInformation = cartRepository.findTop2411ByOrderByQueryTimeDesc();
        sseService.propagate("현재 관심 과목 담기 정보", CartResponse.from(sendingCartInformation));
    }
}
