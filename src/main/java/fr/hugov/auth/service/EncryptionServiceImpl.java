package fr.hugov.auth.service;

import jakarta.inject.Singleton;

@Singleton
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String data) {
        return data;
    }

    @Override
    public String decrypt(String data) {
        return data;
    }
    
}
