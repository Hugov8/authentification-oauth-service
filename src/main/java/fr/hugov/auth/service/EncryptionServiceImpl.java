package fr.hugov.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fr.hugov.auth.exception.EncryptionException;
import fr.hugov.auth.model.EncryptedToken;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class EncryptionServiceImpl implements EncryptionService {

    private static final String ALGO = "AES/GCM/NoPadding";
    private static final String CRYPTO_VERSION = "AES_v1";
    private static final int TAG_LENGTH = 128;
    private static final int IV_SIZE = 12;
    private final SecretKey key;

    public EncryptionServiceImpl(@Value("${application.encryption.service.key}") String secret) {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha256.digest(secret.getBytes(StandardCharsets.UTF_8));
            this.key = new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Impossible d'initialiser le service de chiffrement", e);
        }
    }


    @Override
    public EncryptedToken encrypt(String data) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            
            byte[] iv = generateIv();
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH,  iv));
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            
            Encoder b64Encoder = Base64.getEncoder();
            EncryptedToken encryptedToken = new EncryptedToken();
            encryptedToken.setEncryptedToken(b64Encoder.encodeToString(encrypted));
            encryptedToken.setIv(b64Encoder.encodeToString(iv));
            encryptedToken.setCryptoVersion(ALGO);
            encryptedToken.setKeyId(CRYPTO_VERSION);
            encryptedToken.setCreatedAt(Instant.now());
            return encryptedToken;
        } catch (Exception e) {
            throw new EncryptionException("Problème lors du chiffrement de données", e);
        }
        
    }

    @Override
    public String decrypt(EncryptedToken token) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, Base64.getDecoder().decode(token.getIv())));
            token.setLastUsedAt(Instant.now());
            return new String(cipher.doFinal(Base64.getDecoder().decode(token.getEncryptedToken())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Problème lors du déchiffrement de données", e);
        }
    }

    public static byte[] generateIv() {
        byte[] iv = new byte[IV_SIZE]; // 96 bits pour AES/GCM
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
    
}
