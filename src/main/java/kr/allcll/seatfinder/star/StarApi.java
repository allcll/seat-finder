package kr.allcll.seatfinder.star;

import kr.allcll.seatfinder.ThreadLocalHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarApi {

    private final StarService starService;

    @PostMapping("/api/stars")
    ResponseEntity<Void> addStarOnSubject(@RequestParam Long subjectId) {
        starService.addStarOnSubject(subjectId, ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok().build();
    }
}
