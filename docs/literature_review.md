# Literature Review

## Quantum Threat to RSA

### Shor's Algorithm
- Polynomial-time algorithm for integer factorization on quantum computers
- Breaks RSA, DSA, ECDSA security assumptions
- Estimated to require ~2000-4000 logical qubits for 2048-bit RSA

### Timeline
- Current quantum computers: ~100-1000 noisy qubits
- Cryptographically relevant quantum computer (CRQC): estimated 10-30 years
- "Harvest now, decrypt later" attacks are already a concern

## NIST Post-Quantum Cryptography Standardization

### Selected Algorithms (2022)

**Key Encapsulation Mechanisms (KEM):**
- **CRYSTALS-Kyber**: Lattice-based, selected for standardization
  - Security levels: Kyber512, Kyber768, Kyber1024
  - Public key: ~800-1568 bytes
  - Ciphertext: ~768-1568 bytes

**Digital Signatures:**
- **CRYSTALS-Dilithium**: Lattice-based
  - Security levels: Dilithium2, Dilithium3, Dilithium5
  - Public key: ~1312-2592 bytes
  - Signature: ~2420-4595 bytes

### Comparison: RSA vs Kyber

| Parameter | RSA-2048 | Kyber768 |
|-----------|----------|----------|
| Public Key | 256 bytes | 1184 bytes |
| Ciphertext | 256 bytes | 1088 bytes |
| Security Level | ~112 bits (classical) | ~128 bits (post-quantum) |
| Key Gen Speed | Slow | Fast |
| Enc/Dec Speed | Moderate | Very Fast |

## Java Cryptography Ecosystem

### Bouncy Castle
- Industry-standard cryptography provider for Java
- Implements RSA, AES, SHA, and many other algorithms
- Used by major enterprises worldwide
- Excellent documentation and community support

### liboqs-java
- Java wrapper for liboqs (Open Quantum Safe)
- Implements NIST PQC candidates
- Supports Kyber, Dilithium, and other PQC algorithms
- Requires native library installation

## Migration Challenges

1. **Key Size Increase**: PQC keys are 3-10x larger
2. **Protocol Compatibility**: TLS, SSH, VPN need updates
3. **Hardware Constraints**: IoT devices may struggle
4. **Hybrid Transition**: Running both systems during migration
5. **Performance Impact**: Bandwidth and storage overhead

## References
- NIST PQC Project: https://csrc.nist.gov/projects/post-quantum-cryptography
- Kyber Specification: https://pq-crystals.org/kyber/
- Dilithium Specification: https://pq-crystals.org/dilithium/
- Bouncy Castle: https://www.bouncycastle.org/
- Open Quantum Safe: https://openquantumsafe.org/
