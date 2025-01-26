package kr.allcll.seatfinder.pin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
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

    private static final String NOT_REACH_MAX_TOKEN = "tokenIdNotReachMax";
    private static final String REACH_MAX_TOKEN = "tokenIdReachMax";

    @Autowired
    private PinService pinService;

    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EntityManager entityManager;

    private List<Subject> subjects;

    @BeforeEach
    void setUp() {
        pinRepository.deleteAllInBatch();
        subjectRepository.deleteAllInBatch();
        entityManager.createNativeQuery("ALTER TABLE subject ALTER COLUMN id RESTART WITH 1").executeUpdate();
        subjects = saveSixSubject();
    }

    @Test
    @DisplayName("핀이 5개 미만일 경우 정상 등록을 검증한다.")
    @Transactional
    void addPinOnSubject() {
        // given
        pinService.addPinOnSubject(1L, NOT_REACH_MAX_TOKEN);

        // when
        List<Pin> result = pinRepository.findAllByToken(NOT_REACH_MAX_TOKEN);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("핀이 5개 이상일 경우 예외를 검증한다.")
    @Transactional
    void canNotAddPinOnSubject() {
        // given
        saveFivePinToMaxToken(subjects);

        // then
        assertThatThrownBy(() -> pinService.addPinOnSubject(6L, REACH_MAX_TOKEN))
            .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("이미 핀 등록된 과목일 경우 예외를 검증한다.")
    @Transactional
    void alreadyExistPinSubject() {
        // given
        pinService.addPinOnSubject(1L, NOT_REACH_MAX_TOKEN);

        // then
        assertThatThrownBy(() -> pinService.addPinOnSubject(1L, NOT_REACH_MAX_TOKEN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("핀의 삭제를 검증한다.")
    @Transactional
    void deletePin() {
        // given
        saveFivePinToMaxToken(subjects);

        // when
        pinService.deletePinOnSubject(1L, REACH_MAX_TOKEN);

        // then
        assertThat(pinRepository.findAllByToken(REACH_MAX_TOKEN)).hasSize(4);
    }

    @Test
    @DisplayName("등록되지 않은 핀의 삭제에 대한 예외를 검증한다.")
    @Transactional
    void deleteNotExistPin() {
        // given
        saveFivePinToMaxToken(subjects);

        // when, then
        assertThatThrownBy(() -> pinService.deletePinOnSubject(6L, REACH_MAX_TOKEN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 토큰에 대한 예외를 검증한다.")
    @Transactional
    void deleteNotExistToken() {
        // when, then
        assertThatThrownBy(() -> pinService.deletePinOnSubject(1L, NOT_REACH_MAX_TOKEN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private void saveFivePinToMaxToken(List<Subject> subjects) {
        pinRepository.saveAll(
            List.of(
                new Pin(REACH_MAX_TOKEN, subjects.get(0)),
                new Pin(REACH_MAX_TOKEN, subjects.get(1)),
                new Pin(REACH_MAX_TOKEN, subjects.get(2)),
                new Pin(REACH_MAX_TOKEN, subjects.get(3)),
                new Pin(REACH_MAX_TOKEN, subjects.get(4)))
        );
    }

    private List<Subject> saveSixSubject() {
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("운영체제", "003279", "001", "김수민");
        Subject subjectC = createSubject("자료구조", "003280", "001", "김봉케");
        Subject subjectD = createSubject("알고리즘", "003281", "001", "오현지");
        Subject subjectE = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject subjectF = createSubject("컴퓨터구조", "003278", "003", "김주환");
        return subjectRepository.saveAll(
            List.of(
                subjectA,
                subjectB,
                subjectC,
                subjectD,
                subjectE,
                subjectF)
        );
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
