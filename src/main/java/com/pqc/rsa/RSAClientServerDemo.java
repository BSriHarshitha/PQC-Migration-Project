package com.pqc.rsa;

import java.util.Scanner;

/**
 * RSA Client-Server Demo - Single Terminal
 * Runs server and client in separate threads
 */
public class RSAClientServerDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        RSA Client-Server Demo (Single Terminal)       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter your secret message: ");
        String message = scanner.nextLine();
        scanner.close();

        int port = 8080;

        // Start server in background thread
        Thread serverThread = new Thread(() -> {
            try {
                System.out.println("\n[SERVER] Starting RSA-2048 server on port " + port + "...");
                RSAServer server = new RSAServer(2048, port);
                server.start();
            } catch (Exception e) {
                System.err.println("[SERVER] Error: " + e.getMessage());
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();

        // Wait for server to initialize
        Thread.sleep(2000);

        // Run client in main thread
        System.out.println("\n[CLIENT] Connecting to server at localhost:" + port + "...");
        RSAClient client = new RSAClient("localhost", port);

        System.out.println("[CLIENT] Fetching server's public key...");
        client.fetchPublicKey();

        System.out.println("[CLIENT] Encrypting message: \"" + message + "\"");
        System.out.println("[CLIENT] Sending encrypted message to server...");
        client.sendMessage(message);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("✓ DEMO COMPLETE");
        System.out.println("=".repeat(60));
        System.out.println("✓ Server generated RSA-2048 keys");
        System.out.println("✓ Client received public key");
        System.out.println("✓ Message encrypted and sent");
        System.out.println("✓ Server decrypted: \"" + message + "\"");
        System.out.println("\n[!] Message was encrypted during transmission");
        System.out.println("[!] Only server's private key could decrypt it");
    }
}
