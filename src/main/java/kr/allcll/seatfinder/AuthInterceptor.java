package kr.allcll.seatfinder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getCookies() == null) {
            response.addCookie(new Cookie(TOKEN_KEY, TokenProvider.createToken()));
            return true;
        }
        Cookie newCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(TOKEN_KEY))
            .findAny()
            .orElse(new Cookie(TOKEN_KEY, TokenProvider.createToken()));
        response.addCookie(newCookie);
        return true;
    }
}
