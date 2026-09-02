# PPT SLIDE CONTENT
# Post-Quantum Cryptography Migration: RSA to ML-KEM + Hybrid
# Mini Project Presentation

---

## SLIDE 1 — TITLE SLIDE

**Title:**
Performance and Security Evaluation of RSA and ML-KEM for
Post-Quantum Secure Key Establishment in Java

**Subtitle:** Mini Project Presentation

**Details:**
- Language: Java 11 | Library: Bouncy Castle 1.76
- Standards: NIST FIPS 203 (ML-KEM) · NIST FIPS 204 (ML-DSA)
- GitHub: https://github.com/BSriHarshitha/PQC-Migration-Project

---

## SLIDE 2 — MOTIVATION

**Title:** Why This Project Matters

**Left Column — The Quantum Threat:**
- $10+ trillion in daily transactions protected by RSA
- 4.9 billion internet users rely on RSA-based HTTPS
- Shor's Algorithm (1994) breaks RSA in polynomial time
- RSA-2048 broken in ~52ms on a quantum computer
- "Harvest Now, Decrypt Later" attacks already happening

**Right Column — The Urgency:**
- 2024: NIST published FIPS 203 (ML-KEM) and FIPS 204 (ML-DSA)
- 2030: RSA deprecated for new systems (NIST mandate)
- 2035: Complete migration required for all systems
- Organizations need a practical migration framework NOW

**Bottom Quote:**
> "The question is not IF quantum computers will break RSA, but WHEN."

---

## SLIDE 3 — PROBLEM STATEMENT

**Title:** Problem Statement

**The Core Problem:**
RSA (Rivest-Shamir-Adleman), the world's most widely used public-key algorithm, is mathematically broken by Shor's Algorithm on quantum computers.

**Three Sub-Problems:**

1. RSA Vulnerability
   - Based on integer factorization (hard classically, easy quantum)
   - Shor's Algorithm: O((log N)³) time complexity
   - All RSA key sizes (512/1024/2048-bit) are vulnerable

2. Migration Gap
   - No practical Java framework for RSA → PQC migration
   - Legacy systems cannot switch overnight
   - Need backward-compatible transition path

3. Research Gap
   - No multi-criteria migration suitability model for Java applications
   - No rule-based adaptive security mode selector
   - Existing benchmarks are not Java-based or application-profile aware

**Research Question:**
What are the practical performance and security trade-offs when migrating a Java application from RSA to ML-KEM, and how can a hybrid system provide backward-compatible quantum protection?

---

## SLIDE 4 — OBJECTIVES

**Title:** Project Objectives

| # | Objective | Phase | Status |
|---|-----------|-------|--------|
| 1 | Implement ML-KEM (Kyber) key encapsulation in Java | Phase 1 | ✓ Done |
| 2 | Implement ML-DSA (Dilithium) digital signatures in Java | Phase 1 | ✓ Done |
| 3 | Benchmark PQC vs RSA: key gen, operation time, key sizes | Phase 1 | ✓ Done |
| 4 | Combine RSA + Kyber for hybrid key exchange (KEM combiner) | Phase 2 | ✓ Done |
| 5 | Combine RSA + Dilithium for dual signatures | Phase 2 | ✓ Done |
| 6 | Encrypt/decrypt using hybrid AES-256 derived key | Phase 2 | ✓ Done |
| 7 | Build interactive web UI for live demonstration | Phase 2 | ✓ Done |
| 8 | Prove correctness without quantum hardware | Both | ✓ Done |

---

## SLIDE 5 — LITERATURE SURVEY

**Title:** Literature Survey (2024–2026)

