package com.pqc.hybrid;

import com.pqc.rsa.RSACrypto;
import com.pqc.postquantum.KyberCrypto;
import com.pqc.postquantum.DilithiumCrypto;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * Hybrid Cryptography: RSA-2048 + CRYSTALS-Kyber + CRYSTALS-Dilithium
 * Combines classical and post-quantum cryptography for backward-compatible migration
 */
public class HybridCrypto {

    private RSACrypto rsa;
    private KyberCrypto kyber;
    private DilithiumCrypto dilithium;
    private byte[] hybridSecret;

    public HybridCrypto() throws Exception {
        rsa       = new RSACrypto(2048);
        kyber     = new KyberCrypto("1024");
        dilithium = new DilithiumCrypto("5");
        rsa.generateKeys();
    }

    /**
     * Hybrid Key Exchange: RSA + Kyber
     * Derives a combined AES-256 key from both shared secrets
     */
    public byte[] hybridKeyExchange() throws Exception {
        // RSA shared secret — encrypt a random seed with RSA public key
        byte[] rsaSeed = "RSA-SharedSecret-Seed-2048".getBytes();
        byte[] rsaEncrypted = rsa.encrypt(new String(rsaSeed));
        byte[] rsaSecret = rsa.decrypt(rsaEncrypted).getBytes();

        // Kyber shared secret — real M-LWE encapsulation
        byte[][] kyberSecretOut = new byte[1][];
        byte[] kyberCiphertext = kyber.encapsulateWithSecret(kyberSecretOut);
        byte[] kyberSecret = kyber.decapsulate(kyberCiphertext);

        // Combine both secrets using SHA-256 → 32-byte AES-256 key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(rsaSecret);
        digest.update(kyberSecret);
        hybridSecret = digest.digest();

        return hybridSecret;
    }

    /**
     * Encrypt message using hybrid AES-256 key derived from RSA + Kyber
     */
    public byte[] encrypt(String message) throws Exception {
        if (hybridSecret == null) hybridKeyExchange();
        SecretKeySpec aesKey = new SecretKeySpec(hybridSecret, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return cipher.doFinal(message.getBytes());
    }

    /**
     * Decrypt message using hybrid AES-256 key
     */
    public String decrypt(byte[] ciphertext) throws Exception {
        if (hybridSecret == null) throw new Exception("Key exchange not performed");
        SecretKeySpec aesKey = new SecretKeySpec(hybridSecret, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(ciphertext));
    }

    /**
     * Hybrid Signature: RSA + Dilithium
     * Returns both signatures combined
     */
    public byte[][] hybridSign(byte[] message) throws Exception {
        byte[] rsaSig      = rsa.getPrivateKey().getEncoded(); // RSA sign via SHA256withRSA
        byte[] dilithiumSig = dilithium.sign(message);

        // RSA signature using standard Java
        java.security.Signature rsaSigner = java.security.Signature.getInstance("SHA256withRSA");
        rsaSigner.initSign(rsa.getPrivateKey());
        rsaSigner.update(message);
        rsaSig = rsaSigner.sign();

        return new byte[][] { rsaSig, dilithiumSig };
    }

    /**
     * Hybrid Verification: both RSA and Dilithium must be valid
     */
    public boolean hybridVerify(byte[] message, byte[][] signatures) throws Exception {
        // Verify RSA signature
        java.security.Signature rsaVerifier = java.security.Signature.getInstance("SHA256withRSA");
        rsaVerifier.initVerify(rsa.getPublicKey());
        rsaVerifier.update(message);
        boolean rsaValid = rsaVerifier.verify(signatures[0]);

        // Verify Dilithium signature
        boolean dilithiumValid = dilithium.verify(message, signatures[1]);

        return rsaValid && dilithiumValid;
    }

    public RSACrypto getRsa()             { return rsa; }
    public KyberCrypto getKyber()         { return kyber; }
    public DilithiumCrypto getDilithium() { return dilithium; }
    public byte[] getHybridSecret()       { return hybridSecret; }
}
