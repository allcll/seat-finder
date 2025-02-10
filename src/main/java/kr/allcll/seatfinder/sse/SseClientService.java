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
import kr.allcll.seatfinder.crawler.ExternalProperties;
import kr.allcll.seatfinder.exception.AllcllErrorCode;
import kr.allcll.seatfinder.exception.AllcllException;
import kr.allcll.seatfinder.seat.PinRemainSeat;
import kr.allcll.seatfinder.seat.Seat;
import kr.allcll.seatfinder.seat.SeatStorage;
import kr.allcll.seatfinder.subject.Subject;
import kr.allcll.seatfinder.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(ExternalProperties.class)
public class SseClientService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SeatStorage seatStorage;
    private final SubjectRepository subjectRepository;
    private final ExternalProperties externalProperties;

    @Retryable(
        maxAttempts = 5,
        backoff = @Backoff(delay = 1000)
    )
    public void getSseData() {
        try {
            URL url = new URI(externalProperties.host() + externalProperties.connectionPath()).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/event-stream");
            connection.setDoOutput(true);

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