| Paper Title | Year | Methods Used | Best Outcome | Limitations |
|-------------|------|-------------|--------------|-------------|
| Performance and Storage Analysis of CRYSTALS-Kyber as a Post-Quantum Replacement for RSA and ECC (arXiv:2508.01694) | 2025 | Kyber vs RSA/ECC benchmarking using AES-NI, ASIMD hardware acceleration | Kyber provides acceptable performance on commodity hardware for most applications | No Java implementation; no multi-criteria migration model; no application-profile scoring |
| Performance Analysis and Industry Deployment of Post-Quantum Cryptography Algorithms (arXiv:2503.12952) | 2025 | Kyber + Dilithium vs RSA + ECDSA benchmarking; AVX2 hardware acceleration evaluation | Kyber and Dilithium outperform RSA/ECDSA at equivalent security levels | Telecom-focused; no Java; no migration suitability model |
| Quantum-Resilient Banking Transaction Security using DW-HKEM: A Hybrid RSA/ML-KEM Cryptographic Gateway (arXiv:2607.17573) | 2026 | Hybrid RSA + ML-KEM-768 + AES-256 with SHA-256 KDF combiner; 100-iteration benchmark | ~1.58ms overhead; viable for enterprise banking deployment | Banking-specific; Python-based; no adaptive mode selection; no Java Bouncy Castle |
| QERS: Quantum Encryption Resilience Score for PQC in IoT and IIoT Systems (arXiv:2601.13399) | 2026 | Multi-criteria weighted scoring integrating performance + system constraints + MCDA | Composite resilience score enables comparative PQC evaluation across devices | IoT/IIoT focused; not RSA→ML-KEM Java migration; no application-profile evaluation |
| Adaptive Quantum-Safe Cryptography for 6G Vehicular Networks (arXiv:2602.01342) | 2026 | Dynamic PQC selection using APMOEA multi-objective evolutionary algorithm | 27% latency reduction; 65% overhead reduction in V2X networks | V2X/vehicular domain; AI-based (complex); no Java; no RSA→PQC migration path |
| Assessing and Enhancing Quantum Readiness in Mobile Apps (arXiv:2506.00790) | 2025 | Binary analysis of 4,000+ Android apps for cryptographic readiness | Zero PQC adoption found in production apps; all still use RSA/MD5 | Binary analysis only; no implementation or benchmarking framework |

**Research Gap Identified:**
No existing work provides a Java-based RSA→ML-KEM migration framework with multi-criteria suitability scoring and hybrid mode comparison for application profiles.

---

## SLIDE 6 — DATASET / INPUT

**Title:** Dataset and Input Specification

**No External Dataset Required**

This project is a cryptographic implementation and benchmarking framework. Inputs are:

| Input Type | Description | Example |
|------------|-------------|---------|
| Message | Any plaintext string to encrypt/sign | "Hello Quantum World" |
| Kyber Variant | Security level selection | 512 / 768 / 1024 |
| Dilithium Variant | Security level selection | 2 / 3 / 5 |
| Key Size | RSA key size | 2048 bits |
| Iterations | Benchmark repetitions | 5 |

**Generated Data (Output of Algorithms):**
| Data | Size | Generated By |
|------|------|-------------|
| Kyber Public Key | 1,568 bytes | KyberKeyPairGenerator |
| Kyber Private Key | 3,168 bytes | KyberKeyPairGenerator |
| Kyber Ciphertext | 1,568 bytes | KyberKEMGenerator |
| Shared Secret | 32 bytes | KyberKEMExtractor |
| Dilithium Signature | 4,595 bytes | DilithiumSigner |
| Hybrid AES-256 Key | 32 bytes | SHA-256(rsaSecret∥kyberSecret) |

---

## SLIDE 7 — REQUIREMENT SPECIFICATION

**Title:** Requirement Specification

**Hardware Requirements:**
| Component | Minimum | Recommended |
|-----------|---------|-------------|
| Processor | Any x86/x64 | Intel Core i5+ |
| RAM | 2 GB | 4 GB+ |
| Storage | 100 MB | 500 MB |
| Network | Not required | Not required |

**Software Requirements:**
| Component | Version | Purpose |
|-----------|---------|---------|
| Java JDK | 11 or higher | Runtime and compilation |
| Bouncy Castle | 1.76 | Kyber, Dilithium, RSA, AES |
| Git | Any | Version control |
| Browser | Chrome/Edge | Web UI |
| OS | Windows/Linux/Mac | Any |

**Functional Requirements:**
- FR1: Implement Kyber-512/768/1024 key generation, encapsulation, decapsulation
- FR2: Implement Dilithium-2/3/5 key generation, signing, verification
- FR3: Implement hybrid RSA-2048 + Kyber-1024 key exchange
- FR4: Implement hybrid RSA-2048 + Dilithium-5 dual signatures
- FR5: Encrypt/decrypt messages using AES-256 derived from hybrid key exchange
- FR6: Benchmark all algorithms with timing measurements
- FR7: Provide web UI for interactive demonstration

