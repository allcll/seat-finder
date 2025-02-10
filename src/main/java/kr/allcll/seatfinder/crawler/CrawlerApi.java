package kr.allcll.seatfinder.crawler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlerApi {

    private final CrawlerService crawlerService;

    // 교양 top20을 자료구조에 저장한다.
    @PostMapping("/api/save-nonmajors")
    public ResponseEntity<Void> saveNonMajorSubjects() {
        crawlerService.saveNonMajorSubjects();
        return ResponseEntity.ok().build();
    }

    // 교양 top20을 크롤러에게 전달해둘 아이이다. 힌 번만 실행하면 된다.
    @PostMapping("/api/send-nonmajor")
    public ResponseEntity<Void> retrieveSendNonMajor() {
        crawlerService.sendToCrawlerNonMajorRequest();
        return ResponseEntity.ok().build();
    }
}
