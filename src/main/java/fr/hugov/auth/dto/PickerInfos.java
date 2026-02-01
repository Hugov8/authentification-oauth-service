package fr.hugov.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Serdeable
@Getter @Setter
@NoArgsConstructor
public class PickerInfos {
    private String token;
    private String clientId;
    private String apiKey;
}
