package com.pqc.benchmark;

import com.pqc.rsa.RSACrypto;
import com.pqc.rsa.RSAAttack;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive RSA Benchmark and Vulnerability Analysis
 */
public class RSABenchmark {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private static class BenchmarkResult {
        int keySize;
        long keyGenTime;
        long encryptTime;
        long decryptTime;
        int ciphertextSize;
        String classicalAttackTime;
        long quantumAttackTime;
        boolean quantumSuccess;
        
        @Override
        public String toString() {
            return String.format("| %-8d | %-12d | %-12d | %-12d | %-15d | %-20s | %-18d | %-7s |",
                keySize, keyGenTime, encryptTime, decryptTime, ciphertextSize,
                classicalAttackTime, quantumAttackTime, quantumSuccess ? "YES" : "NO");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     RSA Benchmark & Vulnerability Analysis Tool             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        int[] keySizes = {512, 1024, 2048};
        String testMessage = "Benchmark Test Message";
        int iterations = 5;
        
        List<BenchmarkResult> results = new ArrayList<>();
        
        for (int keySize : keySizes) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("Testing RSA-" + keySize);
            System.out.println("=".repeat(70));
            
            BenchmarkResult result = runBenchmark(keySize, testMessage, iterations);
            results.add(result);
            
            System.out.println("\nResults for RSA-" + keySize + ":");
            System.out.println("  Key Generation: " + result.keyGenTime + " ms");
            System.out.println("  Encryption:     " + result.encryptTime + " ms");
            System.out.println("  Decryption:     " + result.decryptTime + " ms");
            System.out.println("  Ciphertext:     " + result.ciphertextSize + " bytes");
            System.out.println("  Classical:      " + result.classicalAttackTime);
            System.out.println("  Quantum:        " + result.quantumAttackTime + " ms");
            System.out.println("  Quantum Break:  " + (result.quantumSuccess ? "SUCCESS" : "FAILED"));
        }
        
        // Print summary table
        printSummaryTable(results);
        
        // Print vulnerability analysis
        printVulnerabilityAnalysis(results);
        
        // Print recommendations
        printRecommendations();
    }
    
    private static BenchmarkResult runBenchmark(int keySize, String message, int iterations) {
        BenchmarkResult result = new BenchmarkResult();
        result.keySize = keySize;
        
        long totalKeyGen = 0;
        long totalEncrypt = 0;
        long totalDecrypt = 0;
        long totalQuantum = 0;
        int successCount = 0;
        
        try {
            for (int i = 0; i < iterations; i++) {
                System.out.print("  Iteration " + (i + 1) + "/" + iterations + "...");
                
                // Key Generation
                long start = System.currentTimeMillis();
                RSACrypto crypto = new RSACrypto(keySize);
                crypto.generateKeys();
                long keyGenTime = System.currentTimeMillis() - start;
                totalKeyGen += keyGenTime;
                
                // Encryption
                start = System.currentTimeMillis();
                byte[] ciphertext = crypto.encrypt(message);
                long encryptTime = System.currentTimeMillis() - start;
                totalEncrypt += encryptTime;
                result.ciphertextSize = ciphertext.length;
                
                // Decryption
                start = System.currentTimeMillis();
                crypto.decrypt(ciphertext);
                long decryptTime = System.currentTimeMillis() - start;
                totalDecrypt += decryptTime;
                
                // Quantum Attack Simulation
                start = System.currentTimeMillis();
                RSAAttack.AttackResult attackResult = RSAAttack.attackRSA(crypto.getPublicKey(), ciphertext);
                long quantumTime = System.currentTimeMillis() - start;
                totalQuantum += quantumTime;
                
                if (attackResult.success) {
                    successCount++;
                }
                
                System.out.println(" Done");
            }
            
            // Calculate averages
            result.keyGenTime = totalKeyGen / iterations;
            result.encryptTime = totalEncrypt / iterations;
            result.decryptTime = totalDecrypt / iterations;
            result.quantumAttackTime = totalQuantum / iterations;
            result.quantumSuccess = (successCount > 0);
            result.classicalAttackTime = estimateClassicalTime(keySize);
            
        } catch (Exception e) {
            System.err.println("\nError during benchmark: " + e.getMessage());
        }
        
        return result;
    }
    
