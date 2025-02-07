package kr.allcll.seatfinder.support.fixture;

import kr.allcll.seatfinder.basket.Basket;
import kr.allcll.seatfinder.subject.Subject;

public class BasketFixture {

    public static Basket createEmptyBasket(Subject subject) {
        return new Basket(subject, null, null, "", "", null, null,
            null, null, null, null, 99, 0, 0,
            0, 99, null, 0);
    }
}
