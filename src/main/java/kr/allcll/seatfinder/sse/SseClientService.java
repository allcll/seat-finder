package kr.allcll.seatfinder.sse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.seat.PinRemainSeat;
import kr.allcll.seatfinder.seat.Seat;
import kr.allcll.seatfinder.seat.SeatStorage;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseClientService {

    private static final String SseClientURL = "/api/";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SeatStorage seatStorage;
    private final SubjectRepository subjectRepository;

    public void getSseData() {
        try {
            // 외부 서버의 SSE 엔드포인트 URL
            URL url = new URI(SseClientURL).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // GET 요청 설정
            connection.setRequestProperty("Accept", "text/event-stream"); // SSE 형식의 응답 요청?
            connection.setDoOutput(true); // 출력 스트림 사용 가능

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                List<PinRemainSeat> pinRemainSeats = parseJson(line);
                for (PinRemainSeat pinRemainSeat : pinRemainSeats) {
                    Subject subject = subjectRepository.findById(pinRemainSeat.getSubjectId())
                        .orElseThrow(() -> new AllcllException(AllcllErrorCode.SUBJECT_NOT_FOUND));
                    seatStorage.add(
                        new Seat(subject, pinRemainSeat.getRemainSeats(), LocalDateTime.now()));
                }
            }
            reader.close();
        } catch (Exception e) {
            log.error("SSE error", e);
        }
    }

    private List<PinRemainSeat> parseJson(String json) throws Exception {
        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }
}
