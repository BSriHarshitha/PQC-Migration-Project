package com.pqc.rsa;

import com.pqc.utils.Config;
import java.io.*;
import java.net.*;

/**
 * RSA Server - Receives encrypted messages
 */
public class RSAServer {
    
    private RSACrypto crypto;
    private int port;
    
    public RSAServer(int keySize, int port) {
        this.crypto = new RSACrypto(keySize);
        this.port = port;
    }
    
    public void start() throws Exception {
        crypto.generateKeys();
        
        System.out.println("\n=== RSA Server Started ===");
        System.out.println("Listening on port: " + port);
        System.out.println("Key size: " + crypto.getKeySize() + " bits");
        
        ServerSocket serverSocket = new ServerSocket(port);
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
        }
    }
    
    private void handleClient(Socket socket) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            
            String command = (String) in.readObject();
            
            if ("GET_PUBLIC_KEY".equals(command)) {
                out.writeObject(crypto.getPublicKey());
                System.out.println("✓ Sent public key to client");
                
            } else if ("SEND_MESSAGE".equals(command)) {
                byte[] ciphertext = (byte[]) in.readObject();
                String decrypted = crypto.decrypt(ciphertext);
                System.out.println("✓ Received encrypted message");
                System.out.println("✓ Decrypted: " + decrypted);
                out.writeObject("Message received: " + decrypted);
            }
            
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
    
    public RSACrypto getCrypto() {
        return crypto;
    }
}
