package com.pqc.postquantum;

import java.util.Base64;
import java.util.Scanner;

/**
 * Demonstrates Post-Quantum Cryptography (Kyber + Dilithium)
 */
public class PQCDemo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Post-Quantum Cryptography (PQC) Demonstration      ║");
        System.out.println("║   CRYSTALS-Kyber + CRYSTALS-Dilithium                ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();
        
        try {
            // Kyber KEM Demo
            System.out.println("\n" + "=".repeat(60));
            System.out.println("KYBER KEY ENCAPSULATION MECHANISM (KEM)");
            System.out.println("=".repeat(60));
            
            System.out.print("Choose Kyber variant (512/768/1024): ");
            String kyberVariant = scanner.next();
            scanner.nextLine();
            
            System.out.println("\n[*] Generating Kyber-" + kyberVariant + " keys...");
            long startTime = System.nanoTime();
            KyberCrypto kyber = new KyberCrypto(kyberVariant);
            long keyGenTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Keys generated in " + keyGenTime + " ms");
            System.out.println("Public key size: " + kyber.getPublicKeySize() + " bytes");
            System.out.println("Private key size: " + kyber.getPrivateKeySize() + " bytes");
            
            System.out.println("\n[*] Encapsulating shared secret...");
            startTime = System.nanoTime();
            byte[] ciphertext = kyber.encapsulate();
            long encapTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Encapsulation complete in " + encapTime + " ms");
            System.out.println("Ciphertext size: " + ciphertext.length + " bytes");
            System.out.println("Ciphertext (base64): " + Base64.getEncoder().encodeToString(ciphertext));
            
            System.out.println("\n[*] Decapsulating shared secret...");
            startTime = System.nanoTime();
            byte[] sharedSecret = kyber.decapsulate(ciphertext);
            long decapTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Decapsulation complete in " + decapTime + " ms");
            System.out.println("Shared secret recovered: " + (sharedSecret != null));
            
            // Dilithium Signature Demo
            System.out.println("\n" + "=".repeat(60));
            System.out.println("DILITHIUM DIGITAL SIGNATURE");
            System.out.println("=".repeat(60));
            
            System.out.print("Choose Dilithium variant (2/3/5): ");
            String dilithiumVariant = scanner.next();
            
            System.out.println("\n[*] Generating Dilithium-" + dilithiumVariant + " keys...");
            startTime = System.nanoTime();
            DilithiumCrypto dilithium = new DilithiumCrypto(dilithiumVariant);
            keyGenTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Keys generated in " + keyGenTime + " ms");
            System.out.println("Public key size: " + dilithium.getPublicKeySize() + " bytes");
            System.out.println("Private key size: " + dilithium.getPrivateKeySize() + " bytes");
            
            System.out.println("\n[*] Signing message: \"" + message + "\"");
            startTime = System.nanoTime();
            byte[] signature = dilithium.sign(message.getBytes());
            long signTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Signature generated in " + signTime + " ms");
            System.out.println("Signature size: " + signature.length + " bytes");
            System.out.println("Signature (base64): " + Base64.getEncoder().encodeToString(signature).substring(0, 64) + "...");
            
            System.out.println("\n[*] Verifying signature...");
            startTime = System.nanoTime();
            boolean valid = dilithium.verify(message.getBytes(), signature);
            long verifyTime = (System.nanoTime() - startTime) / 1_000_000;
            
            System.out.println("✓ Verification complete in " + verifyTime + " ms");
            System.out.println("Signature valid: " + valid);
            
            // Quantum Resistance
            System.out.println("\n" + "=".repeat(60));
            System.out.println("⚡ QUANTUM RESISTANCE ANALYSIS");
            System.out.println("=".repeat(60));
            System.out.println("✓ Kyber-" + kyberVariant + " is QUANTUM-SAFE");
            System.out.println("✓ Dilithium-" + dilithiumVariant + " is QUANTUM-SAFE");
            System.out.println("\n[!] These algorithms resist Shor's Algorithm");
            System.out.println("[!] Based on lattice problems (hard for quantum computers)");
            System.out.println("[!] NIST-approved post-quantum standards");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
}
