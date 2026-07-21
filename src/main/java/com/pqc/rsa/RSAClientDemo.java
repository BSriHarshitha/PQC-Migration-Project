package com.pqc.rsa;

import java.util.Scanner;

/**
 * RSA Client Demo - Run in Terminal 2
 */
public class RSAClientDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║              RSA CLIENT (Terminal 2)                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        String host = "localhost";
        int port = 8080;
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            RSAClient client = new RSAClient(host, port);
            
            System.out.println("\n[*] Connecting to server at " + host + ":" + port);
            
            // Get server's public key
            System.out.println("[*] Fetching server's public key...");
            client.fetchPublicKey();
            System.out.println("✓ Public key received\n");
            
            // Get message from user
            System.out.print("Enter your secret message: ");
            String message = scanner.nextLine();
            
            // Encrypt and send
            System.out.println("\n[*] Encrypting message with server's public key...");
            System.out.println("[*] Sending encrypted message to server...");
            
            String response = client.sendMessage(message);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("✓ COMMUNICATION SUCCESSFUL");
            System.out.println("=".repeat(60));
            System.out.println("Your message: \"" + message + "\"");
            System.out.println("Server confirmed: " + response);
            System.out.println("\n[!] Message was encrypted during transmission");
            System.out.println("[!] Only server with private key could decrypt it");
            
        } catch (Exception e) {
            System.err.println("\nClient error: " + e.getMessage());
            System.err.println("\n[!] Make sure the server is running in another terminal!");
            System.err.println("[!] Run: java -cp \"lib\\*;src\\main\\java\" com.pqc.rsa.RSAServerDemo");
        } finally {
            scanner.close();
        }
    }
}
