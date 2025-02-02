package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;
import kr.allcll.seatfinder.subject.Subject;

public record EachSubjectBasket(
    Long subjectId,
    String subjectName,
    String departmentName,
    String departmentCode,
    String subjectCode,
    String classCode,
    String professorName,
    Integer totalCount,
    List<DepartmentRegisters> departmentRegisters
) {

    public static EachSubjectBasket from(Subject subject, List<Basket> baskets) {
        return new EachSubjectBasket(
            subject.getId(),
            subject.getSubjectName(),
            subject.getOfferingDepartment(),
            subject.getDeptCd(),
            subject.getSubjectCode(),
            subject.getClassCode(),
            subject.getProfessorName(),
            baskets.getFirst().getTotRcnt(),
            DepartmentRegisters.from(baskets)
        );
    }
}
