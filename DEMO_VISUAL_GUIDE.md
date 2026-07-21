# Client-Server Demo - Visual Guide

## 🖥️ Terminal Setup

```
┌─────────────────────────────┐    ┌─────────────────────────────┐
│   TERMINAL 1 (SERVER)       │    │   TERMINAL 2 (CLIENT)       │
│                             │    │                             │
│  run-server.bat             │    │  run-client.bat             │
│                             │    │                             │
│  [*] Server Started         │    │  Enter message: Hello!      │
│  [*] Listening on port 8080 │    │  [*] Encrypting...          │
│  [*] Waiting for client...  │◄───┤  [*] Sending...             │
│                             │    │  ✓ Success!                 │
│  ✓ Client connected         │    │                             │
│  ✓ Sent public key          │────►                             │
│  ✓ Received encrypted msg   │◄───┤                             │
│  ✓ Decrypted: Hello!        │    │                             │
└─────────────────────────────┘    └─────────────────────────────┘
```

---

## 🔐 Communication Flow

```
STEP 1: Server Generates Keys
┌────────┐
│ SERVER │  Generates RSA-2048 key pair
└────────┘  • Public Key (shared)
            • Private Key (secret)

STEP 2: Client Requests Public Key
┌────────┐                    ┌────────┐
│ CLIENT │ ──── Request ────► │ SERVER │
└────────┘                    └────────┘

STEP 3: Server Sends Public Key
┌────────┐                    ┌────────┐
│ CLIENT │ ◄── Public Key ─── │ SERVER │
└────────┘                    └────────┘

STEP 4: Client Encrypts Message
┌────────┐
│ CLIENT │  "Hello!" → [Encrypted with Public Key] → "x7f9a2..."
└────────┘

STEP 5: Client Sends Encrypted Message
┌────────┐                    ┌────────┐
│ CLIENT │ ── "x7f9a2..." ──► │ SERVER │
└────────┘                    └────────┘

STEP 6: Server Decrypts Message
┌────────┐
│ SERVER │  "x7f9a2..." → [Decrypted with Private Key] → "Hello!"
└────────┘
```

---

## 📊 What Happens Behind the Scenes

### Terminal 1 (Server):
```
1. Generate RSA-2048 keys
   ├─ Public Key: e, n (shared with client)
   └─ Private Key: d, n (kept secret)

2. Listen on port 8080

3. Client connects
   └─ Send public key to client

4. Receive encrypted message
   └─ Decrypt with private key
   └─ Display original message
```

### Terminal 2 (Client):
```
1. Connect to server (localhost:8080)

2. Request public key
   └─ Receive and store public key

3. Get message from user
   └─ "Hello from client!"

4. Encrypt message
   └─ Use server's public key
   └─ Plaintext → Ciphertext

5. Send encrypted message to server

6. Receive confirmation
```

---

## 🎯 Key Concepts Demonstrated

### 1. Public Key Cryptography
```
Public Key:  Anyone can use to ENCRYPT
Private Key: Only server can use to DECRYPT

Message: "Hello"
         ↓ [Encrypt with Public Key]
Ciphertext: "x7f9a2b4c8..."
         ↓ [Decrypt with Private Key]
Message: "Hello"
```

### 2. Secure Communication
```
Client ──────────────────────► Server
       Encrypted Message
       (Safe even if intercepted)

Attacker intercepts: "x7f9a2b4c8..."
Cannot decrypt without private key!
```

### 3. RSA-2048 Security
```
Key Size: 2048 bits
Classical Security: ✓ SECURE (millions of years to break)
Quantum Security: ✗ VULNERABLE (52ms with Shor's algorithm)
```

---

## 🎓 Presentation Script

### Opening:
"I will demonstrate RSA client-server communication using two separate terminals."

### Demo Steps:
1. **Show Terminal 1:** "This is the server. It generates RSA-2048 keys and waits for connections."
2. **Show Terminal 2:** "This is the client. It will connect to the server."
3. **Type message:** "I'll send the message: [YOUR MESSAGE]"
4. **Point to encryption:** "The client encrypts my message with the server's public key."
5. **Point to server:** "The server receives the encrypted message and decrypts it with its private key."
6. **Show both terminals:** "As you can see, the message was transmitted securely."

### Conclusion:
"This demonstrates public key cryptography where:
- The public key encrypts (client side)
- The private key decrypts (server side)
- The message is secure during transmission

However, as shown in Project 1, quantum computers can break RSA using Shor's algorithm. That's why we need post-quantum cryptography, which I implemented in Project 2."

---

## 📸 Screenshot Positions

```
┌─────────────────────────────────────────────────────────┐
│                    YOUR SCREEN                          │
│                                                         │
│  ┌──────────────────┐    ┌──────────────────┐         │
│  │  Terminal 1      │    │  Terminal 2      │         │
│  │  (Server)        │    │  (Client)        │         │
│  │                  │    │                  │         │
│  │  Server Started  │    │  Enter message:  │         │
│  │  Port: 8080      │    │  Hello!          │         │
│  │  Waiting...      │    │  ✓ Sent          │         │
│  │  ✓ Received      │    │                  │         │
│  │  ✓ Decrypted     │    │                  │         │
│  └──────────────────┘    └──────────────────┘         │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## ✅ Checklist for Professor

- [ ] Two terminals visible side-by-side
- [ ] Server running and showing "Listening on port 8080"
- [ ] Client connected and showing "Public key received"
- [ ] Your custom message entered
- [ ] Server showing decrypted message
- [ ] Client showing "COMMUNICATION SUCCESSFUL"

---

**You're ready to demonstrate!** 🚀
