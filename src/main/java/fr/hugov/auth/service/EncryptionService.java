package fr.hugov.auth.service;

public interface EncryptionService {
    String encrypt(String data);
    String decrypt(String data);
}
