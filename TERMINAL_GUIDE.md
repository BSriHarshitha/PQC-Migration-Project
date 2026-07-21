# Terminal Commands - PQC Migration Project

## Step 1: Open Terminal and Navigate to Project

```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
```

---

## Step 2: Verify Java Installation

```bash
java -version
```

Expected output: Java 11 or higher

---

## Step 3: Compile Everything (One-Time Setup)

```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java src\main\java\com\pqc\postquantum\*.java src\main\java\com\pqc\benchmark\*.java
```

---

## Step 4: Run Demos

### Option 1: Quick Comparison (RECOMMENDED FIRST)
```bash
java -cp "lib\*;src\main\java" com.pqc.postquantum.RSAvsPQC
```
Shows RSA vs PQC side-by-side with performance comparison.

---

### Option 2: RSA Basic Demo
```bash
java -cp "lib\*;src\main\java" com.pqc.rsa.RSADemo
```
Demonstrates RSA encryption with secure and vulnerable keys.

---

### Option 3: Quantum Attack Demo (Interactive)
```bash
java -cp "lib\*;src\main\java" com.pqc.rsa.ShorsAlgorithmDemo
```
**You'll be prompted for:**
- Secret message (e.g., "Hello World")
- Key size (512, 1024, or 2048)

Shows how quantum computers break RSA instantly.

---

### Option 4: PQC Interactive Demo
```bash
java -cp "lib\*;src\main\java" com.pqc.postquantum.PQCDemo
```
**You'll be prompted for:**
- Secret message
- Kyber variant (512, 768, or 1024)
- Dilithium variant (2, 3, or 5)

Demonstrates quantum-safe cryptography.

---

### Option 5: RSA Performance Benchmark
```bash
java -cp "lib\*;src\main\java" com.pqc.benchmark.RSABenchmark
```
Tests RSA-512/1024/2048 performance (5 iterations each).

---

### Option 6: PQC Performance Benchmark
```bash
java -cp "lib\*;src\main\java" com.pqc.benchmark.PQCBenchmark
```
Compares RSA vs all Kyber/Dilithium variants.

---

## Quick Start (Copy-Paste These Commands)

```bash
# Navigate to project
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project

# Compile (first time only)
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java src\main\java\com\pqc\postquantum\*.java src\main\java\com\pqc\benchmark\*.java

# Run comparison demo
java -cp "lib\*;src\main\java" com.pqc.postquantum.RSAvsPQC
```

---

## Troubleshooting

### If compilation fails:
```bash
# Compile step by step
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\RSACrypto.java
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\KyberCrypto.java
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\DilithiumCrypto.java
```

### If "class not found" error:
Make sure you're in the project root directory:
```bash
dir
```
You should see: `lib`, `src`, `docs`, `*.bat` files

---

## Expected Output Examples

### RSAvsPQC Demo:
```
RSA-2048:     Key gen ~150ms, Quantum-Safe: ✗ VULNERABLE
Kyber-1024:   Key gen ~12ms,  Quantum-Safe: ✓ SECURE
Dilithium-5:  Key gen ~18ms,  Quantum-Safe: ✓ SECURE
```

### ShorsAlgorithmDemo:
```
Enter message: Secret
Choose key size: 2048
✓ RSA-2048 was BROKEN in 52ms!
Original: "Secret"
Recovered: "Secret"
```

---

**Start with:** `java -cp "lib\*;src\main\java" com.pqc.postquantum.RSAvsPQC`
