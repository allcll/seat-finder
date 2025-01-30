package kr.allcll.seatfinder.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "CART")
@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 대상 학년
    private Integer studentYear;

    // 학수 번호 + 분반
    private String curiClass;

    // 과목명
    private String curiName;

    // 학과명
    private String deptCdNm;

    // 수업 시간 ex) 화목16:30-18:00(조재진/세205)
    private String lecTime;

    // 과목 분류
    private String subjectType;

    // 관심 과목 담은 수
    private Integer totRcnt;

    private LocalDateTime queryTime;

    public Cart(Integer studentYear, String curiClass, String curiName, String deptCdNm, String lecTime,
        String subjectType,
        Integer totRcnt, LocalDateTime queryTime) {
        this.studentYear = studentYear;
        this.curiClass = curiClass;
        this.curiName = curiName;
        this.deptCdNm = deptCdNm;
        this.lecTime = lecTime;
        this.subjectType = subjectType;
        this.totRcnt = totRcnt;
        this.queryTime = queryTime;
    }
}
