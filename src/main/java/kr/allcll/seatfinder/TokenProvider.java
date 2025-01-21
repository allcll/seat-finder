package kr.allcll.seatfinder;

import java.util.UUID;

public class TokenProvider {

    public static String createToken() {
        return UUID.randomUUID().toString();
    }

}
