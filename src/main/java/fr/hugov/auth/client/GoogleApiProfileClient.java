package fr.hugov.auth.client;

import org.reactivestreams.Publisher;

import fr.hugov.auth.dto.GoogleUserInfo;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

@Client("https://www.googleapis.com")
public interface GoogleApiProfileClient {
    @Get("/oauth2/v2/userinfo")
    Publisher<GoogleUserInfo> getUser(@Header(HttpHeaders.AUTHORIZATION) String authString);
}