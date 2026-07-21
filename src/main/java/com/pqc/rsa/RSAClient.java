package com.pqc.rsa;

import java.io.*;
import java.net.*;
import java.security.PublicKey;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

/**
 * RSA Client - Sends encrypted messages
 */
public class RSAClient {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private String host;
    private int port;
    private PublicKey serverPublicKey;
    
    public RSAClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Get server's public key
     */
    public void fetchPublicKey() throws Exception {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_PUBLIC_KEY");
            serverPublicKey = (PublicKey) in.readObject();
            System.out.println("✓ Received server's public key");
        }
    }
    
    /**
     * Send encrypted message to server
     */
    public String sendMessage(String message) throws Exception {
        byte[] ciphertext = encrypt(message);
        
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("SEND_MESSAGE");
            out.writeObject(ciphertext);
            
            String response = (String) in.readObject();
            System.out.println("✓ Server response: " + response);
            return response;
        }
    }
    
    /**
     * Encrypt message with server's public key
     */
    private byte[] encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);
        return cipher.doFinal(message.getBytes());
    }
    
    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }
}
