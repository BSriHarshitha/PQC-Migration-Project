package com.pqc.utils;

/**
 * Central configuration for the PQC Migration Project
 */
public class Config {
    
    // RSA Configuration
    public static final int RSA_KEY_SIZE_PRODUCTION = 2048;  // Standard production
    public static final int RSA_KEY_SIZE_DEMO = 512;         // For attack demo
    public static final int RSA_KEY_SIZE_VULNERABLE = 256;   // Very weak
    
    // PQC Algorithm Variants
    public static final String KYBER_VARIANT = "Kyber768";
    public static final String DILITHIUM_VARIANT = "Dilithium3";
    
    // Server Configuration
    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 8080;
    
    // Benchmark Configuration
    public static final int BENCHMARK_ITERATIONS = 100;
    public static final int BENCHMARK_MESSAGE_SIZE = 1024; // bytes
    
    // File Paths
    public static final String KEYS_DIR = "keys/";
    public static final String RESULTS_DIR = "results/";
    public static final String LOGS_DIR = "logs/";
    
    private Config() {
        // Prevent instantiation
    }
}
