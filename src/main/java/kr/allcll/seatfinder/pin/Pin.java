package kr.allcll.seatfinder.pin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.allcll.seatfinder.AuditEntity;
import kr.allcll.seatfinder.subject.Subject;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PIN")
@NoArgsConstructor
public class Pin extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private String tokenId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public Pin(String tokenId, Subject subject) {
        this.tokenId = tokenId;
        this.subject = subject;
    }
}
