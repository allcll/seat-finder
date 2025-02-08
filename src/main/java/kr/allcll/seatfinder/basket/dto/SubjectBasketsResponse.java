package kr.allcll.seatfinder.basket.dto;

import java.util.List;
import kr.allcll.seatfinder.basket.Basket;

public record SubjectBasketsResponse(
    Long everytimeLectureId,
    List<EachDepartmentBasket> eachDepartmentRegisters
) {

    public static SubjectBasketsResponse from(Long everytimeLectureId, List<Basket> baskets) {
        List<EachDepartmentBasket> result = EachDepartmentBasket.from(baskets);
        return new SubjectBasketsResponse(everytimeLectureId, result);
    }
}
