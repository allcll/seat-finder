package kr.allcll.seatfinder.pin;

import kr.allcll.seatfinder.ThreadLocalHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PinApi {

    private final PinService pinService;

    @PostMapping("/api/pin")
    public ResponseEntity<Void> addPinOnSubject(@RequestParam Long subjectId) {
        pinService.addPinOnSubject(subjectId, ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/pin")
    public ResponseEntity<Void> deletePinOnSubject(@RequestParam Long subjectId) {
        pinService.deletePinOnSubject(subjectId, ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok().build();
    }
}
