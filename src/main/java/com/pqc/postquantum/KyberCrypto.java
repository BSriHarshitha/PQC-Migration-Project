package com.pqc.postquantum;

import org.bouncycastle.pqc.crypto.crystals.kyber.*;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import java.security.SecureRandom;

/**
 * CRYSTALS-Kyber: Real Post-Quantum Key Encapsulation Mechanism (KEM)
 * Uses Bouncy Castle 1.76 - actual lattice-based cryptography (NIST FIPS 203)
 */
public class KyberCrypto {

    private AsymmetricCipherKeyPair keyPair;
    private final KyberParameters params;
    private final String variant;

    public KyberCrypto(String variant) throws Exception {
        this.variant = variant;
        switch (variant) {
            case "512":  this.params = KyberParameters.kyber512;  break;
            case "768":  this.params = KyberParameters.kyber768;  break;
            case "1024": this.params = KyberParameters.kyber1024; break;
            default: throw new IllegalArgumentException("Invalid variant. Use 512, 768, or 1024");
        }
        generateKeyPair();
    }

    private void generateKeyPair() throws Exception {
        KyberKeyPairGenerator gen = new KyberKeyPairGenerator();
        gen.init(new KyberKeyGenerationParameters(new SecureRandom(), params));
        keyPair = gen.generateKeyPair();
    }

    public byte[] encapsulate() throws Exception {
        KyberKEMGenerator kemGen = new KyberKEMGenerator(new SecureRandom());
        SecretWithEncapsulation result = kemGen.generateEncapsulated(keyPair.getPublic());
        // Store encapsulation for decapsulation demo; return ciphertext
        return result.getEncapsulation();
    }

    public byte[] encapsulateWithSecret(byte[][] secretOut) throws Exception {
        KyberKEMGenerator kemGen = new KyberKEMGenerator(new SecureRandom());
        SecretWithEncapsulation result = kemGen.generateEncapsulated(keyPair.getPublic());
        secretOut[0] = result.getSecret();
        return result.getEncapsulation();
    }

    public byte[] decapsulate(byte[] ciphertext) throws Exception {
        KyberKEMExtractor extractor = new KyberKEMExtractor((KyberPrivateKeyParameters) keyPair.getPrivate());
        return extractor.extractSecret(ciphertext);
    }

    public byte[] getPublicKey() {
        return ((KyberPublicKeyParameters) keyPair.getPublic()).getEncoded();
    }

    public byte[] getPrivateKey() {
        return ((KyberPrivateKeyParameters) keyPair.getPrivate()).getEncoded();
    }

    public String getVariant() { return "Kyber-" + variant; }
    public int getPublicKeySize() { return getPublicKey().length; }
    public int getPrivateKeySize() { return getPrivateKey().length; }
    public int getCiphertextSize() { return encapsulateSize(); }

    private int encapsulateSize() {
        switch (variant) {
            case "512":  return 768;
            case "768":  return 1088;
            case "1024": return 1568;
            default:     return 0;
        }
    }
}
