# Client-Server Demo Guide

## 📋 What This Demonstrates

Shows secure RSA communication between client and server in **separate terminals**:
1. Server generates RSA keys and waits for connections
2. Client connects, gets server's public key
3. Client encrypts YOUR message with public key
4. Server receives and decrypts with private key

---

## 🚀 Step-by-Step Instructions

### Step 1: Open TWO Terminal Windows

**Terminal 1:** For Server  
**Terminal 2:** For Client

---

### Step 2: Navigate to Project (Both Terminals)

```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
```

---

### Step 3: Start Server (Terminal 1)

**Option A - Using Batch File:**
```bash
run-server.bat
```

**Option B - Manual Command:**
```bash
java -cp "lib\*;src\main\java" com.pqc.rsa.RSAServerDemo
```

**Expected Output:**
```
╔════════════════════════════════════════════════════════╗
║              RSA SERVER (Terminal 1)                  ║
╚════════════════════════════════════════════════════════╝

[*] Starting RSA server...
[*] Key size: 2048 bits
[*] Port: 8080

[!] Waiting for client connection...
[!] Run RSAClientDemo in another terminal

=== RSA Server Started ===
Listening on port: 8080
Key size: 2048 bits
```

**⚠️ IMPORTANT:** Leave this terminal running! Don't close it.

---

### Step 4: Start Client (Terminal 2)

**Option A - Using Batch File:**
```bash
run-client.bat
```

**Option B - Manual Command:**
```bash
java -cp "lib\*;src\main\java" com.pqc.rsa.RSAClientDemo
```

**Expected Output:**
```
╔════════════════════════════════════════════════════════╗
║              RSA CLIENT (Terminal 2)                  ║
╚════════════════════════════════════════════════════════╝

[*] Connecting to server at localhost:8080
[*] Fetching server's public key...
✓ Public key received

Enter your secret message: _
```

---

### Step 5: Enter Your Message (Terminal 2)

Type your message and press Enter:
```
Enter your secret message: Hello from client!
```

**Client Output:**
```
[*] Encrypting message with server's public key...
[*] Sending encrypted message to server...
✓ Server response: Message received: Hello from client!

============================================================
✓ COMMUNICATION SUCCESSFUL
============================================================
Your message: "Hello from client!"
Server confirmed: Message received: Hello from client!

[!] Message was encrypted during transmission
[!] Only server with private key could decrypt it
```

**Server Output (Terminal 1):**
```
✓ Sent public key to client
✓ Received encrypted message
✓ Decrypted: Hello from client!
```

---

## 🎯 What to Show Your Professor

### Terminal 1 (Server):
```
=== RSA Server Started ===
Listening on port: 8080
Key size: 2048 bits
✓ Sent public key to client
✓ Received encrypted message
✓ Decrypted: [YOUR MESSAGE]
```

### Terminal 2 (Client):
```
✓ Public key received
Enter your secret message: [YOUR MESSAGE]
✓ COMMUNICATION SUCCESSFUL
[!] Message was encrypted during transmission
```

---

## 📸 Screenshot Checklist

Show your professor:
1. ✅ Two separate terminal windows side-by-side
2. ✅ Server running in Terminal 1 (showing "Listening on port")
3. ✅ Client running in Terminal 2 (showing message input)
4. ✅ Server receiving and decrypting the message
5. ✅ Client confirming successful transmission

---

## 🔄 To Run Again

1. **Stop server:** Press `Ctrl+C` in Terminal 1
2. **Restart server:** Run `run-server.bat` again
3. **Run client:** Run `run-client.bat` in Terminal 2
4. **Enter new message:** Type different message

---

## 🐛 Troubleshooting

### Error: "Connection refused"
**Problem:** Server not running  
**Solution:** Start server first (Terminal 1), then client (Terminal 2)

### Error: "Address already in use"
**Problem:** Server still running from previous session  
**Solution:** 
```bash
# Find and kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID [process_id] /F
```

### Error: "Class not found"
**Problem:** Not compiled  
**Solution:**
```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java
```

---

## 💡 Key Points to Explain

1. **Public Key Cryptography:**
   - Server has public key (shared) and private key (secret)
   - Client encrypts with public key
   - Only server can decrypt with private key

2. **Secure Communication:**
   - Message is encrypted during transmission
   - Even if intercepted, cannot be read without private key

3. **RSA-2048:**
   - Uses 2048-bit keys (industry standard)
   - Secure against classical attacks
   - Vulnerable to quantum attacks (Project 1 proved this)

---

## 🎓 For Your Presentation

**Say to your professor:**

"I have implemented RSA client-server communication:
- Terminal 1 runs the server with RSA-2048 encryption
- Terminal 2 runs the client that sends encrypted messages
- The client encrypts my message with the server's public key
- The server decrypts it with its private key
- This demonstrates secure communication using public key cryptography"

---

## 📝 Quick Commands Summary

**Terminal 1 (Server):**
```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
run-server.bat
```

**Terminal 2 (Client):**
```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
run-client.bat
[Enter your message when prompted]
```

---

**Ready to demonstrate!** 🎉
