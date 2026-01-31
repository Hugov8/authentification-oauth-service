package fr.hugov.auth.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.reactivestreams.Publisher;

import fr.hugov.auth.client.GoogleApiProfileClient;
import fr.hugov.auth.client.RefreshTokenGoogleClient;
import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.model.User;
import fr.hugov.auth.repository.UserRepository;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String PREFIXE_TOKEN = "Bearer ";
    private static final String REFRESH_TOKEN_PARAM_VALUE = "refresh_token";
    @Inject
    private UserRepository userRepository;
    @Inject
    private GoogleApiProfileClient googleApiProfileClient;
    @Inject
    private EncryptionService encryptionService;
    @Inject
    private RefreshTokenGoogleClient refreshTokenGoogleClient;

    @Override
    public Publisher<String> getValidToken(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
            .map(user -> {
                Instant expirationDate = user.getExpiresInDate().toInstant();
                Instant now = Instant.now();
                if (expirationDate.minusSeconds(120).isBefore(now)) {
                    return refreshToken(user);
                } else {
                    String token = encryptionService.decrypt(user.getAccessToken());
                    userRepository.update(user);
                    return Publishers.just(token);
                }
            }).orElseThrow(() -> new UserNotFoundException("L'user d'id " + userId + "n'a pas été trouvé", null));
    }

    public Publisher<String> refreshToken(User user) {
        String refreshToken = encryptionService.decrypt(user.getRefreshToken());
        return Publishers.map(refreshTokenGoogleClient.token(refreshToken, REFRESH_TOKEN_PARAM_VALUE), newToken -> {
            user.setAccessToken(encryptionService.encrypt(newToken.getAccessToken()));
            user.setExpiresInDate(Date.from(Instant.now().plusSeconds(newToken.getExpiresIn())));
            userRepository.update(user);
            return newToken.getAccessToken();
        });
    }

    @Override
    public Publisher<User> saveOrUpdateUser(TokenResponse tokenResponse) {
        return Publishers.map(googleApiProfileClient.getUser(PREFIXE_TOKEN + tokenResponse.getAccessToken()), googleUser -> {
            User user;
            Optional<User> optUser = userRepository.findById(googleUser.getId());
            boolean update = true;
            if (optUser.isPresent()) {
                user = optUser.get();
            } else {
                update = false;
                user = new User();
                user.setId(googleUser.getId());
            }

            user.setAccessToken(encryptionService.encrypt(tokenResponse.getAccessToken()));
            user.setExpiresInDate(tokenResponse.getExpiresInDate().orElse(null));
            user.setName(googleUser.getName());
            if (tokenResponse.getRefreshToken() != null) {
                user.setRefreshToken(encryptionService.encrypt(tokenResponse.getRefreshToken()));
            }
            user.setScope(tokenResponse.getScope());
            user.setTokenType(tokenResponse.getTokenType());

            return update ? userRepository.update(user) : userRepository.save(user);
        });
        
    }
    
}
