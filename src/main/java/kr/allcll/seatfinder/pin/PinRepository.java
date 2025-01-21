package kr.allcll.seatfinder.pin;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {

    List<Pin> findAllByTokenId(String tokenId);
}
