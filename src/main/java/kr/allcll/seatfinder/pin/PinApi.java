package kr.allcll.seatfinder.pin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.allcll.seatfinder.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PinApi {

    private static final String TOKEN_KEY = "token";
    private final PinService pinService;

    @PostMapping("/api/pin")
    public ResponseEntity<Void> addPinOnSubject(@RequestParam Long subjectId,
        @CookieValue(value = "token", required = false) Cookie cookie, HttpServletResponse response) {
        Cookie userCookie = getCookieFromRequest(cookie);
        response.addCookie(userCookie);
        pinService.addPinOnSubject(subjectId, userCookie.getValue());
        return ResponseEntity.ok().build();
    }

    private Cookie getCookieFromRequest(Cookie cookie) {
        if (cookie == null) {
            return new Cookie(TOKEN_KEY, TokenProvider.createToken());
        }
        return cookie;
//
//        return Arrays.stream(cookie)
//            .filter(eachCookie -> eachCookie.getName().equals(TOKEN_KEY))
//            .findAny()
//            .orElse(new Cookie(TOKEN_KEY, TokenProvider.createToken()));
    }
}
