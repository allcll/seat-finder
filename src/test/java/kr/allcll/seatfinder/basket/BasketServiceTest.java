package kr.allcll.seatfinder.basket;

import static kr.allcll.seatfinder.support.fixture.SubjectFixture.createSubject;
import static kr.allcll.seatfinder.support.fixture.SubjectFixture.createSubjectWithDepartmentCode;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsEachSubject;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import kr.allcll.seatfinder.basket.dto.SubjectBasketsResponse;
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
        BasketsResponse allSubjects = basketService.findBasketsByCondition(null, null, null);

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
        int expectedSize = 1;

        // when
        BasketsResponse allSubjects = basketService.findBasketsByCondition(null, null, null);

        // then
        assertThat(allSubjects.baskets()).hasSize(expectedSize);
    }

    private Basket createBasket(
        Subject subject,
        String studentBelong,
        String registerDepartment,
        Integer eachCount
    ) {
        return new Basket(subject, "", "", "", "", "", studentBelong,
            "", registerDepartment, "", "", 200, 0, 0,
            0, 0, eachCount, 10);
    }

    @Test
    @DisplayName("학과 코드로 검색했을 시 관심 과목 조회를 확인한다.")
    void departmentCodeSelect() {
        // given
        saveSubject();
        int expectedSize = 2;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            "3210",
            null,
            null);

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("교수명으로 검색했을 시 관심 과목 조회를 확인한다.")
    void professorSelect() {
        // given
        saveSubject();
        int expectedSize = 3;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            null,
            "김보예",
            null);

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("과목명으로 검색했을 시 관심 과목 조회를 확인한다.")
    void subjectNameSelect() {
        // given
        saveSubject();
        int expectedSize = 1;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            null,
            null,
            "컴공 과목A");

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("과목명과 교수명으로 검색했을 시 관심 과목 조회를 확인한다.")
    void subjectNameAndProfessorSelect() {
        // given
        saveSubject();
        int expectedSize = 1;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            null,
            "김보예",
            "컴공 과목A");

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("과목명과 학과코드로 검색했을 시 관심 과목 조회를 확인한다.")
    void subjectNameAndDepartmentCodeSelect() {
        // given
        saveSubject();
        int expectedSize = 1;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            "3210",
            null,
            "컴공 과목A");

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("과목명과 학과코드와 교수명으로 검색했을 시 관심 과목 조회를 확인한다.")
    void subjectNameAndDepartmentCodeAndProfessorNameSelect() {
        // given
        saveSubject();
        int expectedSize = 1;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            "3210",
            "김보예",
            "컴공 과목B");

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("잘못된 조건으로 검색 시 아무것도 조회되지 않음을 확인한다.")
    void wrongSelect() {
        // given
        saveSubject();
        int expectedSize = 0;

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(
            "3211",
            "김보예",
            "컴공 과목B");

        // then
        assertThat(basketsByCondition.baskets()).hasSize(expectedSize);
    }

    private void saveSubject() {
        Subject subjectA = createSubjectWithDepartmentCode("컴공 과목A", "001234", "001", "김보예", "3210");
        Subject subjectB = createSubjectWithDepartmentCode("컴공 과목B", "004321", "001", "김보예", "3210");
        Subject subjectC = createSubjectWithDepartmentCode("소웨 과목C", "004321", "001", "김수민", "3211");
        Subject subjectD = createSubjectWithDepartmentCode("소웨 과목D", "001234", "001", "김보예", "3211");
        subjectRepository.saveAll(
            List.of(
                subjectA,
                subjectB,
                subjectC,
                subjectD
            )
        );
        Basket basketA = createBasket(subjectA, "본교생", "컴공", 10);
        Basket basketB = createBasket(subjectA, "본교생", "전정통", 3);
        Basket basketC = createBasket(subjectB, "본교생", "컴공", 4);
        Basket basketD = createBasket(subjectC, "본교생", "전정통", 5);
        Basket basketE = createBasket(subjectD, "본교생", "소웨", 6);

        basketRepository.saveAll(
            List.of(
                basketA,
                basketB,
                basketC,
                basketD,
                basketE
            )
        );
    }

    @Test
    @DisplayName("각 과목에 대한 관심과목 조회를 확인한다.")
    public void getEachSubjectBasketsTest() {
        // given
        Subject subjectA = createSubjectWithDepartmentCode("컴공 과목A", "001234", "001", "김보예", "3210");
        subjectRepository.save(subjectA);
        Basket basketA = createBasket(subjectA, "본교생", "컴공", 10);
        Basket basketB = createBasket(subjectA, "본교생", "전정통", 3);
        basketRepository.saveAll(List.of(basketA, basketB));
        int expected = 2;

        // when
        SubjectBasketsResponse eachSubjectBaskets = basketService.getEachSubjectBaskets(subjectA.getId());

        // then
        assertThat(eachSubjectBaskets.eachDepartmentRegisters()).hasSize(expected);

    }

    @Test
    @DisplayName("각 과목에 대한 관심과목 조회의 응답값을 확인한다.")
    public void getEachSubjectBasketResponse() {
        // given
        Subject subjectA = createSubjectWithDepartmentCode("컴공 과목A", "001234", "001", "김보예", "3210");
        subjectRepository.save(subjectA);
        Basket basketA = createBasket(subjectA, "본교생", "컴공", 10);
        basketRepository.save(basketA);

        // when
        SubjectBasketsResponse response = basketService.getEachSubjectBaskets(subjectA.getId());

        // then
        assertThat(response.eachDepartmentRegisters().getFirst().studentBelong()).isEqualTo("본교생");
        assertThat(response.eachDepartmentRegisters().getFirst().registerDepartment()).isEqualTo("컴공");
        assertThat(response.eachDepartmentRegisters().getFirst().eachCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("관심과목 인원이 0명 일 때의 동작을 확인한다.")
    public void emptyBasket() {
        // given
        Subject subjectA = createSubjectWithDepartmentCode("컴공 과목A", "001234", "001", "김보예", "3210");
        subjectRepository.save(subjectA);

        // when
        BasketsResponse basketsByCondition = basketService.findBasketsByCondition(null, null, null);

        //then
        List<BasketsEachSubject> baskets = basketsByCondition.baskets();
        assertThat(baskets.getFirst().totalCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("관심과목 인원이 0명 일 때 과목 조회를 확인한다.")
    public void emptyBasketSubject() {
        // given
        Subject subjectA = createSubjectWithDepartmentCode("컴공 과목A", "001234", "001", "김보예", "3210");
        subjectRepository.save(subjectA);

        // when
        SubjectBasketsResponse eachSubjectBaskets = basketService.getEachSubjectBaskets(subjectA.getId());

        // then
        System.out.println("eachSubjectBaskets = " + eachSubjectBaskets);
    }
}
