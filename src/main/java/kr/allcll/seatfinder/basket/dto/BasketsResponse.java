package kr.allcll.seatfinder.basket.dto;

import java.util.List;

public record BasketsResponse(
    List<eachSubjectBasket> baskets
) {

}
