# HOW TO EXECUTE THE PROJECT + VIVA QUESTIONS & ANSWERS
# Post-Quantum Cryptography Migration — RSA to ML-KEM + Hybrid

---

## PART 1: HOW TO EXECUTE THE PROJECT

---

### PREREQUISITES

| Requirement | Version | Check Command |
|-------------|---------|---------------|
| Java JDK | 11 or higher | `java -version` |
| Git | Any | `git --version` |
| Browser | Chrome / Edge | — |

All libraries are already included in the `lib/` folder. No Maven or internet needed.

---

### STEP 1 — CLONE OR OPEN THE PROJECT

If cloning from GitHub:
```
git clone https://github.com/BSriHarshitha/PQC-Migration-Project
cd PQC-Migration-Project
```

If already on your machine:
```
cd "c:\Users\user\OneDrive\Desktop\PQC-Migration-Project"
```

---

### STEP 2 — VERIFY JAVA IS INSTALLED

```
java -version
```
Expected output:
```
java version "11.x.x" or higher
```

---

### STEP 3 — COMPILE ALL FILES

Open PowerShell or Command Prompt in the project folder and run:

```
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java src\main\java\com\pqc\utils\*.java src\main\java\com\pqc\postquantum\*.java src\main\java\com\pqc\hybrid\*.java src\main\java\com\pqc\web\CryptoServer.java
```

If successful: no output, no errors.

---

### STEP 4 — RUN OPTIONS

#### OPTION A: Web UI (Recommended — Best Visual Output)

```
java -cp "lib\*;src\main\java" com.pqc.web.CryptoServer
```

OR double-click:
```
run-web-demo.bat
```

Then open browser: **http://localhost:8080**

You will see 5 tabs:
- ML-KEM (Kyber) — type message, pick variant, click Run
- ML-DSA (Dilithium) — type message, pick variant, click Sign & Verify
- Hybrid RSA+PQC — type message, click Run Hybrid Demo
- RSA (Vulnerable) — type message, click Run RSA
- Comparison — click Run All Algorithms to see bar charts + table

Press Ctrl+C in terminal to stop the server.

---

#### OPTION B: Terminal — Phase 1 PQC Demo

```
java -cp "lib\*;src\main\java" com.pqc.postquantum.PQCDemo
```

Inputs required:
```
Enter your secret message: Hello Quantum World
Choose Kyber variant (512/768/1024): 1024
Choose Dilithium variant (2/3/5): 5
```

OR use batch file:
```
run-pqc-demo.bat
```

---

#### OPTION C: Terminal — Phase 2 Hybrid Demo

```
java -cp "lib\*;src\main\java" com.pqc.hybrid.HybridDemo
```

Input required:
```
Enter your secret message: Hello Quantum World
```

OR use batch file:
```
run-hybrid-demo.bat
```

---

#### OPTION D: Terminal — RSA vs PQC Comparison

```
java -cp "lib\*;src\main\java" com.pqc.postquantum.RSAvsPQC
```

No input needed. Runs automatically.

---

#### OPTION E: Terminal — Full Benchmark

```
java -cp "lib\*;src\main\java" com.pqc.benchmark.PQCBenchmark
```

No input needed. Runs 5 iterations for all variants.

---

#### OPTION F: Terminal — Hybrid Benchmark

```
java -cp "lib\*;src\main\java" com.pqc.hybrid.HybridBenchmark
```

No input needed. Compares RSA-only vs PQC-only vs Hybrid.

---

### ALL BATCH FILES SUMMARY

| Batch File | What it runs |
|------------|-------------|
| `run-web-demo.bat` | Web UI at http://localhost:8080 |
| `run-pqc-demo.bat` | Interactive Kyber + Dilithium demo |
| `run-pqc-benchmark.bat` | All PQC variants benchmark |
| `run-hybrid-demo.bat` | Interactive hybrid demo |
| `run-hybrid-benchmark.bat` | RSA vs PQC vs Hybrid comparison |
| `run-demo.bat` | RSA encryption demo (Project 1) |
| `run-benchmark.bat` | RSA benchmark (Project 1) |

---

### EXPECTED OUTPUTS

**PQCDemo:**
```
Keys generated in ~60 ms
Public key size: 1568 bytes
Shared secret recovered: true
Signature valid: true
✓ Kyber-1024 is QUANTUM-SAFE
```

