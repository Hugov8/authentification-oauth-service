package fr.hugov.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Serdeable
public class GoogleUserInfo {
    @JsonProperty("name")
    private String name;
    private String id;
    private String picture;
    @JsonProperty("given_name")
    private String givenName;

    @JsonCreator
    public GoogleUserInfo(String name, String id, String picture, String givenName) {
        this.name = name;
        this.id = id;
        this.picture = picture;
        this.givenName = givenName;
    }
}
