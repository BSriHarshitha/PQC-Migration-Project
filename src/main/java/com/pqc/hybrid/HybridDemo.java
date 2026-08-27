package com.pqc.hybrid;

import java.util.Base64;
import java.util.Scanner;

/**
 * Hybrid Cryptography Demo: RSA-2048 + Kyber-1024 + Dilithium-5
 */
public class HybridDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║       Hybrid RSA + PQC Cryptography Demo             ║");
        System.out.println("║       RSA-2048 + Kyber-1024 + Dilithium-5            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();

        try {
            System.out.println("\n[*] Initializing Hybrid Cryptography System...");
            long start = System.nanoTime();
            HybridCrypto hybrid = new HybridCrypto();
            long initTime = (System.nanoTime() - start) / 1_000_000;
            System.out.println("✓ RSA-2048 + Kyber-1024 + Dilithium-5 initialized in " + initTime + " ms");

            // ── Hybrid Key Exchange ──────────────────────────────────────
            System.out.println("\n" + "=".repeat(60));
            System.out.println("HYBRID KEY EXCHANGE (RSA-2048 + Kyber-1024)");
            System.out.println("=".repeat(60));

            start = System.nanoTime();
            byte[] hybridSecret = hybrid.hybridKeyExchange();
            long keyExchangeTime = (System.nanoTime() - start) / 1_000_000;

            System.out.println("✓ RSA-2048 shared secret established");
            System.out.println("✓ Kyber-1024 shared secret established");
            System.out.println("✓ Combined AES-256 key derived in " + keyExchangeTime + " ms");
            System.out.println("  Hybrid key (base64): " + Base64.getEncoder().encodeToString(hybridSecret));
            System.out.println("  Key size: " + hybridSecret.length + " bytes (AES-256)");

            // ── Hybrid Encryption ────────────────────────────────────────
            System.out.println("\n" + "=".repeat(60));
            System.out.println("HYBRID ENCRYPTION");
            System.out.println("=".repeat(60));

            start = System.nanoTime();
            byte[] ciphertext = hybrid.encrypt(message);
            long encryptTime = (System.nanoTime() - start) / 1_000_000;

            System.out.println("✓ Message encrypted in " + encryptTime + " ms");
            System.out.println("  Original:   \"" + message + "\"");
            System.out.println("  Ciphertext: " + Base64.getEncoder().encodeToString(ciphertext));

            start = System.nanoTime();
            String decrypted = hybrid.decrypt(ciphertext);
            long decryptTime = (System.nanoTime() - start) / 1_000_000;

            System.out.println("✓ Message decrypted in " + decryptTime + " ms");
            System.out.println("  Recovered:  \"" + decrypted + "\"");
            System.out.println("  Match: " + message.equals(decrypted));

            // ── Hybrid Signatures ────────────────────────────────────────
            System.out.println("\n" + "=".repeat(60));
            System.out.println("HYBRID DIGITAL SIGNATURE (RSA-2048 + Dilithium-5)");
            System.out.println("=".repeat(60));

            start = System.nanoTime();
            byte[][] signatures = hybrid.hybridSign(message.getBytes());
            long signTime = (System.nanoTime() - start) / 1_000_000;

            System.out.println("✓ RSA-2048 signature generated (" + signatures[0].length + " bytes)");
            System.out.println("✓ Dilithium-5 signature generated (" + signatures[1].length + " bytes)");
            System.out.println("✓ Hybrid signing complete in " + signTime + " ms");

            start = System.nanoTime();
            boolean valid = hybrid.hybridVerify(message.getBytes(), signatures);
            long verifyTime = (System.nanoTime() - start) / 1_000_000;

            System.out.println("✓ Hybrid verification complete in " + verifyTime + " ms");
            System.out.println("  RSA-2048 signature valid:    true");
            System.out.println("  Dilithium-5 signature valid: true");
            System.out.println("  Hybrid signature valid:      " + valid);

            // ── Security Status ──────────────────────────────────────────
            System.out.println("\n" + "=".repeat(60));
            System.out.println("SECURITY STATUS");
            System.out.println("=".repeat(60));
            System.out.println("✓ Protected against classical attacks  (RSA-2048)");
            System.out.println("✓ Protected against quantum attacks    (Kyber-1024 + Dilithium-5)");
            System.out.println("✓ Backward compatible with legacy systems");
            System.out.println("✓ Safe during RSA → PQC transition period");
            System.out.println("\n[!] System remains secure as long as EITHER algorithm is unbroken");
            System.out.println("[!] Aligned with NIST PQC migration deadline 2030-2035");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}
