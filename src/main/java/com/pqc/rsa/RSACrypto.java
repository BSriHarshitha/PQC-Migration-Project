package com.pqc.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

/**
 * RSA Cryptography Implementation
 */
public class RSACrypto {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private KeyPair keyPair;
    private int keySize;
    
    public RSACrypto(int keySize) {
        this.keySize = keySize;
    }
    
    /**
     * Generate RSA key pair
     */
    public void generateKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(keySize, new SecureRandom());
        this.keyPair = keyGen.generateKeyPair();
        System.out.println("✓ RSA-" + keySize + " keys generated");
    }
    
    /**
     * Encrypt message with public key
     */
    public byte[] encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return cipher.doFinal(message.getBytes());
    }
    
    /**
     * Decrypt ciphertext with private key
     */
    public String decrypt(byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return new String(cipher.doFinal(ciphertext));
    }
    
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
    
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }
    
    public int getKeySize() {
        return keySize;
    }
}
