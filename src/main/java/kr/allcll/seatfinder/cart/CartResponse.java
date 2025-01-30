package kr.allcll.seatfinder.cart;

import java.util.List;

public record CartResponse(
    List<Cart> carts
) {

    public static CartResponse from(List<Cart> allCart) {
        return new CartResponse(allCart);
    }
}
