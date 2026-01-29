package fr.hugov.auth.client;

import org.reactivestreams.Publisher;

import fr.hugov.auth.dto.RefreshToken;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client("https://oauth2.googleapis.com")
public interface RefreshTokenGoogleClient {
    @Post("/token")
    Publisher<RefreshToken> token(@QueryValue("refresh_token") String refreshToken, @QueryValue("grant_type") String grantType);
}
