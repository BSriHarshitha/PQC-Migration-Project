package com.pqc;

import com.pqc.utils.Config;

/**
 * Main entry point for PQC Migration Project
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Post-Quantum Cryptography Migration Project ===");
        System.out.println("RSA Production Key Size: " + Config.RSA_KEY_SIZE_PRODUCTION + " bits");
        System.out.println("RSA Demo Key Size: " + Config.RSA_KEY_SIZE_DEMO + " bits");
        System.out.println("PQC Algorithm: " + Config.KYBER_VARIANT);
        System.out.println("Server: " + Config.SERVER_HOST + ":" + Config.SERVER_PORT);
        
        System.out.println("\n✓ Phase 1 Complete: Project Setup");
        System.out.println("✓ Java environment configured");
        System.out.println("✓ Dependencies ready");
        System.out.println("\nNext: Phase 2 - RSA Implementation");
    }
}