**HybridDemo:**
```
Hybrid key (base64): L8q8Xyr6uHRIhFA/...
Original:  "Hello Quantum World"
Recovered: "Hello Quantum World"
Match: true
Hybrid signature valid: true
```

**HybridBenchmark:**
```
RSA-only:       Key Gen ~417ms  | Quantum-Safe: NO
PQC-only:       Key Gen ~21ms   | Quantum-Safe: YES
Hybrid RSA+PQC: Key Gen ~510ms  | Quantum-Safe: YES
```

---

### TROUBLESHOOTING

| Problem | Solution |
|---------|----------|
| `javac not found` | Install JDK 11+, add to PATH |
| `ClassNotFoundException` | Recompile all files using Step 3 command |
| `Port 8080 in use` | Close other apps using port 8080 |
| `NoSuchElementException` | Use batch files, not piped input |
| `lib jars not found` | Make sure you are in project root folder |

---
---

## PART 2: VIVA QUESTIONS AND ANSWERS

---

### SECTION A — BASIC CONCEPTS

**Q1. What is Post-Quantum Cryptography (PQC)?**

A: Post-Quantum Cryptography refers to cryptographic algorithms that are secure against attacks from both classical and quantum computers. Unlike RSA which relies on integer factorization, PQC algorithms are based on mathematical problems like lattice problems (LWE, M-LWE) that no known quantum algorithm can solve efficiently. NIST standardized ML-KEM (Kyber) and ML-DSA (Dilithium) as PQC standards in August 2024.

---

**Q2. Why is RSA vulnerable to quantum computers?**

A: RSA security is based on the difficulty of factoring large integers. Peter Shor's algorithm (1994) can factor integers in polynomial time O((log N)³) on a quantum computer. This means a quantum computer with ~4,000 logical qubits can break RSA-2048 in approximately 52 milliseconds — which we demonstrated in Project 1 using a simulation. Classical computers would take millions of years for the same task.

---

**Q3. What is Shor's Algorithm?**

A: Shor's Algorithm is a quantum algorithm published by Peter Shor in 1994. It uses quantum Fourier transform and period-finding to factor large integers in polynomial time. It runs on a quantum computer and breaks RSA, DSA, and ECC — all algorithms based on integer factorization or discrete logarithm problems. It does NOT break lattice-based algorithms like Kyber or Dilithium.

---

**Q4. What is the difference between ML-KEM and ML-DSA?**

A: ML-KEM (Module-Lattice Key Encapsulation Mechanism) is used for key exchange — it establishes a shared secret between two parties without transmitting the secret directly. ML-DSA (Module-Lattice Digital Signature Algorithm) is used for digital signatures — it proves a message was signed by a specific private key and has not been tampered with. ML-KEM = Kyber (FIPS 203), ML-DSA = Dilithium (FIPS 204).

---

**Q5. What is a KEM (Key Encapsulation Mechanism)?**

A: A KEM is a cryptographic primitive that allows one party to generate a shared secret and securely transmit it to another party using their public key. The receiver uses their private key to recover the same shared secret. This shared secret is then used as a symmetric key (e.g., AES-256) for actual data encryption. Kyber is a KEM — it does not encrypt messages directly.

---

### SECTION B — ALGORITHM DETAILS

**Q6. What is the mathematical basis of Kyber?**

A: Kyber is based on the Module Learning With Errors (M-LWE) problem. The problem is: given a matrix A and vector b = A·s + e (where s is a secret vector and e is a small error vector), find s. This is computationally hard even for quantum computers. The "Module" part means it uses polynomial rings for efficiency. No known classical or quantum algorithm solves M-LWE efficiently.

---

**Q7. What is the mathematical basis of Dilithium?**

A: Dilithium is based on two lattice problems: Module-LWE (M-LWE) and Module-SIS (M-SIS). It uses the Fiat-Shamir with Aborts technique. Signing involves: generate a random polynomial, compute a challenge hash, produce a response polynomial. Verification checks that the response satisfies certain lattice polynomial equations. The "with Aborts" part means signing restarts if the response leaks information about the private key.

---

**Q8. What are the three variants of Kyber and what do they mean?**

