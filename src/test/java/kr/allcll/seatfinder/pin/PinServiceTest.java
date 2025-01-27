package kr.allcll.seatfinder.pin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class PinServiceTest {

    private static final int MAX_PIN_NUMBER = 5;
    private static final String PIN_REPOSITORY_FIND_ERROR_MESSAGE = "핀에 등록된 과목이 아닙니다.";
    private static final String EXIST_PIN_ERROR_MESSAGE = "이미 핀 등록된 과목 입니다.";
    private static final String OVER_PIN_COUNT_ERROR_MESSAGE = String.format("이미 %d개의 핀을 등록했습니다.", MAX_PIN_NUMBER);

    @Autowired
    private PinService pinService;

    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private static final String TOKEN = "token";

    @BeforeEach
    @Transactional
    void setUp() {
        pinRepository.deleteAllInBatch();
        subjectRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("핀이 5개 미만일 경우 정상 등록을 검증한다.")
    void addPinOnSubject() {
        // given
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subjectA);
        pinService.addPinOnSubject(subjectA.getId(), TOKEN);

        // when
        List<Pin> result = pinRepository.findAllByToken(TOKEN);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("핀이 5개 이상일 경우 예외를 검증한다.")
    void canNotAddPinOnSubject() {
        // given
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("운영체제", "003279", "001", "김수민");
        Subject subjectC = createSubject("자료구조", "003280", "001", "김봉케");
        Subject subjectD = createSubject("알고리즘", "003281", "001", "오현지");
        Subject subjectE = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject overCountSubject = createSubject("컴퓨터구조", "003278", "002", "전유채");
        subjectRepository.saveAll(List.of(subjectA, subjectB, subjectC, subjectD, subjectE, overCountSubject));
        pinRepository.saveAll(
            List.of(
                new Pin(TOKEN, subjectA),
                new Pin(TOKEN, subjectB),
                new Pin(TOKEN, subjectC),
                new Pin(TOKEN, subjectD),
                new Pin(TOKEN, subjectE))
        );

        // when, then
        assertThatThrownBy(() -> pinService.addPinOnSubject(overCountSubject.getId(), TOKEN))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(OVER_PIN_COUNT_ERROR_MESSAGE);
    }


    @Test
    @DisplayName("이미 핀 등록된 과목일 경우 예외를 검증한다.")
    void alreadyExistPinSubject() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        pinRepository.save(new Pin(TOKEN, subject));

        // when, then
        assertThatThrownBy(() -> pinService.addPinOnSubject(subject.getId(), TOKEN))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(EXIST_PIN_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("핀의 삭제를 검증한다.")
    void deletePin() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        pinRepository.save(new Pin(TOKEN, subject));

        // when
        pinService.deletePinOnSubject(subject.getId(), TOKEN);

        // then
        assertThat(pinRepository.findAllByToken(TOKEN)).hasSize(0);
    }

    @Test
    @DisplayName("등록되지 않은 핀의 삭제에 대한 예외를 검증한다.")
    void deleteNotExistPin() {
        // given
        String token = "token";
        Subject pinnedSubject = createSubject("핀과목", "123456", "001", "김보예");
        Subject notPinnedSubject = createSubject("핀 안된 과목", "654321", "001", "김주환");
        subjectRepository.saveAll(List.of(pinnedSubject, notPinnedSubject));
        pinRepository.save(new Pin(token, pinnedSubject));

        // when, then
        assertThatThrownBy(() -> pinService.deletePinOnSubject(notPinnedSubject.getId(), token))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(PIN_REPOSITORY_FIND_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 토큰에 대한 예외를 검증한다.")
    void deleteNotExistToken() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);

        // when, then
        assertThatThrownBy(() -> pinService.deletePinOnSubject(subject.getId(), TOKEN))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(PIN_REPOSITORY_FIND_ERROR_MESSAGE);
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
