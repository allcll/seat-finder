package kr.allcll.seatfinder.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.allcll.seatfinder.basket.Basket;
import kr.allcll.seatfinder.basket.BasketRepository;
import kr.allcll.seatfinder.crawler.dto.NonMajorRequest;
import kr.allcll.seatfinder.crawler.dto.PinSubject;
import kr.allcll.seatfinder.crawler.dto.PinSubjectsRequest;
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
        List<Subject> nonMajorSubjects = subjectRepository.findAllByDeptCd(NON_MAJOR_DEPT_CD);
        Map<Subject, Integer> nonMajorSeats = getNonMajorSeats(nonMajorSubjects);
        List<Subject> topSubjects = getTopSubjects(nonMajorSeats);
        topNonMajorStorage.addAll(topSubjects);
    }

    private Map<Subject, Integer> getNonMajorSeats(List<Subject> nonMajorSubjects) {
        Map<Subject, Integer> nonMajorSeats = new HashMap<>();
        for (Subject subject : nonMajorSubjects) {
            Long subjectId = subject.getId();
            Basket basket = basketRepository.findBySubjectId(subjectId).stream()
                .findAny()
                .orElseThrow(() -> new AllcllException(AllcllErrorCode.BASKET_NOT_FOUND));
            Integer seatCount = basket.getTotRcnt();
            nonMajorSeats.put(subject, seatCount);
        }
        return nonMajorSeats;
    }

    private List<Subject> getTopSubjects(Map<Subject, Integer> nonMajorSeats) {
        return nonMajorSeats.keySet().stream()
            .sorted((o1, o2) -> nonMajorSeats.get(o2).compareTo(nonMajorSeats.get(o1)))
            .limit(SUBJECT_OFFER_COUNT)
            .toList();
    }

    public void sendToCrawlerNonMajorRequest() {
        List<Subject> topNonMajors = topNonMajorStorage.getSubjects();
        ResponseEntity<String> response = crawlerClient.retrieveNonMajor(NonMajorRequest.from(topNonMajors));
        if (SUCCESS_MESSAGE.equals(response.getBody())) {
            log.info("교양 과목 정보를 전달 성공했습니다.");
        }
    }

    @Scheduled(fixedDelay = 1000 * 60) // TODO: 이전 작업 이후 1분. 주기 합의 필요
    public void sendWantPinSubjectIdsToCrawler() {
        PinSubjectsRequest request = getPinSubjects();
        ResponseEntity<String> response = crawlerClient.retrieveWantPinSubjectsRequest(request);
        if (SUCCESS_MESSAGE.equals(response.getBody())) {
            log.info("pin 과목 정보를 전달 성공했습니다.");
        }
    }

    private PinSubjectsRequest getPinSubjects() {
        List<String> tokens = sseEmitterStorage.getUserTokens();
        Map<Subject, Integer> pinSubjects = new HashMap<>();
        for (String token : tokens) {
            List<Pin> pins = pinRepository.findAllByToken(token);
            for (Pin pin : pins) {
                Subject subject = pin.getSubject();
                pinSubjects.merge(subject, 1, Integer::sum);
            }
        }
        List<PinSubject> wantPinSubjects = getWantPinSubjects(pinSubjects);
        PinSubjectsRequest request = PinSubjectsRequest.from(wantPinSubjects);
        return request;
    }

    private List<PinSubject> getWantPinSubjects(Map<Subject, Integer> pinSubjects) {
        List<Long> subjectIds = pinSubjects.keySet().stream()
            .sorted((o1, o2) -> pinSubjects.get(o2).compareTo(pinSubjects.get(o1)))
            .map(Subject::getId)
            .toList();

        int mapSize = pinSubjects.size();
        int firstIdx = mapSize / 3;
        int secondIdx = mapSize * 2 / 3;

        List<PinSubject> firstPrioritySubject = getPrioritySubject(subjectIds.subList(0, firstIdx), 1);
        List<PinSubject> secondPrioritySubject = getPrioritySubject(subjectIds.subList(firstIdx, secondIdx), 2);
        List<PinSubject> thirdPrioritySubject = getPrioritySubject(subjectIds.subList(secondIdx, subjectIds.size()), 3);
        firstPrioritySubject.addAll(secondPrioritySubject);
        firstPrioritySubject.addAll(thirdPrioritySubject);
        return firstPrioritySubject;
    }

    private List<PinSubject> getPrioritySubject(List<Long> subjectIds, int priority) {
        return subjectIds.stream()
            .map(subjectId -> new PinSubject(subjectId, priority))
            .toList();
    }
}
