package fr.hugov.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Serdeable(naming = SnakeCaseStrategy.class)
public class GoogleUserInfo {
    private String name;
    private String id;
    private String picture;
    private String givenName;

    @JsonCreator
    public GoogleUserInfo(String name, String id, String picture, String givenName) {
        this.name = name;
        this.id = id;
        this.picture = picture;
        this.givenName = givenName;
    }
}
