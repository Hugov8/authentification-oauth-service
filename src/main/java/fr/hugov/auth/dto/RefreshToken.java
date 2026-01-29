package fr.hugov.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Serdeable(naming = SnakeCaseStrategy.class)
public class RefreshToken {
    private String accessToken;
    private Integer expiresIn;
}