A: Kyber has three variants based on security level:
- Kyber-512: 128-bit quantum security. Public key = 800 bytes, ciphertext = 768 bytes.
- Kyber-768: 192-bit quantum security. Public key = 1,184 bytes, ciphertext = 1,088 bytes.
- Kyber-1024: 256-bit quantum security. Public key = 1,568 bytes, ciphertext = 1,568 bytes.

We used Kyber-1024 in our hybrid system for maximum security (256-bit quantum security).

---

**Q9. What are the three variants of Dilithium?**

A: Dilithium has three variants:
- Dilithium-2: 128-bit quantum security. Signature = 2,420 bytes.
- Dilithium-3: 192-bit quantum security. Signature = 3,293 bytes.
- Dilithium-5: 256-bit quantum security. Signature = 4,595 bytes.

We used Dilithium-5 in our hybrid system for maximum security.

---

**Q10. How does Kyber encapsulation work step by step?**

A:
1. Alice generates a key pair: public key (1568B) + private key (3168B)
2. Alice sends her public key to Bob
3. Bob runs encapsulate(publicKey) → gets ciphertext (1568B) + sharedSecret (32B)
4. Bob sends ciphertext to Alice
5. Alice runs decapsulate(ciphertext, privateKey) → recovers same sharedSecret (32B)
6. Both Alice and Bob now have the same 32-byte secret → use as AES-256 key

The security: an attacker who intercepts the ciphertext cannot recover the shared secret without Alice's private key, because solving M-LWE is computationally hard.

---

### SECTION C — HYBRID SYSTEM

**Q11. Why did you implement a Hybrid system instead of just PQC?**

A: Organizations cannot switch from RSA to PQC overnight because:
1. Legacy systems already use RSA everywhere
2. PQC is new and needs time for widespread adoption and trust
3. During the transition period (2024–2030), both must coexist

The hybrid system runs RSA-2048 AND Kyber-1024 simultaneously. An attacker must break BOTH to compromise the system. If RSA is broken by a quantum computer, Kyber still protects. If Kyber has an unknown flaw, RSA still protects. This is recommended by NIST SP 800-227.

---

**Q12. How does the hybrid key exchange work?**

A:
1. RSA side: encrypt a seed with RSA-2048 public key → decrypt with private key → rsaSecret
2. Kyber side: encapsulate with Kyber-1024 public key → decapsulate → kyberSecret
3. Combine: SHA-256(rsaSecret ∥ kyberSecret) → hybridSecret (32 bytes = AES-256 key)
4. Use hybridSecret for AES-256 encryption of actual messages

This is called the KEM Combiner pattern, proven secure by Giacon et al. (PKC 2018).

---

**Q13. How does hybrid signing work?**

A: Both RSA-2048 and Dilithium-5 sign the same message independently:
- RSA signs using SHA256withRSA → rsaSignature (256 bytes)
- Dilithium signs using DilithiumSigner → dilithiumSignature (4595 bytes)

For verification, BOTH must return true. If either fails, the message is rejected. This means an attacker must forge both an RSA signature AND a Dilithium signature simultaneously — which is computationally infeasible.

---

**Q14. What is SHA-256 used for in your hybrid system?**

A: SHA-256 is used as a Key Derivation Function (KDF) to combine the RSA shared secret and Kyber shared secret into a single 32-byte AES-256 key. The formula is: hybridSecret = SHA-256(rsaSecret ∥ kyberSecret). SHA-256 is a one-way hash function — given the output, you cannot recover the inputs. This ensures the combined key has full 256-bit security.

---

**Q15. What is AES-256 and why is it used?**

A: AES (Advanced Encryption Standard) with 256-bit key is a symmetric encryption algorithm. It is used to encrypt the actual message because:
1. Symmetric encryption is much faster than asymmetric (RSA/Kyber)
2. AES-256 is quantum-resistant — Grover's algorithm only reduces it to 128-bit security, which is still secure
3. The hybrid key exchange establishes the AES-256 key securely

This is the standard hybrid encryption pattern: asymmetric for key exchange, symmetric for data encryption.

---

### SECTION D — IMPLEMENTATION

**Q16. Which library did you use and why?**

A: We used Bouncy Castle 1.76 (bcprov-jdk18on-1.76.jar). We chose it because:
1. It is the only Java library with real Kyber and Dilithium implementations (FIPS 203/204)
2. It is production-grade and FIPS 140-2 validated
3. Used by Google, Amazon, IBM, Oracle in production
4. We initially had BC 1.70 which had NO real Kyber/Dilithium — we upgraded to 1.76

