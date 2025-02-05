package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;

public record SubjectBasketsResponse(
    List<EachDepartmentBasket> eachDepartmentRegisters
) {

    public static SubjectBasketsResponse from(List<Basket> baskets) {
        List<EachDepartmentBasket> result = EachDepartmentBasket.from(baskets);
        return new SubjectBasketsResponse(result);
    }
}
