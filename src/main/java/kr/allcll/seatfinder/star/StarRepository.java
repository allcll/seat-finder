package kr.allcll.seatfinder.star;

import java.util.List;
import kr.allcll.seatfinder.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {

    boolean existsBySubjectAndToken(Subject subject, String token);

    List<Star> findAllByToken(String token);

    Long countAllByToken(String token);

    void deleteStarBySubjectAndToken(Subject subject, String token);
}
