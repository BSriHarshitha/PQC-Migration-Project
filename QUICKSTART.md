# Quick Start Guide - Running the PQC Migration Project

## Prerequisites Check

1. **Verify Java is installed:**
   ```bash
   java -version
   ```
   You should see Java 11 or higher.

2. **Navigate to project directory:**
   ```bash
   cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
   ```

---

## Project 1: RSA Vulnerability Demos

### Demo 1: Basic RSA Encryption
**File:** `run-demo.bat`

**What it does:** Shows RSA encryption with secure (2048-bit) and vulnerable (512-bit) keys

**Run:**
```bash
run-demo.bat
```

---

### Demo 2: Interactive Attack Demo
**File:** `run-custom-attack.bat`

**What it does:** You enter a message, system encrypts it, then attempts classical attacks

**Run:**
```bash
run-custom-attack.bat
```

**Example input:**
- Message: `Hello World`
- Key size: `512` (for faster demo)

---

### Demo 3: Shor's Algorithm Quantum Attack
**File:** `run-custom-attack.bat` (uses ShorsAlgorithmDemo)

**What it does:** Simulates quantum computer breaking RSA instantly

**Run:**
```bash
run-custom-attack.bat
```

**Example input:**
- Message: `Secret data`
- Key size: `2048` (shows even strong RSA breaks)

---

### Demo 4: RSA Performance Benchmark
**File:** `run-benchmark.bat`

**What it does:** Tests RSA-512/1024/2048 performance (5 iterations each)

**Run:**
```bash
run-benchmark.bat
```

**Output:** Key generation times, encryption/decryption speeds, quantum attack simulation

---

## Project 2: Post-Quantum Cryptography Demos

### Demo 5: PQC Interactive Demo
**File:** `run-pqc-demo.bat`

**What it does:** Demonstrates Kyber (key exchange) and Dilithium (signatures)

**Run:**
```bash
run-pqc-demo.bat
```

**Example input:**
- Message: `Quantum-safe message`
- Kyber variant: `1024` (highest security)
- Dilithium variant: `5` (highest security)

---

### Demo 6: RSA vs PQC Benchmark
**File:** `run-pqc-benchmark.bat`

**What it does:** Compares RSA-2048 vs all Kyber/Dilithium variants

**Run:**
```bash
run-pqc-benchmark.bat
```

**Output:** Performance comparison showing PQC is 10-15x faster at key generation

---

### Demo 7: Side-by-Side Comparison
**File:** `run-comparison.bat`

**What it does:** Shows RSA-2048, Kyber-1024, and Dilithium-5 side-by-side

**Run:**
```bash
run-comparison.bat
```

**Output:** Direct comparison with summary table

---

## Troubleshooting

### Error: "javac is not recognized"
**Solution:** Add Java to PATH or use full path:
```bash
"C:\Program Files\Java\jdk-11\bin\javac" -version
```

### Error: "cannot find symbol"
**Solution:** Recompile everything:
```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\**\*.java
```

### Error: "class not found"
**Solution:** Check you're in the project root directory:
```bash
cd C:\Users\user\OneDrive\Desktop\PQC-Migration-Project
```

---

## Quick Test (Recommended First Run)

**Run this first to verify everything works:**

```bash
run-comparison.bat
```

This will:
1. Compile all necessary files
2. Run RSA-2048 demo
3. Run Kyber-1024 demo
4. Run Dilithium-5 demo
5. Show comparison table

**Expected output:**
- RSA key generation: ~150ms
- Kyber key generation: ~12ms
- Dilithium key generation: ~18ms
- Summary showing PQC is quantum-safe

---

## Manual Compilation (If Needed)

### Compile Project 1 (RSA):
```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java
```

### Compile Project 2 (PQC):
```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\*.java
```

### Compile Benchmarks:
```bash
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\benchmark\*.java
```

---

## Recommended Demo Order

1. **run-comparison.bat** - Quick overview of everything
2. **run-demo.bat** - Understand RSA basics
3. **run-custom-attack.bat** - See quantum attack in action
4. **run-pqc-demo.bat** - Explore PQC interactively
5. **run-benchmark.bat** - Full RSA performance analysis
6. **run-pqc-benchmark.bat** - Full PQC performance analysis

---

## What to Expect

### Project 1 Demos Show:
✓ RSA encryption/decryption works  
✓ Classical attacks fail on 256+ bit keys  
✓ Quantum attacks break ALL RSA keys in ~50ms  
✓ Conclusion: RSA is vulnerable to quantum computers  

### Project 2 Demos Show:
✓ Kyber provides quantum-safe key exchange  
✓ Dilithium provides quantum-safe signatures  
✓ PQC is 10-15x faster at key generation  
✓ PQC has comparable encryption/signing speed  
✓ Conclusion: PQC is ready for production use  

---

## Need Help?

Check documentation:
- `docs\PROJECT1_REPORT.md` - Complete RSA analysis
- `docs\PROJECT2_REPORT.md` - Complete PQC guide
- `docs\PROJECT_SUMMARY.md` - Overall summary

---

**Ready to start? Run:** `run-comparison.bat`
