package fr.hugov.auth.dto;

import java.time.Instant;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Serdeable
@Getter @Setter
@NoArgsConstructor
public class AuthMeInfo {
    private String user;
    private AuthenticationStatus status;
    private Instant expires;

    public enum AuthenticationStatus {
        AUTHENTIFIED, EXPIRED, ANONYM
    }
}
