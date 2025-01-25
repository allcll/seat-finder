package kr.allcll.seatfinder;

import java.util.UUID;

public class TokenProvider {

    public static String create() {
        return UUID.randomUUID().toString();
    }

}
