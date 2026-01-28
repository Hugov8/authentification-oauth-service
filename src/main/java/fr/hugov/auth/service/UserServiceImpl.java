package fr.hugov.auth.service;

import java.util.Optional;

import org.reactivestreams.Publisher;

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
    @Inject
    private UserRepository userRepository;
    @Inject
    private GoogleApiProfileClient googleApiProfileClient;
    //TODO créer ou retrouver le client pour refresh token

    @Override
    public String getValidToken(String userId) throws UserNotFoundException{
        //TODO prévoir la logique refresh token
        return userRepository.findById(userId).map(User::getAccessToken).orElseThrow(() -> new UserNotFoundException("L'user d'id " + userId + "n'a pas été trouvé", null));
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

            user.setAccessToken(tokenResponse.getAccessToken());
            user.setExpiresInDate(tokenResponse.getExpiresInDate().orElse(null));
            user.setName(googleUser.getName());
            if (tokenResponse.getRefreshToken() != null) {
                user.setRefreshToken(tokenResponse.getRefreshToken());
            }
            user.setScope(tokenResponse.getScope());
            user.setTokenType(tokenResponse.getTokenType());

            return update ? userRepository.update(user) : userRepository.save(user);
        }) ;
        
    }
    
}
