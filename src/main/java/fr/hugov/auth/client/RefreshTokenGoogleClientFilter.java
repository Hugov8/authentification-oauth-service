package fr.hugov.auth.client;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import lombok.extern.slf4j.Slf4j;

@ClientFilter("/token/**")
@Slf4j
public class RefreshTokenGoogleClientFilter {
    
    @Value("${micronaut.security.oauth2.clients.google.client-id}") private String clientId;
    @Value("${micronaut.security.oauth2.clients.google.client-secret}") private String clientSecret;
    

    @RequestFilter
    public void filter(MutableHttpRequest<?> request) {
        request.getParameters().add("client_id", clientId).add("client_secret", clientSecret);
    }

}
