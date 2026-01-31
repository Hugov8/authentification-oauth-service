package fr.hugov.auth.service;

import org.reactivestreams.Publisher;

import fr.hugov.auth.dto.AuthMeInfo;
import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.model.User;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;

public interface UserService {
    Publisher<String> getValidToken(String userId) throws UserNotFoundException;
    Publisher<User> saveOrUpdateUser(TokenResponse tokenResponse);
    Publisher<AuthMeInfo> infosUser(String userId) throws UserNotFoundException;
}