---

**Q17. What was the difference between Bouncy Castle 1.70 and 1.76?**

A: BC 1.70 did not contain real Kyber or Dilithium. Our original code used SecureRandom to generate fake random bytes as "keys" and SHA-256 hash comparison as fake "verification" — not real cryptography. BC 1.76 contains the actual NIST FIPS 203 and FIPS 204 implementations with real M-LWE lattice mathematics. We upgraded and rewrote KyberCrypto.java and DilithiumCrypto.java completely.

---

**Q18. What classes did you implement and what does each do?**

A:
- KyberCrypto.java: ML-KEM key generation, encapsulation, decapsulation using BC 1.76
- DilithiumCrypto.java: ML-DSA key generation, signing, verification using BC 1.76
- HybridCrypto.java: Combines RSA-2048 + Kyber-1024 + Dilithium-5 into one hybrid engine
- HybridDemo.java: Interactive demo showing full hybrid flow
- HybridBenchmark.java: Compares RSA-only vs PQC-only vs Hybrid performance
- PQCDemo.java: Interactive demo for Kyber and Dilithium
- RSAvsPQC.java: Side-by-side comparison table
- CryptoServer.java: Java HTTP server serving the web UI

---

**Q19. How did you measure performance?**

A: We used System.nanoTime() which is Java's highest resolution timer (nanosecond precision). We recorded time before and after each operation and converted to milliseconds: (System.nanoTime() - startTime) / 1,000,000. We ran 5 iterations for each algorithm to reduce JVM warm-up effects and get consistent results.

---

**Q20. What is the classpath command used to compile?**

A:
```
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\...
```
- `-cp "lib\*;src\main\java"` tells Java to look for classes in the lib folder (Bouncy Castle jars) and the source folder
- `lib\*` includes all .jar files in the lib directory
- The semicolon `;` separates classpath entries on Windows (use `:` on Linux/Mac)

---

### SECTION E — RESULTS AND CORRECTNESS

**Q21. How do you prove your results are correct without a quantum computer?**

A: We prove correctness at multiple levels:
1. Functional correctness: encapsulate → decapsulate gives same secret (verified by Arrays.equals)
2. Tamper detection: changing 1 byte of message makes Dilithium verify return false
3. Encrypt → decrypt: original message is recovered exactly (Match: true)
4. Mathematical proof: NIST ran 8-year competition (2016–2024), nobody broke Kyber or Dilithium
5. Library trust: Bouncy Castle 1.76 implements exact NIST FIPS 203/204 specifications
6. KEM combiner theory: hybrid security proven by Giacon et al. PKC 2018

---

**Q22. What are your actual performance results?**

A:
- RSA-2048 key generation: ~417 ms (quantum VULNERABLE)
- Kyber-1024 key generation: ~52 ms (8x faster than RSA, quantum SAFE)
- Dilithium-5 key generation: ~60 ms (7x faster than RSA, quantum SAFE)
- Hybrid initialization: ~510 ms (includes RSA key gen)
- Hybrid encryption: ~1 ms (AES-256 is very fast)
- Hybrid signing: ~15 ms
- Hybrid verification: ~3 ms
- Hybrid signature valid: true
- Message match after encrypt/decrypt: true

---

**Q23. Why are PQC key sizes larger than RSA?**

A: RSA-2048 public key is only 294 bytes because it stores just two numbers (n, e). Kyber-1024 public key is 1,568 bytes because it stores a polynomial matrix (structured lattice data). Dilithium-5 signature is 4,595 bytes because it stores polynomial vectors. This size increase is the accepted trade-off for quantum resistance. NIST and industry consider it acceptable — modern networks easily handle the extra bandwidth.

---

**Q24. What does "Shared secret recovered: true" mean?**

A: It means the encapsulation and decapsulation produced the exact same 32-byte shared secret. The sender (encapsulate) and receiver (decapsulate) independently derived the same key without ever transmitting the key itself. This is verified using Arrays.equals(encapsulatedSecret, decapsulatedSecret). If this returns true, the M-LWE lattice math is working correctly and both parties can now use this secret as an AES-256 key.

---

**Q25. What does "Signature valid: true" mean and how is it verified?**

