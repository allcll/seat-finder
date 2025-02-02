package kr.allcll.seatfinder.basket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.allcll.seatfinder.TimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Basket extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_id")
    private Object subjectId;

    @Column(name = "semester_at")
    private String semesterAt;

    @Column(name = "smt_cd")
    private String smtCd;

    @Column(name = "year")
    private String year;

    @Column(name = "curi_no")
    private String curiNo;

    @Column(name = "class")
    private String className;

    @Column(name = "student_div")
    private String studentDiv;

    @Column(name = "student_div_nm")
    private String studentDivNm;

    @Column(name = "student_dept_cd")
    private String studentDeptCd;

    @Column(name = "student_dept_cd_nm")
    private String studentDeptCdNm;

    @Column(name = "dept_cd")
    private String deptCd;

    @Column(name = "orgn_clsf_cd")
    private String orgnClsfCd;

    @Column(name = "tot_limit_rcnt")
    private Integer totLimitRcnt;

    @Column(name = "out_limit_rcnt")
    private Integer outLimitRcnt;

    @Column(name = "tot_out_rcnt")
    private Integer totOutRcnt;

    @Column(name = "remain_out_rcnt")
    private Integer remainOutRcnt;

    @Column(name = "remain_other_rcnt")
    private Integer remainOtherRcnt;

    @Column(name = "rcnt")
    private Integer rcnt;

    @Column(name = "tot_rcnt")
    private Integer totRcnt;

}
