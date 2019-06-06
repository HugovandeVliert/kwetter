package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24; // 24 Hours

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    private LocalDateTime expiryDate;

    public VerificationToken() {
        this.expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION);
    }

    public boolean isExpired() {
        return this.expiryDate.isBefore(LocalDateTime.now());
    }
}