A: It means the DilithiumSigner.verifySignature(message, signature) returned true. This confirms:
1. The signature was created by the holder of the private key
2. The message has not been modified since signing
3. The lattice polynomial equations in the signature are consistent with the public key

We also run a tamper test: verify(tamperedMessage, signature) returns false — proving the signature is cryptographically bound to the exact original message bytes.

---

### SECTION F — SECURITY AND STANDARDS

**Q26. What is NIST FIPS 203 and FIPS 204?**

A: FIPS stands for Federal Information Processing Standard. NIST (National Institute of Standards and Technology) published:
- FIPS 203 (August 2024): Standardizes ML-KEM (Kyber) for key encapsulation
- FIPS 204 (August 2024): Standardizes ML-DSA (Dilithium) for digital signatures

These are mandatory standards for US federal agencies and widely adopted by industry. Our implementation uses Bouncy Castle 1.76 which implements these exact specifications.

---

**Q27. What is the NIST PQC migration deadline?**

A: NIST has set a mandatory deadline of 2030–2035 for all systems to migrate from RSA/ECC to post-quantum cryptography. Specifically:
- 2030: RSA and ECC should be deprecated for new systems
- 2035: Complete migration required for all systems
- Currently (2024–2025): Pilot PQC in non-critical systems
- 2025–2030: Deploy hybrid RSA+PQC mode (which is what our project implements)

---

**Q28. What is Grover's Algorithm and does it affect PQC?**

A: Grover's Algorithm is a quantum search algorithm that provides a quadratic speedup for searching unsorted databases. For symmetric cryptography (AES), it effectively halves the security level — AES-256 becomes equivalent to 128-bit security against a quantum computer. However, 128-bit security is still considered secure. For lattice-based PQC (Kyber, Dilithium), Grover's algorithm does not provide a significant speedup because the underlying M-LWE problem is not a simple search problem.

---

**Q29. What is the "Harvest Now, Decrypt Later" attack?**

A: This is a real threat where adversaries (nation-states, hackers) are currently recording encrypted RSA traffic today, storing it, and waiting until quantum computers are powerful enough to decrypt it in the future. This means data encrypted with RSA today is already at risk — even though quantum computers cannot break it yet. This is why migration to PQC is urgent even before quantum computers exist.

---

**Q30. Why is your hybrid approach better than just using PQC alone?**

A: During the transition period:
1. Legacy compatibility: Many systems only support RSA. Hybrid works with both.
2. Defense in depth: If Kyber has an undiscovered vulnerability, RSA still protects.
3. Trust: RSA has been battle-tested for 47 years. PQC is new (standardized 2024).
4. Regulatory compliance: Some regulations still require RSA. Hybrid satisfies both.
5. Gradual migration: Organizations can migrate at their own pace without breaking existing systems.

---

### SECTION G — WEB UI

**Q31. How does the web UI work?**

A: We built a Java HTTP server (CryptoServer.java) using the built-in com.sun.net.httpserver package. It:
1. Serves the HTML/CSS/JavaScript UI at http://localhost:8080
2. Exposes REST API endpoints: /api/kyber, /api/dilithium, /api/hybrid, /api/rsa
3. When user clicks a button, JavaScript sends a POST request with the message
4. The server runs the actual Java crypto code (KyberCrypto, DilithiumCrypto, HybridCrypto)
5. Returns JSON results which JavaScript displays in the UI with colored metrics

---

**Q32. What is the project's GitHub repository?**

A: https://github.com/BSriHarshitha/PQC-Migration-Project

It contains all source code, documentation, batch files, and the Bouncy Castle 1.76 library. The main branch has all completed phases: Project 1 (RSA), Project 2 (PQC), Project 3 (Hybrid), and the Web UI.

---

**Q33. What are the future enhancements for this project?**

A: The planned next phases are:
1. Healthcare Application (Project 4): Apply hybrid PQC to secure medical image transmission with HIPAA compliance
2. Adaptive Security Controller: Automatically select Classical/PQC/Hybrid mode based on security requirements and system constraints
3. PQC Migration Suitability Score: Multi-criteria model to evaluate migration readiness for different application profiles
4. TLS Integration: Integrate ML-KEM into TLS 1.3 handshake for HTTPS connections
5. Performance optimization: Hardware acceleration using AES-NI instructions

---
