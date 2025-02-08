package kr.allcll.seatfinder.star;

import static kr.allcll.seatfinder.support.fixture.SubjectFixture.createSubject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.star.dto.StarredSubjectIdsResponse;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class StarServiceTest {

    private static final String TOKEN = "token";

    @Autowired
    private StarService starService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StarRepository starRepository;

    @BeforeEach
    void setUp() {
        starRepository.deleteAllInBatch();
        subjectRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("즐겨찾기 5개 미만일 경우 정상 등록을 검증한다.")
    void addStarOnProject() {
        // given
        int expected = 5;
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("컴퓨터구조", "003278", "002", "김보예");
        Subject subjectC = createSubject("컴퓨터구조", "003278", "003", "김보예");
        Subject subjectD = createSubject("컴퓨터구조", "003278", "004", "김보예");
        Subject starSubject = createSubject("컴퓨터구조", "003278", "005", "김보예");
        subjectRepository.saveAll(List.of(
            subjectA,
            subjectB,
            subjectC,
            subjectD,
            starSubject
        ));
        starRepository.saveAll(List.of(
                new Star(TOKEN, subjectA),
                new Star(TOKEN, subjectB),
                new Star(TOKEN, subjectC),
                new Star(TOKEN, subjectD)
            )
        );
        starService.addStarOnSubject(starSubject.getId(), TOKEN);

        // when
        List<Star> result = starRepository.findAllByToken(TOKEN);

        // then
        assertThat(result).hasSize(expected);
    }

    @Test
    @DisplayName("즐겨찾기가 10개 이상일 경우 예외를 검증한다.")
    void canNotAddStarOnSubject() {
        // given
        int MAX_STAR_NUMBER = 10;
        Subject overCountSubject = createSubject("컴퓨터구조", "003278", "005", "김수민");
        subjectRepository.save(overCountSubject);
        saveSubjectsAndTenStars();

        // when, then
        assertThatThrownBy(() -> starService.addStarOnSubject(overCountSubject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(String.format(AllcllErrorCode.STAR_LIMIT_EXCEEDED.getMessage(), MAX_STAR_NUMBER));
    }

    private void saveSubjectsAndTenStars() {
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("운영체제", "003279", "001", "김수민");
        Subject subjectC = createSubject("자료구조", "003280", "001", "김봉케");
        Subject subjectD = createSubject("알고리즘", "003281", "001", "오현지");
        Subject subjectE = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject subjectF = createSubject("과목6", "003279", "003", "전유채");
        Subject subjectG = createSubject("과목7", "003270", "002", "김주환");
        Subject subjectH = createSubject("운영체제", "113278", "001", "김뽀예");
        Subject subjectI = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject subjectJ = createSubject("자료구조", "003272", "001", "박희준");
        subjectRepository.saveAll(
            List.of(subjectA, subjectB, subjectC, subjectD, subjectE, subjectF,
                subjectG, subjectH, subjectI, subjectJ));
        starRepository.saveAll(
            List.of(
                new Star(TOKEN, subjectA),
                new Star(TOKEN, subjectB),
                new Star(TOKEN, subjectC),
                new Star(TOKEN, subjectD),
                new Star(TOKEN, subjectE),
                new Star(TOKEN, subjectF),
                new Star(TOKEN, subjectG),
                new Star(TOKEN, subjectH),
                new Star(TOKEN, subjectI),
                new Star(TOKEN, subjectJ)
            )
        );
    }

    @Test
    @DisplayName("이미 즐겨찾기 등록된 과목일 경우 예외를 검증한다.")
    void alreadyExistStarSubject() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        starRepository.save(new Star(TOKEN, subject));

        // when, then
        assertThatThrownBy(() -> starService.addStarOnSubject(subject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(new AllcllException(AllcllErrorCode.DUPLICATE_STAR, "컴퓨터구조").getMessage());
    }

    @Test
    @DisplayName("즐겨찾기의 삭제를 검증한다.")
    void deletePin() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        starRepository.save(new Star(TOKEN, subject));

        // when
        starService.deleteStarOnSubject(subject.getId(), TOKEN);

        // then
        assertThat(starRepository.findAllByToken(TOKEN)).hasSize(0);
    }

    @Test
    @DisplayName("등록되지 않은 즐겨찾기를 삭제하면 예외가 발생한다.")
    void deleteNotExistPin() {
        // given
        Subject starSubject = createSubject("즐겨찾기 된 과목", "123456", "001", "김보예");
        Subject notStarSubject = createSubject("즐겨찾기 안된 과목", "654321", "001", "김주환");
        subjectRepository.saveAll(List.of(starSubject, notStarSubject));
        starRepository.save(new Star(TOKEN, starSubject));

        // when, then
        assertThatThrownBy(() -> starService.deleteStarOnSubject(notStarSubject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(AllcllErrorCode.STAR_SUBJECT_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 토큰에 대한 예외를 검증한다.")
    void deleteNotExistToken() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);

        // when, then
        assertThatThrownBy(() -> starService.deleteStarOnSubject(subject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(AllcllErrorCode.STAR_SUBJECT_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("등록된 핀이 존재할 때 조회 기능을 테스트한다.")
    void retrieveExistPins() {
        // given
        int expectedSize = 1;
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        starRepository.save(new Star(TOKEN, subject));

        // when
        StarredSubjectIdsResponse response = starService.retrieveStars(TOKEN);

        // then
        assertThat(response.subjects()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("등록된 핀이 존재할 때 예외가 발생하지 않음을 검증한다.")
    void retrieveStars() {
        // given
        int expectedSize = 0;

        // when
        StarredSubjectIdsResponse response = starService.retrieveStars(TOKEN);

        // then
        assertThat(response.subjects()).hasSize(expectedSize);
    }
}
