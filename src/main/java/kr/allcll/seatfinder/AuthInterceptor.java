package kr.allcll.seatfinder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (hasNoToken(request)) {
            ThreadLocalHolder.SHARED_TOKEN.set(TokenProvider.create());
            response.addCookie(new Cookie(TOKEN_KEY, ThreadLocalHolder.SHARED_TOKEN.get()));
            return true;
        }
        ThreadLocalHolder.SHARED_TOKEN.set(findTokenFromCookie(request));
        return true;
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
            .orElseThrow(() -> new AllcllException(AllcllErrorCode.TOKEN_NOT_FOUND))
            .getValue();
    }
}
