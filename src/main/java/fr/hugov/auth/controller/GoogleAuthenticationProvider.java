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
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

import org.reactivestreams.Publisher;

import fr.hugov.auth.service.UserService;

@Named("google")
@Singleton
@Slf4j
public class GoogleAuthenticationProvider implements OauthAuthenticationMapper  {
    @Inject
    private UserService userService;

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State arg1) {
        return Publishers.map(userService.saveOrUpdateUser(tokenResponse), (user) -> {
            return AuthenticationResponse.success(user.getId(), Collections.singletonList("SHEET"));
        });
    }
}