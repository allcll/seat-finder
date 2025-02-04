package kr.allcll.seatfinder.basket;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import kr.allcll.seatfinder.basket.dto.BasketsDepartmentRegisters;
import kr.allcll.seatfinder.basket.dto.BasketsEachSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(BasketApi.class)
public class BasketApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BasketService basketService;

    @Test
    @DisplayName("관심과목 전체 조회의 요청과 응답을 확인한다.")
    void findAllBasket() throws Exception {
        // given
        String expected = """
            {
                "baskets": [
                    {
                        "subjectId": 1,
                        "subjectName": "컴퓨터구조",
                        "departmentName": "전자정보공학과",
                        "departmentCode": "3210",
                        "subjectCode": "004310",
                        "classCode": "001",
                        "professorName": "김보예",
                        "totalCount": 14,
                        "departmentRegisters": [
                            {
                                "studentBelong": "본교생",
                                "registerDepartment": "전정통",
                                "eachCount": 5
                            },
                            {
                                "studentBelong": "타학교학점교환학생",
                                "registerDepartment": "전정통",
                                "eachCount": 5
                            },
                            {
                                "studentBelong": "본교생",
                                "registerDepartment": "컴공",
                                "eachCount": 4
                            }
                        ]
                    }
                ]
            }
            """;

        // when
        when(basketService.getAllSubjects()).thenReturn(
            new BasketsResponse(List.of(
                new BasketsEachSubject(1L, "컴퓨터구조", "전자정보공학과", "3210", "004310", "001", "김보예", 14,
                    List.of(
                        new BasketsDepartmentRegisters("본교생", "전정통", 5),
                        new BasketsDepartmentRegisters("타학교학점교환학생", "전정통", 5),
                        new BasketsDepartmentRegisters("본교생", "컴공", 4))
                )
            ))
        );
        MvcResult result = mockMvc.perform(get("/api/basket")).andExpect(status().isOk()).andReturn();

        // then
        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(expected);
    }
}
