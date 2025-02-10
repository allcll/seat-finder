package kr.allcll.seatfinder.crawler;

import kr.allcll.seatfinder.external.CrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerApi {

    private final CrawlerService crawlerService;

    // 교양 top20을 자료구조에 저장한다.
    @Retryable(
        retryFor = {Exception.class},
        maxAttempts = 1000,
        backoff = @Backoff(delay = 1000)
    )
    public void saveNonMajorSubjects() {
        try {
            crawlerService.saveNonMajorSubjects();
        } catch (Exception e) {
            log.error("교양 top20 저장 중 오류 발생", e);
            throw e;
        }
        log.info("교양 top20 저장 완료");
    }

    // 교양 top20을 크롤러에게 전달해둘 아이이다. 힌 번만 실행하면 된다.
    @Retryable(
        retryFor = {Exception.class},
        maxAttempts = 1000,
        backoff = @Backoff(delay = 5000)
    )
    public void retrieveSendNonMajor() {
        try {
            crawlerService.sendToCrawlerNonMajorRequest();
        } catch (Exception e) {
            log.error("교양 top20 전달 중 오류 발생", e);
            throw e;
        }
        log.info("교양 top20 전달 완료");
    }
}