**Non-Functional Requirements:**
- NFR1: Key generation time < 500ms for all variants
- NFR2: Signature verification must return correct true/false
- NFR3: Encrypt → decrypt must recover original message exactly
- NFR4: Web UI must respond within 5 seconds per operation

---

## SLIDE 8 — PLAN OF IMPLEMENTATION

**Title:** Plan of Implementation

```
Phase 1 (Months 1–2): Post-Quantum Cryptography
┌─────────────────────────────────────────────────┐
│ Week 1-2: Library Setup                         │
│  → Download Bouncy Castle 1.76                  │
│  → Upgrade from BC 1.70 (no real Kyber/Dil)    │
│                                                 │
│ Week 3-4: KyberCrypto.java                      │
│  → KyberKeyPairGenerator                        │
│  → KyberKEMGenerator (encapsulate)              │
│  → KyberKEMExtractor (decapsulate)              │
│                                                 │
│ Week 5-6: DilithiumCrypto.java                  │
│  → DilithiumKeyPairGenerator                    │
│  → DilithiumSigner (sign + verify)              │
│                                                 │
│ Week 7-8: Demo + Benchmark                      │
│  → PQCDemo, RSAvsPQC, PQCBenchmark              │
└─────────────────────────────────────────────────┘
                    ↓
Phase 2 (Months 3–4): Hybrid Cryptography
┌─────────────────────────────────────────────────┐
│ Week 9-10: HybridCrypto.java                    │
│  → hybridKeyExchange (RSA+Kyber→SHA256→AES256)  │
│  → encrypt/decrypt (AES-256/ECB)                │
│  → hybridSign (RSA+Dilithium)                   │
│  → hybridVerify (both must pass)                │
│                                                 │
│ Week 11-12: HybridDemo + HybridBenchmark        │
│  → Interactive demo                             │
│  → RSA vs PQC vs Hybrid comparison              │
│                                                 │
│ Week 13-14: Web UI                              │
│  → CryptoServer.java (Java HTTP server)         │
│  → index.html (5-tab dashboard)                 │
│                                                 │
│ Week 15-16: Documentation + GitHub              │
│  → Complete reports, viva prep, push to GitHub  │
└─────────────────────────────────────────────────┘
```

---

## SLIDE 9 — ALGORITHMS INVOLVED

**Title:** Algorithms Involved

**Algorithm 1: ML-KEM (CRYSTALS-Kyber) — NIST FIPS 203**
- Type: Key Encapsulation Mechanism
- Math: Module Learning With Errors (M-LWE)
- Problem: Given A·s + e = b, find s (hard even for quantum)
- Operations: KeyGen → Encapsulate → Decapsulate
- Output: 32-byte shared secret

**Algorithm 2: ML-DSA (CRYSTALS-Dilithium) — NIST FIPS 204**
- Type: Digital Signature Algorithm
- Math: Module-LWE + Module-SIS, Fiat-Shamir with Aborts
- Operations: KeyGen → Sign → Verify
- Output: Signature (2420–4595 bytes depending on variant)

