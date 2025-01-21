package kr.allcll.seatfinder.pin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import kr.allcll.seatfinder.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PinApi {

    private static final String TOKEN_KEY = "token";
    private final PinService pinService;

    @PostMapping("/api/pin")
    public ResponseEntity<Void> pinSubject(@RequestParam Long subjectId, HttpServletRequest request,
        HttpServletResponse response) {
        Cookie userCookie = getCookieFromRequest(request);
        response.addCookie(userCookie);
        pinService.addPinAtSubject(subjectId, userCookie.getValue());
        return ResponseEntity.ok().build();
    }

    private Cookie getCookieFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return new Cookie(TOKEN_KEY, TokenProvider.createToken());
        }
        return Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(TOKEN_KEY))
            .findAny()
            .orElse(new Cookie(TOKEN_KEY, TokenProvider.createToken()));
    }
}
