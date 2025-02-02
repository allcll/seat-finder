package kr.allcll.seatfinder.basket.dto;

import java.util.List;

public record eachSubjectBasket(
    Long subjectId,
    String subjectName,
    String departmentName,
    String departmentCode,
    String curiCode,
    String classCode,
    String professorName,
    Integer totalCount,
    List<DepartmentRegisters> departmentRegisters
) {

}
