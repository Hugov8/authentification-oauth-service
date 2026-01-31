package fr.hugov.auth.model;

import java.util.Date;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "google_user")
public class User {
    @Id
    private String id;
    private String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "access_token_id")
    private @NonNull EncryptedToken accessToken;
    private @NonNull String tokenType;

    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "refresh_token_id")
    private @Nullable EncryptedToken refreshToken;
    private @Nullable String scope;
    private @Nullable Date expiresInDate;

}