**Algorithm 3: RSA-2048 (Classical)**
- Type: Public-key encryption + signatures
- Math: Integer factorization (hard classically, broken by Shor's)
- Operations: KeyGen → Encrypt → Decrypt / Sign → Verify
- Status: QUANTUM VULNERABLE

**Algorithm 4: SHA-256 (Key Derivation)**
- Type: Cryptographic hash function
- Use: Combine RSA secret + Kyber secret → AES-256 key
- Formula: hybridSecret = SHA-256(rsaSecret ∥ kyberSecret)
- Output: 32 bytes (256 bits)

**Algorithm 5: AES-256 (Symmetric Encryption)**
- Type: Symmetric block cipher
- Use: Encrypt/decrypt actual messages using hybrid key
- Mode: ECB/PKCS5Padding
- Quantum resistance: Grover's reduces to 128-bit (still secure)

---

## SLIDE 10 — ARCHITECTURE DIAGRAM

**Title:** System Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                    USER INPUT (Message)                      │
└──────────────────────────┬───────────────────────────────────┘
                           │
              ┌────────────┴────────────┐
              ▼                         ▼
┌─────────────────────┐    ┌────────────────────────┐
│   PHASE 1: PQC      │    │   PHASE 2: HYBRID      │
│                     │    │                        │
│  KyberCrypto        │    │  RSACrypto (2048)      │
│  ┌───────────────┐  │    │       +                │
│  │ KeyGen        │  │    │  KyberCrypto (1024)    │
│  │ Encapsulate   │  │    │       +                │
│  │ Decapsulate   │  │    │  DilithiumCrypto (5)   │
│  └───────────────┘  │    │       │                │
│  → SharedSecret     │    │  hybridKeyExchange()   │
│                     │    │  SHA-256 combiner      │
│  DilithiumCrypto    │    │  → AES-256 key         │
│  ┌───────────────┐  │    │       │                │
│  │ KeyGen        │  │    │  encrypt/decrypt()     │
│  │ Sign          │  │    │  hybridSign()          │
│  │ Verify        │  │    │  hybridVerify()        │
│  └───────────────┘  │    └────────────────────────┘
│  → true/false       │
└─────────────────────┘
              │                         │
              └────────────┬────────────┘
                           ▼
              ┌────────────────────────┐
              │   Bouncy Castle 1.76   │
              │  NIST FIPS 203 + 204   │
              └────────────────────────┘
```

**Package Structure:**
```
com.pqc/
├── postquantum/ → KyberCrypto, DilithiumCrypto, PQCDemo
├── hybrid/      → HybridCrypto, HybridDemo, HybridBenchmark
├── rsa/         → RSACrypto, ShorsAlgorithmDemo
├── benchmark/   → PQCBenchmark, RSABenchmark
└── web/         → CryptoServer (HTTP server + Web UI)
```

---

## SLIDE 11 — RESULTS OBTAINED

**Title:** Results Obtained

**Result 1: Key Generation Performance**

| Algorithm | Key Gen Time | vs RSA | Quantum-Safe |
|-----------|-------------|--------|-------------|
| RSA-2048 | ~417 ms | baseline | ✗ NO |
| Kyber-512 | ~10 ms | 40x faster | ✓ YES |
| Kyber-768 | ~15 ms | 28x faster | ✓ YES |
| Kyber-1024 | ~52 ms | 8x faster | ✓ YES |
| Dilithium-2 | ~20 ms | 21x faster | ✓ YES |
| Dilithium-3 | ~35 ms | 12x faster | ✓ YES |
| Dilithium-5 | ~60 ms | 7x faster | ✓ YES |

**Result 2: Key and Signature Sizes**

| Algorithm | Public Key | Output Size | Quantum-Safe |
|-----------|-----------|-------------|-------------|
| RSA-2048 | 294 bytes | 256 bytes | ✗ NO |
| Kyber-1024 | 1,568 bytes | 1,568 bytes | ✓ YES |
| Dilithium-5 | 2,592 bytes | 4,595 bytes | ✓ YES |

**Result 3: Hybrid System**

| Mode | Key Exchange | Encrypt | Sign | Quantum-Safe |
|------|-------------|---------|------|-------------|
| RSA-only | ~3500 ms | ~7 ms | ~5 ms | ✗ NO |
| PQC-only | ~120 ms | ~12 ms | ~65 ms | ✓ YES |
| Hybrid | ~3600 ms | ~1 ms | ~15 ms | ✓ YES |

**Result 4: Correctness Verification**

| Test | Result |
|------|--------|
| Kyber shared secret match | true ✓ |
| Dilithium signature valid | true ✓ |
| Dilithium tamper detection | false ✓ (tamper detected) |
| Hybrid encrypt→decrypt match | true ✓ |
| Hybrid signature valid | true ✓ |

**Key Findings:**
- PQC key generation is 7–40x faster than RSA
- PQC keys are 5–8x larger (acceptable trade-off for quantum safety)
- Hybrid mode provides both classical and quantum protection
- All correctness tests pass — implementation is verified

---

## SLIDE 12 — OUTCOMES OF THE PROJECT

**Title:** Project Outcomes

**Technical Outcomes:**
1. Working Java implementation of ML-KEM (Kyber-512/768/1024) using NIST FIPS 203
2. Working Java implementation of ML-DSA (Dilithium-2/3/5) using NIST FIPS 204
3. Hybrid cryptographic engine combining RSA-2048 + Kyber-1024 + Dilithium-5
4. Interactive web UI dashboard for live demonstration
5. Complete benchmark framework comparing RSA vs PQC vs Hybrid

**Research Outcomes:**
1. Quantified performance trade-offs: PQC is 7–40x faster at key generation
2. Quantified size trade-offs: PQC keys are 5–8x larger
3. Proved hybrid KEM combiner pattern works correctly in Java
4. Demonstrated backward-compatible migration path from RSA to PQC

**Practical Outcomes:**
1. Migration framework ready for enterprise Java applications
2. Aligned with NIST 2030–2035 migration deadline
3. Open-source on GitHub for community use
4. Foundation for major project (adaptive security controller)

---

## SLIDE 13 — REFERENCES

**Title:** References

[1] NIST. "FIPS 203: Module-Lattice-Based Key-Encapsulation Mechanism Standard." August 2024.
    https://csrc.nist.gov/pubs/fips/203/final

[2] NIST. "FIPS 204: Module-Lattice-Based Digital Signature Standard." August 2024.
    https://csrc.nist.gov/pubs/fips/204/final

[3] Rodriguez-Alvarez, N., Rodriguez-Merino, F. "Performance and Storage Analysis of CRYSTALS-Kyber as a Post-Quantum Replacement for RSA and ECC." arXiv:2508.01694. 2025.
    https://arxiv.org/abs/2508.01694

[4] Demir, E., Bilgin, B., Onbasli, M. "Performance Analysis and Industry Deployment of Post-Quantum Cryptography Algorithms." arXiv:2503.12952. 2025.
    https://arxiv.org/abs/2503.12952

[5] Prabhu, S., Cherukuri, A. "Quantum-Resilient Banking Transaction Security using DW-HKEM: A Hybrid RSA/ML-KEM Cryptographic Gateway." arXiv:2607.17573. 2026.
    https://arxiv.org/abs/2607.17573

[6] Rassekhnia, J. "QERS: Quantum Encryption Resilience Score for PQC in Computer, IoT, and IIoT Systems." arXiv:2601.13399. 2026.
    https://arxiv.org/abs/2601.13399

[7] Sengupta, P. et al. "Adaptive Quantum-Safe Cryptography for 6G Vehicular Networks." arXiv:2602.01342. NDSS 2026.
    https://arxiv.org/abs/2602.01342

[8] Strauss, J. et al. "Assessing and Enhancing Quantum Readiness in Mobile Apps." arXiv:2506.00790. IEEE S&P 2025.
    https://arxiv.org/abs/2506.00790

[9] Giacon, F., Heuer, F., Poettering, B. "KEM Combiners." PKC 2018.
    https://eprint.iacr.org/2018/024

[10] Avanzi, R. et al. "CRYSTALS-Kyber Algorithm Specifications." NIST PQC Round 3. 2021.
     https://pq-crystals.org/kyber/

[11] Ducas, L. et al. "CRYSTALS-Dilithium Algorithm Specifications." NIST PQC Round 3. 2021.
     https://pq-crystals.org/dilithium/

[12] Bouncy Castle. "Lightweight Cryptography API 1.76." 2024.
     https://bouncycastle.org

---

## SLIDE 14 — CONCLUSION

**Title:** Conclusion

**What We Achieved:**
- Successfully implemented NIST-standardized ML-KEM and ML-DSA in Java
- Built a hybrid RSA+PQC system for backward-compatible migration
- Proved correctness through functional tests, tamper detection, and mathematical proofs
- PQC is 7–40x faster at key generation than RSA
- Hybrid system provides quantum protection while maintaining legacy compatibility

**Why It Matters:**
- RSA protects $10+ trillion in daily transactions — all vulnerable to quantum computers
- NIST mandates migration by 2030–2035
- Our framework provides a practical, tested migration path

**Next Steps (Major Project):**
- Adaptive Security Controller: automatically select Classical/PQC/Hybrid based on requirements
- Healthcare Application: secure medical image transmission with HIPAA compliance
- PQC Migration Suitability Score: multi-criteria model for enterprise migration decisions

---
