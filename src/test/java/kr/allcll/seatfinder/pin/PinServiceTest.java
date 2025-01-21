package kr.allcll.seatfinder.pin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class PinServiceTest {

    @Autowired
    private PinService pinService;

    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        pinRepository.deleteAllInBatch();
        subjectRepository.deleteAllInBatch();
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("운영체제", "003279", "001", "김수민");
        Subject subjectC = createSubject("자료구조", "003280", "001", "김봉케");
        Subject subjectD = createSubject("알고리즘", "003281", "001", "오현지");
        Subject subjectE = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject subjectF = createSubject("컴퓨터구조", "003278", "003", "김주환");
        subjectRepository.saveAll(
            List.of(
                subjectA,
                subjectB,
                subjectC,
                subjectD,
                subjectE,
                subjectF)
        );
        pinRepository.saveAll(
            List.of(
                new Pin(REACH_MAX_TOKEN, subjectA),
                new Pin(REACH_MAX_TOKEN, subjectB),
                new Pin(REACH_MAX_TOKEN, subjectC),
                new Pin(REACH_MAX_TOKEN, subjectD),
                new Pin(REACH_MAX_TOKEN, subjectE))
        );
    }

    private static final String NOT_REACH_MAX_TOKEN = "tokenIdNotReachMax";
    private static final String REACH_MAX_TOKEN = "tokenIdReachMax";

    @Test
    @DisplayName("핀이 5개 미만일 경우 정상 등록을 검증한다.")
    void addPinAtSubject() {
        pinService.addPinAtSubject(1L, NOT_REACH_MAX_TOKEN);
        List<Pin> result = pinRepository.findAllByTokenId(NOT_REACH_MAX_TOKEN);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("핀이 5개 이상일 경우 예외를 검증한다.")
    void canNotAddPinAtSubject() {
        Assertions.assertThatThrownBy(() -> pinService.addPinAtSubject(6L, REACH_MAX_TOKEN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private Subject createSubject(
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return new Subject(null, "", "", subjectCode, classCode, subjectName, "", "", "", "", "", "", "", "", "",
            "", professorName, "", "", "", "", "", "", "");
    }
}