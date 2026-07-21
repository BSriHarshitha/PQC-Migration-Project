package com.pqc.rsa;

/**
 * RSA Server Demo - Run in Terminal 1
 */
public class RSAServerDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║              RSA SERVER (Terminal 1)                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        int keySize = 2048;
        int port = 8080;
        
        try {
            RSAServer server = new RSAServer(keySize, port);
            
            System.out.println("\n[*] Starting RSA server...");
            System.out.println("[*] Key size: " + keySize + " bits");
            System.out.println("[*] Port: " + port);
            System.out.println("\n[!] Waiting for client connection...");
            System.out.println("[!] Run RSAClientDemo in another terminal\n");
            
            server.start();
            
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
