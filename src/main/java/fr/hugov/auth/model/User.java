package fr.hugov.auth.model;

import java.util.Date;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    @Lob
    private @NonNull String accessToken;
    private @NonNull String tokenType;
    @Lob
    private @Nullable String refreshToken;
    private @Nullable String scope;
    private @Nullable Date expiresInDate;

}