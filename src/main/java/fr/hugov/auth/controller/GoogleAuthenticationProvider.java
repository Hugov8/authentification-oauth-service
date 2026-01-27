package fr.hugov.auth.controller;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.authentication.*;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import org.reactivestreams.Publisher;

import fr.hugov.auth.dto.GoogleUserInfo;
import fr.hugov.auth.model.User;
import fr.hugov.auth.repository.UserRepository;
import fr.hugov.auth.service.GoogleApiProfileClient;

@Named("google")
@Singleton
public class GoogleAuthenticationProvider implements OauthAuthenticationMapper  {
    private static final Logger log = Logger.getLogger(GoogleAuthenticationProvider.class.getName());

    private static final String PREFIXE_TOKEN = "Bearer ";
    @Inject 
    private GoogleApiProfileClient googlClient;
    @Inject
    private UserRepository userRepository;

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State arg1) {
        Publisher<GoogleUserInfo> googleUserPublisher = googlClient.getUser(PREFIXE_TOKEN + tokenResponse.getAccessToken());
        log.info("Connexion");
        return Publishers.map(googleUserPublisher, (googleUser) -> {
            User user = userRepository.findById(googleUser.getId()).orElse(new User());
            user.setAccessToken(tokenResponse.getAccessToken());
            user.setExpiresInDate(tokenResponse.getExpiresInDate().orElse(null));
            user.setId(googleUser.getId());
            user.setName(googleUser.getName());
            user.setRefreshToken(tokenResponse.getRefreshToken());
            user.setScope(tokenResponse.getScope());
            user.setTokenType(tokenResponse.getTokenType());

            userRepository.save(user);

            return AuthenticationResponse.success(googleUser.getId(), Collections.singletonList("SHEET"), 
                            Map.of("sub", googleUser.getId()));
        });
    }
}