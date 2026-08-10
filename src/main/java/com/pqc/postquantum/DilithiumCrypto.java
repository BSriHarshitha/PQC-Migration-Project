package com.pqc.postquantum;

import org.bouncycastle.pqc.crypto.crystals.dilithium.*;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import java.security.SecureRandom;

/**
 * CRYSTALS-Dilithium: Real Post-Quantum Digital Signature Algorithm
 * Uses Bouncy Castle 1.76 - actual lattice-based cryptography (NIST FIPS 204)
 */
public class DilithiumCrypto {

    private AsymmetricCipherKeyPair keyPair;
    private final DilithiumParameters params;
    private final String variant;

    public DilithiumCrypto(String variant) throws Exception {
        this.variant = variant;
        switch (variant) {
            case "2": this.params = DilithiumParameters.dilithium2; break;
            case "3": this.params = DilithiumParameters.dilithium3; break;
            case "5": this.params = DilithiumParameters.dilithium5; break;
            default: throw new IllegalArgumentException("Invalid variant. Use 2, 3, or 5");
        }
        generateKeyPair();
    }

    private void generateKeyPair() throws Exception {
        DilithiumKeyPairGenerator gen = new DilithiumKeyPairGenerator();
        gen.init(new DilithiumKeyGenerationParameters(new SecureRandom(), params));
        keyPair = gen.generateKeyPair();
    }

    public byte[] sign(byte[] message) throws Exception {
        DilithiumSigner signer = new DilithiumSigner();
        signer.init(true, keyPair.getPrivate());
        return signer.generateSignature(message);
    }

    public boolean verify(byte[] message, byte[] signature) throws Exception {
        DilithiumSigner verifier = new DilithiumSigner();
        verifier.init(false, keyPair.getPublic());
        return verifier.verifySignature(message, signature);
    }

    public byte[] getPublicKey() {
        return ((DilithiumPublicKeyParameters) keyPair.getPublic()).getEncoded();
    }

    public byte[] getPrivateKey() {
        return ((DilithiumPrivateKeyParameters) keyPair.getPrivate()).getEncoded();
    }

    public String getVariant() { return "Dilithium-" + variant; }
    public int getPublicKeySize() { return getPublicKey().length; }
    public int getPrivateKeySize() { return getPrivateKey().length; }
    public int getSignatureSize() {
        switch (variant) {
            case "2": return 2420;
            case "3": return 3293;
            case "5": return 4595;
            default:  return 0;
        }
    }
}
