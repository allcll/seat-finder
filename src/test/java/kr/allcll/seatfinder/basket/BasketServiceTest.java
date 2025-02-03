package kr.allcll.seatfinder.basket;

import static kr.allcll.seatfinder.support.fixture.SubjectFixture.createSubject;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class BasketServiceTest {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketRepository.deleteAllInBatch();
        subjectRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("전체 관심과목 기능의 정상 동작을 확인한다.")
    void getAllSubjects() {
        // given
        Subject subjectA = createSubject("과목A", "001234", "001", "3210");
        Subject subjectB = createSubject("과목B", "004321", "002", "3210");
        subjectRepository.saveAll(List.of(subjectA, subjectB));
        basketRepository.save(createBasket(subjectA, "본교생", "전자정보통신공학과", 1));
        basketRepository.save(createBasket(subjectB, "본교생", "에너지자원공학과", 1));
        int expectedSize = 2;

        // when
        BasketsResponse allSubjects = basketService.getAllSubjects();

        // then
        assertThat(allSubjects.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("전체 관심과목 조회를 했을 시 각 과목 조회를 확인한다.")
    void getEachSubject() {
        // given
        Subject subjectA = createSubject("과목A", "001234", "001", "3210");
        subjectRepository.save(subjectA);
        basketRepository.save(createBasket(subjectA, "본교생", "전자정보통신공학과", 1));
        basketRepository.save(createBasket(subjectA, "본교생", "컴퓨터공학과", 1));
        int expectedSize = 2;

        // when
        BasketsResponse allSubjects = basketService.getAllSubjects();

        // then
        assertThat(allSubjects.baskets().getFirst().departmentRegisters()).hasSize(expectedSize);
    }

    private Basket createBasket(
        Subject subject,
        String studentBelong,
        String registerDepartment,
        Integer eachCount
    ) {
        return new Basket(subject, "", "", "", "", "", "", studentBelong, "", registerDepartment, "", "", 200, 0, 0, 0,
            0, eachCount, 10);
    }
}
