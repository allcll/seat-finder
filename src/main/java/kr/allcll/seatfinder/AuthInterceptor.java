package kr.allcll.seatfinder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (hasNoToken(request)) {
            ThreadLocalHolder.SHARED_TOKEN.set(TokenProvider.create());
            return true;
        }
        ThreadLocalHolder.SHARED_TOKEN.set(findTokenFromCookie(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
        if (hasNoToken(request)) {
            response.addCookie(new Cookie(TOKEN_KEY, ThreadLocalHolder.SHARED_TOKEN.get()));
        }
    }

    private boolean hasNoToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return true;
        }
        return Arrays.stream(request.getCookies())
            .noneMatch(cookie -> TOKEN_KEY.equals(cookie.getName()));
    }

    private String findTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> TOKEN_KEY.equals(cookie.getName()))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("쿠키에 토큰이 존재하지 않습니다."))
            .getValue();
    }
}