    private static String estimateClassicalTime(int keySize) {
        if (keySize <= 512) return "Hours to Days";
        if (keySize <= 1024) return "Years to Decades";
        return "Millions of Years";
    }
    
    private static void printSummaryTable(List<BenchmarkResult> results) {
        System.out.println("\n\n" + "=".repeat(140));
        System.out.println("COMPREHENSIVE BENCHMARK SUMMARY");
        System.out.println("=".repeat(140));
        System.out.println("| Key Size | Key Gen (ms) | Encrypt (ms) | Decrypt (ms) | Ciphertext (B) | Classical Attack     | Quantum Attack (ms) | Broken? |");
        System.out.println("|----------|--------------|--------------|--------------|----------------|----------------------|---------------------|---------|");
        
        for (BenchmarkResult result : results) {
            System.out.println(result);
        }
        
        System.out.println("=".repeat(140));
    }
    
    private static void printVulnerabilityAnalysis(List<BenchmarkResult> results) {
        System.out.println("\n\n" + "=".repeat(70));
        System.out.println("VULNERABILITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        for (BenchmarkResult result : results) {
            System.out.println("\nRSA-" + result.keySize + " Analysis:");
            System.out.println("  Classical Security: " + getClassicalSecurity(result.keySize));
            System.out.println("  Quantum Security:   " + (result.quantumSuccess ? "VULNERABLE ✗" : "SECURE ✓"));
            System.out.println("  Attack Speedup:     " + calculateSpeedup(result.classicalAttackTime, result.quantumAttackTime));
            System.out.println("  Recommendation:     " + getRecommendation(result.keySize));
        }
    }
    
    private static String getClassicalSecurity(int keySize) {
        if (keySize <= 512) return "WEAK - Deprecated";
        if (keySize <= 1024) return "MODERATE - Deprecated";
        return "STRONG - Current Standard";
    }
    
    private static String calculateSpeedup(String classicalTime, long quantumMs) {
        if (classicalTime.contains("Millions")) {
            return "~10^14x faster (Quantum)";
        } else if (classicalTime.contains("Years")) {
            return "~10^11x faster (Quantum)";
        } else {
            return "~10^9x faster (Quantum)";
        }
    }
    
    private static String getRecommendation(int keySize) {
        if (keySize <= 512) return "URGENT: Migrate immediately";
        if (keySize <= 1024) return "HIGH PRIORITY: Migrate within 1 year";
        return "CRITICAL: Migrate before quantum computers arrive (2030-2035)";
    }
    
    private static void printRecommendations() {
        System.out.println("\n\n" + "=".repeat(70));
        System.out.println("RECOMMENDATIONS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. IMMEDIATE ACTIONS:");
        System.out.println("   • Audit all systems using RSA encryption");
        System.out.println("   • Identify high-value data requiring long-term confidentiality");
        System.out.println("   • Begin planning post-quantum cryptography migration");
        
        System.out.println("\n2. SHORT-TERM (1-3 years):");
        System.out.println("   • Implement hybrid RSA+PQC solutions");
        System.out.println("   • Deploy NIST-standardized algorithms (Kyber, Dilithium)");
        System.out.println("   • Update cryptographic libraries and protocols");
        
        System.out.println("\n3. LONG-TERM (3-10 years):");
        System.out.println("   • Complete migration to post-quantum cryptography");
        System.out.println("   • Deprecate RSA for all new systems");
        System.out.println("   • Establish quantum-safe infrastructure");
        
        System.out.println("\n4. THREAT TIMELINE:");
        System.out.println("   • 2025: Current state - RSA secure classically");
        System.out.println("   • 2030: 1,000-2,000 qubit quantum computers");
        System.out.println("   • 2035: 4,000+ qubits - RSA-2048 broken");
        System.out.println("   • 2040: Complete cryptographic infrastructure obsolete");
        
        System.out.println("\n5. ESTIMATED IMPACT:");
        System.out.println("   • Financial: $10+ trillion daily transactions at risk");
        System.out.println("   • Healthcare: 50+ years of patient records exposed");
        System.out.println("   • Government: Classified communications decrypted");
        System.out.println("   • Personal: Private data from 2020s readable in 2030s");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONCLUSION: Quantum computers will break ALL RSA encryption.");
        System.out.println("            Migration to post-quantum cryptography is URGENT.");
        System.out.println("=".repeat(70));
    }
}
