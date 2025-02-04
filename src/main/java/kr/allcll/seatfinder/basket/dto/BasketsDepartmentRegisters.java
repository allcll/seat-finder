package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;

public record BasketsDepartmentRegisters(
    String studentBelong,
    String registerDepartment,
    Integer eachCount
) {

    public static List<BasketsDepartmentRegisters> from(List<Basket> baskets) {
        return baskets.stream()
            .map(basket -> new BasketsDepartmentRegisters(
                basket.getStudentDivNm(),
                basket.getStudentDeptCdNm(),
                basket.getRcnt()))
            .toList();
    }
}
