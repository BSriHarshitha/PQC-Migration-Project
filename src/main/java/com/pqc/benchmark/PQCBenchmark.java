package com.pqc.benchmark;

import com.pqc.postquantum.*;
import com.pqc.rsa.RSACrypto;
import java.security.KeyPair;

/**
 * Benchmark comparing RSA vs Post-Quantum Cryptography
 */
public class PQCBenchmark {
    
    private static final int ITERATIONS = 5;
    private static final String TEST_MESSAGE = "Benchmark test message for cryptographic performance evaluation";
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   RSA vs PQC Performance Benchmark                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        // RSA Benchmarks
        System.out.println("=".repeat(70));
        System.out.println("CLASSICAL CRYPTOGRAPHY (RSA)");
        System.out.println("=".repeat(70));
        benchmarkRSA(2048);
        
        // Kyber Benchmarks
        System.out.println("\n" + "=".repeat(70));
        System.out.println("POST-QUANTUM KEY ENCAPSULATION (KYBER)");
        System.out.println("=".repeat(70));
        benchmarkKyber("512");
        benchmarkKyber("768");
        benchmarkKyber("1024");
        
        // Dilithium Benchmarks
        System.out.println("\n" + "=".repeat(70));
        System.out.println("POST-QUANTUM SIGNATURES (DILITHIUM)");
        System.out.println("=".repeat(70));
        benchmarkDilithium("2");
        benchmarkDilithium("3");
        benchmarkDilithium("5");
        
        // Summary
        printSummary();
    }
    
    private static void benchmarkRSA(int keySize) {
        System.out.println("\n[RSA-" + keySize + "]");
        
        try {
            long totalKeyGen = 0, totalEncrypt = 0, totalDecrypt = 0;
            int pubKeySize = 0, privKeySize = 0, ciphertextSize = 0;
            
            for (int i = 0; i < ITERATIONS; i++) {
                RSACrypto rsa = new RSACrypto(keySize);
                
                long start = System.nanoTime();
                rsa.generateKeys();
                totalKeyGen += (System.nanoTime() - start);
                
                pubKeySize = rsa.getPublicKey().getEncoded().length;
                privKeySize = rsa.getPrivateKey().getEncoded().length;
                
                start = System.nanoTime();
                byte[] ciphertext = rsa.encrypt(TEST_MESSAGE);
                totalEncrypt += (System.nanoTime() - start);
                ciphertextSize = ciphertext.length;
                
                start = System.nanoTime();
                rsa.decrypt(ciphertext);
                totalDecrypt += (System.nanoTime() - start);
            }
            
            System.out.println("  Key Generation:  " + (totalKeyGen / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Encryption:      " + (totalEncrypt / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Decryption:      " + (totalDecrypt / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Public Key:      " + pubKeySize + " bytes");
            System.out.println("  Private Key:     " + privKeySize + " bytes");
            System.out.println("  Ciphertext:      " + ciphertextSize + " bytes");
            System.out.println("  Quantum-Safe:    ✗ VULNERABLE");
            
        } catch (Exception e) {
            System.err.println("  Error: " + e.getMessage());
        }
    }
    
    private static void benchmarkKyber(String variant) {
        System.out.println("\n[Kyber-" + variant + "]");
        
        try {
            long totalKeyGen = 0, totalEncap = 0, totalDecap = 0;
            int pubKeySize = 0, privKeySize = 0, ciphertextSize = 0;
            
            for (int i = 0; i < ITERATIONS; i++) {
                long start = System.nanoTime();
                KyberCrypto kyber = new KyberCrypto(variant);
                totalKeyGen += (System.nanoTime() - start);
                
                pubKeySize = kyber.getPublicKeySize();
                privKeySize = kyber.getPrivateKeySize();
                
                start = System.nanoTime();
                byte[] ciphertext = kyber.encapsulate();
                totalEncap += (System.nanoTime() - start);
                ciphertextSize = ciphertext.length;
                
                start = System.nanoTime();
                kyber.decapsulate(ciphertext);
                totalDecap += (System.nanoTime() - start);
            }
            
            System.out.println("  Key Generation:  " + (totalKeyGen / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Encapsulation:   " + (totalEncap / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Decapsulation:   " + (totalDecap / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Public Key:      " + pubKeySize + " bytes");
            System.out.println("  Private Key:     " + privKeySize + " bytes");
            System.out.println("  Ciphertext:      " + ciphertextSize + " bytes");
            System.out.println("  Quantum-Safe:    ✓ SECURE");
            
        } catch (Exception e) {
            System.err.println("  Error: " + e.getMessage());
        }
    }
    
    private static void benchmarkDilithium(String variant) {
        System.out.println("\n[Dilithium-" + variant + "]");
        
        try {
            long totalKeyGen = 0, totalSign = 0, totalVerify = 0;
            int pubKeySize = 0, privKeySize = 0, signatureSize = 0;
            
            for (int i = 0; i < ITERATIONS; i++) {
                long start = System.nanoTime();
                DilithiumCrypto dilithium = new DilithiumCrypto(variant);
                totalKeyGen += (System.nanoTime() - start);
                
                pubKeySize = dilithium.getPublicKeySize();
                privKeySize = dilithium.getPrivateKeySize();
                
                start = System.nanoTime();
                byte[] signature = dilithium.sign(TEST_MESSAGE.getBytes());
                totalSign += (System.nanoTime() - start);
                signatureSize = signature.length;
                
                start = System.nanoTime();
                dilithium.verify(TEST_MESSAGE.getBytes(), signature);
                totalVerify += (System.nanoTime() - start);
            }
            
            System.out.println("  Key Generation:  " + (totalKeyGen / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Signing:         " + (totalSign / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Verification:    " + (totalVerify / ITERATIONS / 1_000_000) + " ms");
            System.out.println("  Public Key:      " + pubKeySize + " bytes");
            System.out.println("  Private Key:     " + privKeySize + " bytes");
            System.out.println("  Signature:       " + signatureSize + " bytes");
            System.out.println("  Quantum-Safe:    ✓ SECURE");
            
        } catch (Exception e) {
            System.err.println("  Error: " + e.getMessage());
        }
    }
    
    private static void printSummary() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SUMMARY & RECOMMENDATIONS");
        System.out.println("=".repeat(70));
        System.out.println("\n✓ PQC algorithms provide quantum resistance");
        System.out.println("✓ Performance is comparable to RSA");
        System.out.println("✓ Larger key/signature sizes (acceptable tradeoff)");
        System.out.println("\n⚠ RECOMMENDATION: Migrate to PQC immediately");
        System.out.println("  - Use Kyber for key exchange");
        System.out.println("  - Use Dilithium for digital signatures");
        System.out.println("  - Consider hybrid mode during transition");
    }
}
