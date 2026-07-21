package com.pqc.rsa;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.security.PrivateKey;

/**
 * Demonstrates RSA vulnerability through factorization attack
 * Simulates Shor's algorithm concept (classical factorization for small keys)
 */
public class RSAAttack {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    /**
     * Attempt to break RSA by factoring the modulus
     */
    public static AttackResult attackRSA(PublicKey publicKey, byte[] ciphertext) {
        long startTime = System.currentTimeMillis();
        
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            BigInteger n = rsaPublicKey.getModulus();
            BigInteger e = rsaPublicKey.getPublicExponent();
            
            System.out.println("\n=== RSA Attack Simulation ===");
            System.out.println("Modulus (n): " + n);
            System.out.println("Public exponent (e): " + e);
            System.out.println("Modulus bit length: " + n.bitLength());
            
            // Attempt factorization
            System.out.println("\n[*] Attempting to factor n...");
            BigInteger[] factors = factorize(n);
            
            if (factors == null) {
                long duration = System.currentTimeMillis() - startTime;
                return new AttackResult(false, null, duration, "Factorization failed or key too large");
            }
            
            BigInteger p = factors[0];
            BigInteger q = factors[1];
            
            System.out.println("✓ Factorization successful!");
            System.out.println("p = " + p);
            System.out.println("q = " + q);
            
            // Compute private key
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            BigInteger d = e.modInverse(phi);
            
            System.out.println("✓ Private exponent (d) recovered!");
            
            // Reconstruct private key
            RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(n, d);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            
            // Decrypt the message
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String decryptedMessage = new String(cipher.doFinal(ciphertext));
            
            long duration = System.currentTimeMillis() - startTime;
            
            System.out.println("✓ Message decrypted: " + decryptedMessage);
            System.out.println("✓ Attack completed in " + duration + " ms");
            
            return new AttackResult(true, decryptedMessage, duration, "RSA broken via factorization");
            
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            return new AttackResult(false, null, duration, "Attack failed: " + e.getMessage());
        }
    }
    
    /**
     * Pollard's Rho factorization algorithm (works for keys up to ~512 bits)
     * Simulates what Shor's algorithm would do on a quantum computer
     */
    private static BigInteger[] factorize(BigInteger n) {
        // For very large keys, quantum computer needed
        if (n.bitLength() > 512) {
            System.out.println("[!] Key too large for classical factorization");
            System.out.println("[!] Quantum computer with Shor's algorithm would be needed");
            return null;
        }
        
        System.out.println("[*] Using Pollard's Rho algorithm (classical simulation)...");
        
        // Check for small factors first
        BigInteger two = BigInteger.valueOf(2);
        if (n.mod(two).equals(BigInteger.ZERO)) {
            System.out.println("[*] Found factor: 2");
            return new BigInteger[]{two, n.divide(two)};
        }
        
        // Try small primes up to 100000
        for (int i = 3; i < 100000; i += 2) {
            BigInteger divisor = BigInteger.valueOf(i);
            if (n.mod(divisor).equals(BigInteger.ZERO)) {
                System.out.println("[*] Found small factor: " + i);
                return new BigInteger[]{divisor, n.divide(divisor)};
            }
        }
        
        // Pollard's Rho algorithm for larger factors
        System.out.println("[*] Applying Pollard's Rho algorithm...");
        BigInteger factor = pollardRho(n);
        
        if (factor != null && !factor.equals(BigInteger.ONE) && !factor.equals(n)) {
            System.out.println("[*] Found factor: " + factor);
            return new BigInteger[]{factor, n.divide(factor)};
        }
        
        System.out.println("[!] Factorization failed with classical methods");
        System.out.println("[!] Quantum computer with Shor's algorithm required");
        return null;
    }
    
    /**
     * Pollard's Rho algorithm implementation
     */
    private static BigInteger pollardRho(BigInteger n) {
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = BigInteger.valueOf(2);
        BigInteger d = BigInteger.ONE;
        
        // Polynomial function: f(x) = (x^2 + 1) mod n
        int maxIterations = 100000;
        int iteration = 0;
        
        while (d.equals(BigInteger.ONE)) {
            if (iteration++ > maxIterations) {
                return null; // Give up after max iterations
            }
            
            // x = f(x)
            x = x.multiply(x).add(BigInteger.ONE).mod(n);
            
            // y = f(f(y))
            y = y.multiply(y).add(BigInteger.ONE).mod(n);
            y = y.multiply(y).add(BigInteger.ONE).mod(n);
            
            // d = gcd(|x - y|, n)
            d = x.subtract(y).abs().gcd(n);
            
            if (iteration % 1000 == 0) {
                System.out.println("[*] Iteration " + iteration + "...");
            }
        }
        
        if (d.equals(n)) {
            return null; // Failed
        }
        
        return d;
    }
    
    /**
     * Integer square root
     */
    private static BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }
    
    /**
     * Result of attack attempt
     */
    public static class AttackResult {
        public final boolean success;
        public final String decryptedMessage;
        public final long durationMs;
        public final String message;
        
        public AttackResult(boolean success, String decryptedMessage, long durationMs, String message) {
            this.success = success;
            this.decryptedMessage = decryptedMessage;
            this.durationMs = durationMs;
            this.message = message;
        }
    }
}
