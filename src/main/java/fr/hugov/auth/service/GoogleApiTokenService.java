package fr.hugov.auth.service;

import fr.hugov.auth.exception.UserNotFoundException;

public interface GoogleApiTokenService {
    String getValidToken(String userId) throws UserNotFoundException;
}
