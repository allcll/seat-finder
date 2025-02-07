package kr.allcll.seatfinder.star;

import java.util.List;
import java.util.Optional;
import kr.allcll.seatfinder.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {

    boolean existsBySubjectAndToken(Subject subject, String token);

    List<Star> findAllByToken(String token);

    Optional<Star> findBySubjectAndToken(Subject subject, String token);
}
