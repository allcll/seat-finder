package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;

public record SubjectDepartmentRegisters(
    List<BasketsDepartmentRegisters> basketsDepartmentRegisters
) {

    public static SubjectDepartmentRegisters from(List<Basket> baskets) {
        List<BasketsDepartmentRegisters> result = BasketsDepartmentRegisters.from(baskets);
        return new SubjectDepartmentRegisters(result);
    }
}
