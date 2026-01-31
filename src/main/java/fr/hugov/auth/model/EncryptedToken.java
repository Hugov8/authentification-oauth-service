package fr.hugov.auth.model;

import java.time.Instant;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Entity
@NoArgsConstructor
@ToString
@Serdeable
@Table(name = "encrypted_token")
public class EncryptedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "encrypted_token_id_seq")
    @SequenceGenerator(name = "encrypted_token_id_seq", sequenceName = "encrypted_token_id_seq ", allocationSize = 1)
    private Long id;
    @Lob private String encryptedToken;
    private String iv;
    private String cryptoVersion;
    private String keyId;
    private Instant createdAt;
    private Instant lastUsedAt;
}
