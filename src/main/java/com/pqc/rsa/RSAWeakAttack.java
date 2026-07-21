package com.pqc.rsa;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.Scanner;

public class RSAWeakAttack {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║     RSA Attack Demo - Guaranteed Success             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();
        
        try {
            // Use small primes for guaranteed factorization
            BigInteger p = BigInteger.valueOf(104729);
            BigInteger q = BigInteger.valueOf(104743);
            BigInteger n = p.multiply(q);
            BigInteger e = BigInteger.valueOf(65537);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            BigInteger d = e.modInverse(phi);
            
            System.out.println("\n[*] Using weak RSA with small primes...");
            System.out.println("p = " + p);
            System.out.println("q = " + q);
            System.out.println("n = " + n);
            
            // Create keys
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            PublicKey publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(n, e));
            PrivateKey privateKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(n, d));
            
            // Encrypt
            System.out.println("\n[*] Encrypting your message...");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] ciphertext = cipher.doFinal(message.getBytes());
            System.out.println("✓ Message encrypted");
            
            // Attack
            System.out.println("\n[*] Starting attack...");
            System.out.println("[*] Trying to factor n = " + n + "...");
            
            BigInteger factor = null;
            int iterations = 0;
            
            // Trial division
            for (long i = 2; i < 200000; i++) {
                iterations++;
                BigInteger divisor = BigInteger.valueOf(i);
                if (n.mod(divisor).equals(BigInteger.ZERO)) {
                    factor = divisor;
                    break;
                }
                if (i % 10000 == 0) {
                    System.out.println("[*] Tested " + i + " factors...");
                }
            }
            
            if (factor != null) {
                BigInteger p_found = factor;
                BigInteger q_found = n.divide(factor);
                
                System.out.println("\n✓ FACTORIZATION SUCCESSFUL!");
                System.out.println("Found p = " + p_found);
                System.out.println("Found q = " + q_found);
                System.out.println("Iterations: " + iterations);
                
                // Recover private key
                BigInteger phi_recovered = p_found.subtract(BigInteger.ONE).multiply(q_found.subtract(BigInteger.ONE));
                BigInteger d_recovered = e.modInverse(phi_recovered);
                
                System.out.println("\n[*] Reconstructing private key...");
                PrivateKey recoveredKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(n, d_recovered));
                
                // Decrypt
                cipher.init(Cipher.DECRYPT_MODE, recoveredKey);
                String decrypted = new String(cipher.doFinal(ciphertext));
                
                System.out.println("\n" + "=".repeat(60));
                System.out.println("⚠ ATTACK SUCCESSFUL ⚠");
                System.out.println("Original message: \"" + message + "\"");
                System.out.println("Recovered message: \"" + decrypted + "\"");
                System.out.println("=".repeat(60));
                System.out.println("\n[!] This proves RSA with small primes is BROKEN!");
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
}
