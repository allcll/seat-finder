package kr.allcll.seatfinder.basket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.allcll.seatfinder.TimeEntity;
import kr.allcll.seatfinder.subject.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Basket extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "semester_at")
    private String semesterAt;

    @Column(name = "smt_cd")
    private String smtCd;

    @Column(name = "`year`")
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

    public Basket(Subject subject, String semesterAt, String smtCd, String year, String curiNo, String className,
        String studentDiv, String studentDivNm, String studentDeptCd, String studentDeptCdNm, String deptCd,
        String orgnClsfCd, Integer totLimitRcnt, Integer outLimitRcnt, Integer totOutRcnt, Integer remainOutRcnt,
        Integer remainOtherRcnt, Integer rcnt, Integer totRcnt) {
        this.subject = subject;
        this.semesterAt = semesterAt;
        this.smtCd = smtCd;
        this.year = year;
        this.curiNo = curiNo;
        this.className = className;
        this.studentDiv = studentDiv;
        this.studentDivNm = studentDivNm;
        this.studentDeptCd = studentDeptCd;
        this.studentDeptCdNm = studentDeptCdNm;
        this.deptCd = deptCd;
        this.orgnClsfCd = orgnClsfCd;
        this.totLimitRcnt = totLimitRcnt;
        this.outLimitRcnt = outLimitRcnt;
        this.totOutRcnt = totOutRcnt;
        this.remainOutRcnt = remainOutRcnt;
        this.remainOtherRcnt = remainOtherRcnt;
        this.rcnt = rcnt;
        this.totRcnt = totRcnt;
    }
}
