package com.pqc.rsa;

import com.pqc.utils.Config;

/**
 * Phase 2 Demo: RSA Communication and Vulnerability
 */
public class RSADemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Phase 2: RSA Implementation & Vulnerability Demo    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        try {
            // Demo 1: Secure RSA (2048-bit)
            System.out.println("\n--- Demo 1: Production RSA (2048-bit) ---");
            demonstrateSecureRSA();
            
            // Demo 2: Vulnerable RSA (512-bit)
            System.out.println("\n\n--- Demo 2: Vulnerable RSA (512-bit) - Attack Demo ---");
            demonstrateVulnerableRSA();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Demo 1: Show RSA working correctly with production key size
     */
    private static void demonstrateSecureRSA() throws Exception {
        RSACrypto crypto = new RSACrypto(Config.RSA_KEY_SIZE_PRODUCTION);
        crypto.generateKeys();
        
        String message = "Confidential medical record: Patient ID 12345";
        System.out.println("Original message: " + message);
        
        byte[] ciphertext = crypto.encrypt(message);
        System.out.println("✓ Message encrypted (" + ciphertext.length + " bytes)");
        
        String decrypted = crypto.decrypt(ciphertext);
        System.out.println("✓ Message decrypted: " + decrypted);
        
        System.out.println("\n[*] Attempting attack on 2048-bit RSA...");
        RSAAttack.AttackResult result = RSAAttack.attackRSA(crypto.getPublicKey(), ciphertext);
        
        if (!result.success) {
            System.out.println("✓ Attack FAILED - RSA-2048 is secure against classical attacks");
            System.out.println("✓ " + result.message);
        }
    }
    
    /**
     * Demo 2: Show RSA vulnerability with small key size
     */
    private static void demonstrateVulnerableRSA() throws Exception {
        // Use 256-bit key for successful attack demo
        RSACrypto crypto = new RSACrypto(Config.RSA_KEY_SIZE_VULNERABLE);
        crypto.generateKeys();
        
        String message = "TOP SECRET";
        System.out.println("Original message: " + message);
        
        byte[] ciphertext = crypto.encrypt(message);
        System.out.println("✓ Message encrypted (" + ciphertext.length + " bytes)");
        
        System.out.println("\n[*] Simulating Shor's Algorithm attack...");
        System.out.println("[*] (Using Pollard's Rho - classical factorization)");
        
        RSAAttack.AttackResult result = RSAAttack.attackRSA(crypto.getPublicKey(), ciphertext);
        
        if (result.success) {
            System.out.println("\n⚠ SECURITY BREACH ⚠");
            System.out.println("✗ RSA-" + Config.RSA_KEY_SIZE_VULNERABLE + " was BROKEN in " + result.durationMs + " ms");
            System.out.println("✗ Attacker recovered: " + result.decryptedMessage);
            System.out.println("\n[!] This demonstrates why quantum-resistant crypto is needed!");
        } else {
            System.out.println("\n[!] Attack failed: " + result.message);
            System.out.println("[!] Note: Larger keys would be broken by quantum Shor's algorithm");
        }
    }
}
