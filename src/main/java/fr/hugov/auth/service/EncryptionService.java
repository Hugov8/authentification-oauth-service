package fr.hugov.auth.service;

import fr.hugov.auth.exception.EncryptionException;
import fr.hugov.auth.model.EncryptedToken;

public interface EncryptionService {
    EncryptedToken encrypt(String data) throws EncryptionException;
    String decrypt(EncryptedToken token) throws EncryptionException;
}
