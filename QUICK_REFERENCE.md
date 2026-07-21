# Quick Reference - Demo Day

## 🚀 Quick Start (2 Steps)

### Terminal 1 (Server):
```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
run-server.bat
```
**Wait for:** "Listening on port: 8080"

### Terminal 2 (Client):
```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
run-client.bat
```
**Enter your message when prompted**

---

## 💬 What to Say

**"I have implemented secure RSA client-server communication."**

**Point to Terminal 1:** "The server generates RSA-2048 keys."

**Point to Terminal 2:** "The client encrypts my message with the server's public key."

**Point to Terminal 1:** "The server decrypts it with its private key."

**"This demonstrates public key cryptography in action."**

---

## 📋 Expected Output

### Terminal 1 (Server):
```
=== RSA Server Started ===
Listening on port: 8080
✓ Sent public key to client
✓ Received encrypted message
✓ Decrypted: [YOUR MESSAGE]
```

### Terminal 2 (Client):
```
✓ Public key received
Enter your secret message: [YOUR MESSAGE]
✓ COMMUNICATION SUCCESSFUL
```

---

## 🐛 If Something Goes Wrong

**Problem:** "Connection refused"  
**Fix:** Start server FIRST, then client

**Problem:** "Port already in use"  
**Fix:** Press Ctrl+C in server terminal, restart

**Problem:** "Class not found"  
**Fix:** Run: `javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java`

---

## 🎯 Key Points

1. ✅ Two separate terminals (client and server)
2. ✅ RSA-2048 encryption
3. ✅ Public key shared, private key secret
4. ✅ Message encrypted during transmission
5. ✅ Demonstrates secure communication

---

**Good luck with your demo!** 🎉
