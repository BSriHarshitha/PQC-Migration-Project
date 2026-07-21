package com.pqc.rsa;

import java.util.Scanner;

/**
 * Interactive RSA Attack Demo
 * User provides a message, system encrypts it, then attacks to recover it
 */
public class RSACustomAttack {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        Interactive RSA Attack Demonstration          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();
        
        int keySize = 512;
        System.out.println("Using 512-bit key (attackable)...");
        
        try {
            System.out.println("\n[*] Generating RSA-" + keySize + " keys...");
            RSACrypto crypto = new RSACrypto(keySize);
            crypto.generateKeys();
            System.out.println("✓ Keys generated");
            
            System.out.println("\n[*] Encrypting your message...");
            byte[] ciphertext = crypto.encrypt(message);
            System.out.println("✓ Message encrypted (" + ciphertext.length + " bytes)");
            
            System.out.println("\n[*] Starting attack to recover original message...");
            System.out.println("[*] Attempting to factor the RSA modulus...\n");
            
            RSAAttack.AttackResult result = RSAAttack.attackRSA(crypto.getPublicKey(), ciphertext);
            
            System.out.println("\n" + "=".repeat(60));
            if (result.success) {
                System.out.println("⚠ ATTACK SUCCESSFUL ⚠");
                System.out.println("Original message: \"" + message + "\"");
                System.out.println("Recovered message: \"" + result.decryptedMessage + "\"");
                System.out.println("Time taken: " + result.durationMs + " ms");
                System.out.println("\n[!] RSA-" + keySize + " was BROKEN!");
            } else {
                System.out.println("✓ ATTACK FAILED");
                System.out.println("Your message remains secure (classically)");
                System.out.println("Reason: " + result.message);
                System.out.println("\n[!] However, a quantum computer with Shor's algorithm would break this instantly!");
            }
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
}
