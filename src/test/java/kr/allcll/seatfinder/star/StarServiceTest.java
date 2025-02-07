package kr.allcll.seatfinder.star;

import static kr.allcll.seatfinder.support.fixture.SubjectFixture.createSubject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
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

    private static final int MAX_STAR_NUMBER = 5;
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
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subjectA);
        starService.addStarOnSubject(subjectA.getId(), TOKEN);

        // when
        List<Star> result = starRepository.findAllByToken(TOKEN);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("즐겨찾기가 5개 이상일 경우 예외를 검증한다.")
    void canNotAddStarOnSubject() {
        // given
        Subject subjectA = createSubject("컴퓨터구조", "003278", "001", "김보예");
        Subject subjectB = createSubject("운영체제", "003279", "001", "김수민");
        Subject subjectC = createSubject("자료구조", "003280", "001", "김봉케");
        Subject subjectD = createSubject("알고리즘", "003281", "001", "오현지");
        Subject subjectE = createSubject("컴퓨터구조", "003278", "002", "전유채");
        Subject overCountSubject = createSubject("컴퓨터구조", "003278", "002", "전유채");
        subjectRepository.saveAll(List.of(subjectA, subjectB, subjectC, subjectD, subjectE, overCountSubject));
        starRepository.saveAll(
            List.of(
                new Star(TOKEN, subjectA),
                new Star(TOKEN, subjectB),
                new Star(TOKEN, subjectC),
                new Star(TOKEN, subjectD),
                new Star(TOKEN, subjectE))
        );

        // when, then
        assertThatThrownBy(() -> starService.addStarOnSubject(overCountSubject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(String.format(AllcllErrorCode.STAR_LIMIT_EXCEEDED.getMessage(), MAX_STAR_NUMBER));
    }

    @Test
    @DisplayName("이미 즐겨찾기 등록된 과목일 경우 예외를 검증한다.")
    void alreadyExistStarSubject() {
        // given
        Subject subject = createSubject("컴퓨터구조", "003278", "001", "김보예");
        subjectRepository.save(subject);
        starRepository.save(new Star(TOKEN, subject));
        String expectExceptionMessage = new AllcllException(AllcllErrorCode.DUPLICATE_STAR, "컴퓨터구조").getMessage();

        // when, then
        assertThatThrownBy(() -> starService.addStarOnSubject(subject.getId(), TOKEN))
            .isInstanceOf(AllcllException.class)
            .hasMessageContaining(expectExceptionMessage);
    }
}
