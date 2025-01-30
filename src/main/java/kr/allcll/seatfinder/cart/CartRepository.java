package kr.allcll.seatfinder.cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c ORDER BY c.queryTime DESC")
    List<Cart> findTop2411ByOrderByQueryTimeDesc();
}
