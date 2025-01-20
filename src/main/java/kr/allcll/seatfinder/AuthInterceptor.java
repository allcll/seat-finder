package kr.allcll.seatfinder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie newCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(TOKEN_KEY))
            .findAny()
            .orElse(new Cookie(TOKEN_KEY, createToken()));
        response.addCookie(newCookie);
        return true;
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }
}
