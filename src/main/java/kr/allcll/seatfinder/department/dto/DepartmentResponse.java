package kr.allcll.seatfinder.department.dto;

import kr.allcll.seatfinder.department.Department;

public record DepartmentResponse(
    String departmentName,
    String departmentCode
) {

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(department.getDeptDegree(), department.getDeptCd());
    }
}
