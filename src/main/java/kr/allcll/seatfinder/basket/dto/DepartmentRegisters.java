package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;

public record DepartmentRegisters(
    String studentBelong,
    String registerDepartment,
    Integer eachCount
) {

    public static List<DepartmentRegisters> from(List<Basket> baskets) {
        return baskets.stream()
            .map(basket -> new DepartmentRegisters(
                basket.getStudentDivNm(),
                basket.getStudentDeptCdNm(),
                basket.getRcnt()))
            .toList();
    }
}
