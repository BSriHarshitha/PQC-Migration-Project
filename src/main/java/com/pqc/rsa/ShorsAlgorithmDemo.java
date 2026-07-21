package com.pqc.rsa;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.Scanner;

/**
 * Demonstrates Shor's Algorithm threat by simulating instant factorization
 */
public class ShorsAlgorithmDemo {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Shor's Algorithm Simulation - Quantum Attack       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();
        
        System.out.print("Choose key size (512/1024/2048): ");
        int keySize = scanner.nextInt();
        
        try {
            // Generate RSA keys
            System.out.println("\n[*] Generating RSA-" + keySize + " keys...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
            keyGen.initialize(keySize);
            KeyPair keyPair = keyGen.generateKeyPair();
            
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            BigInteger n = publicKey.getModulus();
            BigInteger e = publicKey.getPublicExponent();
            
            System.out.println("✓ Keys generated");
            System.out.println("Modulus (n): " + n);
            System.out.println("Bit length: " + n.bitLength());
            
            // Encrypt
            System.out.println("\n[*] Encrypting your message...");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] ciphertext = cipher.doFinal(message.getBytes());
            System.out.println("✓ Message encrypted (" + ciphertext.length + " bytes)");
            System.out.println("Ciphertext (hex): " + bytesToHex(ciphertext));
            System.out.println("Ciphertext (base64): " + java.util.Base64.getEncoder().encodeToString(ciphertext));
            
            // Classical attack attempt
            System.out.println("\n" + "=".repeat(60));
            System.out.println("CLASSICAL COMPUTER ATTACK");
            System.out.println("=".repeat(60));
            System.out.println("[*] Attempting classical factorization...");
            System.out.println("[*] Estimated time: " + estimateClassicalTime(keySize));
            System.out.println("✗ ATTACK FAILED - Key too large for classical methods");
            
            // Quantum attack simulation
            System.out.println("\n" + "=".repeat(60));
            System.out.println("QUANTUM COMPUTER ATTACK (Shor's Algorithm)");
            System.out.println("=".repeat(60));
            System.out.println("[*] Simulating Shor's Algorithm on quantum computer...");
            System.out.println("[*] Quantum superposition analyzing all factors simultaneously...");
            
            long startTime = System.currentTimeMillis();
            
            // Simulate quantum computation delay
            Thread.sleep(100);
            
            // In reality, we use the actual factors (simulating quantum success)
            BigInteger[] factors = extractFactors(privateKey, n, e);
            
            long endTime = System.currentTimeMillis();
            
            if (factors != null) {
                BigInteger p = factors[0];
                BigInteger q = factors[1];
                
                System.out.println("✓ QUANTUM FACTORIZATION SUCCESSFUL!");
                System.out.println("Time taken: " + (endTime - startTime) + " ms (instant!)");
                System.out.println("\nFactors found:");
                System.out.println("p = " + p);
                System.out.println("q = " + q);
                System.out.println("Verification: p × q = " + p.multiply(q).equals(n));
                
                // Recover private key
                System.out.println("\n[*] Reconstructing private key from factors...");
                BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                BigInteger d = e.modInverse(phi);
                
                KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
                PrivateKey recoveredKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(n, d));
                
                // Decrypt
                cipher.init(Cipher.DECRYPT_MODE, recoveredKey);
                String decrypted = new String(cipher.doFinal(ciphertext));
                
                System.out.println("\n" + "=".repeat(60));
                System.out.println("⚠ QUANTUM ATTACK SUCCESSFUL ⚠");
                System.out.println("=".repeat(60));
                System.out.println("Original message: \"" + message + "\"");
                System.out.println("Recovered message: \"" + decrypted + "\"");
                System.out.println("\nRSA-" + keySize + " was BROKEN in " + (endTime - startTime) + " ms!");
                System.out.println("\n[!] This proves ANY RSA key is vulnerable to quantum computers!");
                System.out.println("[!] Post-Quantum Cryptography (PQC) is REQUIRED!");
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
    
    private static String estimateClassicalTime(int keySize) {
        if (keySize <= 512) return "Hours to Days";
        if (keySize <= 1024) return "Years to Decades";
        if (keySize <= 2048) return "Millions of Years";
        return "Billions of Years";
    }
    
    private static BigInteger[] extractFactors(PrivateKey privateKey, BigInteger n, BigInteger e) {
        try {
            // Extract private exponent
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec privateSpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            BigInteger d = privateSpec.getPrivateExponent();
            
            // Use mathematical relationship to find factors
            // This simulates what Shor's algorithm would do
            BigInteger k = d.multiply(e).subtract(BigInteger.ONE);
            
            // Find factors using Fermat's method (simulating quantum result)
            BigInteger sqrt = sqrt(n);
            for (BigInteger i = sqrt; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
                BigInteger square = i.multiply(i).subtract(n);
                if (isSquare(square)) {
                    BigInteger root = sqrt(square);
                    BigInteger p = i.subtract(root);
                    BigInteger q = i.add(root);
                    if (p.multiply(q).equals(n)) {
                        return new BigInteger[]{p, q};
                    }
                }
                // Limit iterations for demo
                if (i.subtract(sqrt).compareTo(BigInteger.valueOf(100000)) > 0) break;
            }
            
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    
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
    
    private static boolean isSquare(BigInteger n) {
        if (n.signum() < 0) return false;
        BigInteger sqrt = sqrt(n);
        return sqrt.multiply(sqrt).equals(n);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    
}




