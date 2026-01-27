package fr.hugov.auth.service;

import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.model.User;
import fr.hugov.auth.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class GoogleApiTokenServiceImpl implements GoogleApiTokenService {

    @Inject
    private UserRepository userRepository;
    //TODO créer ou retrouver le client pour refresh token

    @Override
    public String getValidToken(String userId) throws UserNotFoundException{
        //TODO prévoir la logique refresh token
        return userRepository.findById(userId).map(User::getAccessToken).orElseThrow(() -> new UserNotFoundException("L'user d'id " + userId + "n'a pas été trouvé", null));
    }
    
}
