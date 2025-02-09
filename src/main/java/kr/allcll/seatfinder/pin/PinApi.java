package kr.allcll.seatfinder.pin;

import kr.allcll.seatfinder.ThreadLocalHolder;
import kr.allcll.seatfinder.pin.dto.SubjectIdsResponse;
import kr.allcll.seatfinder.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class PinApi {

    private final PinService pinService;
    private final SseService sseService;

    @PostMapping("/api/pin")
    public ResponseEntity<Void> addPinOnSubject(@RequestParam Long subjectId) {
        pinService.addPinOnSubject(subjectId, ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/pin/{subjectId}")
    public ResponseEntity<Void> deletePinOnSubject(@PathVariable Long subjectId) {
        pinService.deletePinOnSubject(subjectId, ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/pins")
    public ResponseEntity<SubjectIdsResponse> retrievePins() {
        SubjectIdsResponse response = pinService.retrievePins(ThreadLocalHolder.SHARED_TOKEN.get());
        return ResponseEntity.ok(response);
    }
}
