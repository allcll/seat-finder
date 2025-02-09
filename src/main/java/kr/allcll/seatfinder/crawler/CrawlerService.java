package kr.allcll.seatfinder.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.allcll.seatfinder.basket.Basket;
import kr.allcll.seatfinder.basket.BasketRepository;
import kr.allcll.seatfinder.crawler.dto.WantNonMajorRequest;
import kr.allcll.seatfinder.crawler.dto.WantPinSubject;
import kr.allcll.seatfinder.crawler.dto.WantPinSubjectsRequest;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.pin.Pin;
import kr.allcll.seatfinder.pin.PinRepository;
import kr.allcll.seatfinder.sse.SseEmitterStorage;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerService {

    private static final String NON_MAJOR_DEPT_CD = "9005"; // 대양휴머니티칼리지 학과코드
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final int SUBJECT_OFFER_COUNT = 20;

    private final SubjectRepository subjectRepository;
    private final BasketRepository basketRepository;
    private final PinRepository pinRepository;
    private final TopNonMajorStorage topNonMajorStorage;
    private final SseEmitterStorage sseEmitterStorage;
    private final CrawlerClient crawlerClient;

    public void saveNonMajorSubjects() {
        Map<Long, Integer> nonMajorInformation = new HashMap<>();
        List<Subject> nonMajorSubjects = subjectRepository.findAllByDeptCd(NON_MAJOR_DEPT_CD);
        for (Subject nonMajorSubject : nonMajorSubjects) {
            Basket basket = basketRepository.findBySubjectId(nonMajorSubject.getId()).stream().findAny()
                .orElseThrow(() -> new AllcllException(
                    AllcllErrorCode.BASKET_NOT_FOUND));
            Integer totRcnt = basket.getTotRcnt();
            nonMajorInformation.put(nonMajorSubject.getId(), totRcnt);
        }
        List<Long> keySet = new ArrayList<>(nonMajorInformation.keySet());
        keySet.sort((o1, o2) -> nonMajorInformation.get(o2).compareTo(nonMajorInformation.get(o1)));
        List<Long> topKeys = keySet.stream()
            .limit(SUBJECT_OFFER_COUNT)
            .toList();
        topNonMajorStorage.addAll(topKeys);
    }

    public void sendToCrawlerNonMajorRequest() {
        List<Long> topNonMajors = topNonMajorStorage.getTopNonMajors();
        ResponseEntity<String> response = crawlerClient.retrieveNonMajor(WantNonMajorRequest.from(topNonMajors));
        if (SUCCESS_MESSAGE.equals(response.getBody())) {
            log.info("교양 과목 정보를 전달 성공했습니다.");
        }
    }

    @Scheduled(fixedDelay = 1000 * 60) // 이전 작업 이후 1분. 1분으로 될 지 걱정
    public void sendWantPinSubjectIdsToCrawler() {
        WantPinSubjectsRequest request = getPinSubjects();
        ResponseEntity<String> response = crawlerClient.retrieveWantPinSubjectsRequest(request);
        if (SUCCESS_MESSAGE.equals(response.getBody())) {
            log.info("pin 과목 정보를 전달 성공했습니다.");
        }
    }

    private WantPinSubjectsRequest getPinSubjects() {
        Map<String, SseEmitter> mapEmitters = sseEmitterStorage.getMapEmitters();
        Set<String> tokens = mapEmitters.keySet();
        Map<Long, Integer> pinsInformation = new HashMap<>();
        // 활성사용자를 기반으로, 과목들의 핀이 몇개 있는지 센다.
        for (String token : tokens) {
            List<Pin> pins = pinRepository.findAllByToken(token);
            for (Pin pin : pins) {
                Subject subject = pin.getSubject();
                Long subjectId = subject.getId();
                pinsInformation.merge(subjectId, 1, Integer::sum);
            }
        }
        List<WantPinSubject> wantPinSubjects = getWantPinSubjects(pinsInformation);
        WantPinSubjectsRequest request = WantPinSubjectsRequest.from(wantPinSubjects);
        return request;
    }

    private List<WantPinSubject> getWantPinSubjects(Map<Long, Integer> pinsInformation) {
        List<Long> keySet = new ArrayList<>(pinsInformation.keySet());
        keySet.sort((o1, o2) -> pinsInformation.get(o2).compareTo(pinsInformation.get(o1)));

        int mapSize = pinsInformation.size();
        int firstIdx = mapSize / 3;
        int secondIdx = firstIdx + 1;

        List<WantPinSubject> wantPinSubjects = new ArrayList<>();

        for (int nowIdx = 0; nowIdx < mapSize; nowIdx++) {
            Long subjectId = keySet.get(nowIdx);
            int priority;
            if (nowIdx <= firstIdx) {
                priority = 1;
            } else if (nowIdx <= secondIdx) {
                priority = 2;
            } else {
                priority = 3;
            }
            wantPinSubjects.add(new WantPinSubject(subjectId, priority));
        }
        return wantPinSubjects;
    }
}
