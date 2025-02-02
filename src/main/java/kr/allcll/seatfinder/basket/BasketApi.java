package kr.allcll.seatfinder.basket;

import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @GetMapping("/api/basket")
    ResponseEntity<BasketsResponse> getAllSubjects() {
        BasketsResponse response = basketService.getAllSubjects();
        return ResponseEntity.ok(response);
    }
}
