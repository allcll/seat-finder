package kr.allcll.seatfinder.pin;

import java.util.List;
import java.util.Optional;
import kr.allcll.seatfinder.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {

    List<Pin> findAllByToken(String tokenId);

    Optional<Pin> findBySubjectAndToken(Subject subject, String token);

    boolean existsBySubjectAndToken(Subject subject, String token);
}
