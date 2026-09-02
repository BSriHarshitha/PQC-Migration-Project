package com.pqc.web;

import com.pqc.postquantum.KyberCrypto;
import com.pqc.postquantum.DilithiumCrypto;
import com.pqc.hybrid.HybridCrypto;
import com.pqc.rsa.RSACrypto;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CryptoServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", CryptoServer::serveUI);
        server.createContext("/api/kyber", CryptoServer::handleKyber);
        server.createContext("/api/dilithium", CryptoServer::handleDilithium);
        server.createContext("/api/hybrid", CryptoServer::handleHybrid);
        server.createContext("/api/rsa", CryptoServer::handleRSA);
        server.start();
        System.out.println("Server running at http://localhost:8080");
    }

    static void serveUI(HttpExchange ex) throws IOException {
        String html = readHTML();
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        ex.sendResponseHeaders(200, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.getResponseBody().close();
    }

    static void handleKyber(HttpExchange ex) throws IOException {
        Map<String, String> p = parseBody(ex);
        String message = p.getOrDefault("message", "Hello PQC");
        String variant  = p.getOrDefault("variant", "1024");
        StringBuilder sb = new StringBuilder("{");
        try {
            long t0 = System.nanoTime();
            KyberCrypto kyber = new KyberCrypto(variant);
            long keyGenMs = ms(t0);

            long t1 = System.nanoTime();
            byte[][] secretOut = new byte[1][];
            byte[] ciphertext = kyber.encapsulateWithSecret(secretOut);
            long encapMs = ms(t1);

            long t2 = System.nanoTime();
            byte[] recovered = kyber.decapsulate(ciphertext);
            long decapMs = ms(t2);

            boolean match = Arrays.equals(secretOut[0], recovered);
            String ctB64 = Base64.getEncoder().encodeToString(ciphertext);

            sb.append("\"status\":\"success\",");
            sb.append("\"variant\":\"Kyber-").append(variant).append("\",");
            sb.append("\"message\":\"").append(esc(message)).append("\",");
            sb.append("\"keyGenMs\":").append(keyGenMs).append(",");
            sb.append("\"encapMs\":").append(encapMs).append(",");
            sb.append("\"decapMs\":").append(decapMs).append(",");
            sb.append("\"publicKeySize\":").append(kyber.getPublicKeySize()).append(",");
            sb.append("\"privateKeySize\":").append(kyber.getPrivateKeySize()).append(",");
            sb.append("\"ciphertextSize\":").append(ciphertext.length).append(",");
            sb.append("\"secretSize\":").append(recovered.length).append(",");
            sb.append("\"ciphertextB64\":\"").append(ctB64.substring(0, Math.min(80, ctB64.length()))).append("...\",");
            sb.append("\"secretMatch\":").append(match).append(",");
            sb.append("\"quantumSafe\":true");
        } catch (Exception e) {
            sb.append("\"status\":\"error\",\"message\":\"").append(esc(e.getMessage())).append("\"");
        }
        sb.append("}");
        sendJSON(ex, sb.toString());
    }

    static void handleDilithium(HttpExchange ex) throws IOException {
        Map<String, String> p = parseBody(ex);
        String message = p.getOrDefault("message", "Hello PQC");
        String variant  = p.getOrDefault("variant", "5");
        StringBuilder sb = new StringBuilder("{");
        try {
            long t0 = System.nanoTime();
            DilithiumCrypto dil = new DilithiumCrypto(variant);
            long keyGenMs = ms(t0);

            byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);

            long t1 = System.nanoTime();
            byte[] signature = dil.sign(msgBytes);
            long signMs = ms(t1);

            long t2 = System.nanoTime();
            boolean valid = dil.verify(msgBytes, signature);
            long verifyMs = ms(t2);

            boolean tamperedResult = dil.verify((message + "TAMPERED").getBytes(StandardCharsets.UTF_8), signature);
            String sigB64 = Base64.getEncoder().encodeToString(signature);

            sb.append("\"status\":\"success\",");
            sb.append("\"variant\":\"Dilithium-").append(variant).append("\",");
            sb.append("\"message\":\"").append(esc(message)).append("\",");
            sb.append("\"keyGenMs\":").append(keyGenMs).append(",");
            sb.append("\"signMs\":").append(signMs).append(",");
            sb.append("\"verifyMs\":").append(verifyMs).append(",");
            sb.append("\"publicKeySize\":").append(dil.getPublicKeySize()).append(",");
            sb.append("\"privateKeySize\":").append(dil.getPrivateKeySize()).append(",");
            sb.append("\"signatureSize\":").append(signature.length).append(",");
            sb.append("\"signatureB64\":\"").append(sigB64.substring(0, Math.min(80, sigB64.length()))).append("...\",");
            sb.append("\"signatureValid\":").append(valid).append(",");
            sb.append("\"tamperedValid\":").append(tamperedResult).append(",");
            sb.append("\"quantumSafe\":true");
        } catch (Exception e) {
            sb.append("\"status\":\"error\",\"message\":\"").append(esc(e.getMessage())).append("\"");
        }
        sb.append("}");
        sendJSON(ex, sb.toString());
    }

    static void handleHybrid(HttpExchange ex) throws IOException {
        Map<String, String> p = parseBody(ex);
        String message = p.getOrDefault("message", "Hello Hybrid PQC");
        StringBuilder sb = new StringBuilder("{");
        try {
            long t0 = System.nanoTime();
            HybridCrypto hybrid = new HybridCrypto();
            long initMs = ms(t0);

            long t1 = System.nanoTime();
            byte[] hybridKey = hybrid.hybridKeyExchange();
            long keyExMs = ms(t1);

            long t2 = System.nanoTime();
            byte[] ciphertext = hybrid.encrypt(message);
            long encMs = ms(t2);

            long t3 = System.nanoTime();
            String decrypted = hybrid.decrypt(ciphertext);
            long decMs = ms(t3);

            long t4 = System.nanoTime();
            byte[][] sigs = hybrid.hybridSign(message.getBytes(StandardCharsets.UTF_8));
            long signMs = ms(t4);

            long t5 = System.nanoTime();
            boolean valid = hybrid.hybridVerify(message.getBytes(StandardCharsets.UTF_8), sigs);
            long verifyMs = ms(t5);

            sb.append("\"status\":\"success\",");
            sb.append("\"message\":\"").append(esc(message)).append("\",");
            sb.append("\"decrypted\":\"").append(esc(decrypted)).append("\",");
            sb.append("\"match\":").append(message.equals(decrypted)).append(",");
            sb.append("\"initMs\":").append(initMs).append(",");
            sb.append("\"keyExchangeMs\":").append(keyExMs).append(",");
            sb.append("\"encryptMs\":").append(encMs).append(",");
            sb.append("\"decryptMs\":").append(decMs).append(",");
            sb.append("\"signMs\":").append(signMs).append(",");
            sb.append("\"verifyMs\":").append(verifyMs).append(",");
            sb.append("\"hybridKeyB64\":\"").append(Base64.getEncoder().encodeToString(hybridKey)).append("\",");
            sb.append("\"hybridKeySize\":").append(hybridKey.length).append(",");
            sb.append("\"ciphertextB64\":\"").append(Base64.getEncoder().encodeToString(ciphertext)).append("\",");
            sb.append("\"rsaSigSize\":").append(sigs[0].length).append(",");
            sb.append("\"dilithiumSigSize\":").append(sigs[1].length).append(",");
            sb.append("\"hybridValid\":").append(valid);
        } catch (Exception e) {
            sb.append("\"status\":\"error\",\"message\":\"").append(esc(e.getMessage())).append("\"");
        }
        sb.append("}");
        sendJSON(ex, sb.toString());
    }

    static void handleRSA(HttpExchange ex) throws IOException {
        Map<String, String> p = parseBody(ex);
        String message = p.getOrDefault("message", "Hello RSA");
        StringBuilder sb = new StringBuilder("{");
        try {
            long t0 = System.nanoTime();
            RSACrypto rsa = new RSACrypto(2048);
            rsa.generateKeys();
            long keyGenMs = ms(t0);

            long t1 = System.nanoTime();
            byte[] cipher = rsa.encrypt(message);
            long encMs = ms(t1);

            long t2 = System.nanoTime();
            String dec = rsa.decrypt(cipher);
            long decMs = ms(t2);

            sb.append("\"status\":\"success\",");
            sb.append("\"message\":\"").append(esc(message)).append("\",");
            sb.append("\"decrypted\":\"").append(esc(dec)).append("\",");
            sb.append("\"match\":").append(message.equals(dec)).append(",");
            sb.append("\"keyGenMs\":").append(keyGenMs).append(",");
            sb.append("\"encryptMs\":").append(encMs).append(",");
            sb.append("\"decryptMs\":").append(decMs).append(",");
            sb.append("\"publicKeySize\":").append(rsa.getPublicKey().getEncoded().length).append(",");
            sb.append("\"ciphertextB64\":\"").append(Base64.getEncoder().encodeToString(cipher)).append("\",");
            sb.append("\"quantumSafe\":false");
        } catch (Exception e) {
            sb.append("\"status\":\"error\",\"message\":\"").append(esc(e.getMessage())).append("\"");
        }
        sb.append("}");
        sendJSON(ex, sb.toString());
    }

    static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }

    static void sendJSON(HttpExchange ex, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json");
        ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        ex.sendResponseHeaders(200, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.getResponseBody().close();
    }

    static Map<String, String> parseBody(HttpExchange ex) throws IOException {
        Map<String, String> map = new HashMap<>();
        if (ex.getRequestMethod().equals("POST")) {
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2)
                    map.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
            }
        }
        return map;
    }

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    static String readHTML() {
        try {
            String path = "src/main/resources/web/index.html";
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<h1>index.html not found at src/main/resources/web/index.html</h1>";
        }
    }
}
