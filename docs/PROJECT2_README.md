# Project 2: Post-Quantum Cryptography (PQC)

## Quick Start

### Run PQC Demo
```bash
run-pqc-demo.bat
```
Interactive demo of Kyber (key encapsulation) and Dilithium (signatures).

### Run PQC Benchmark
```bash
run-pqc-benchmark.bat
```
Compare RSA vs PQC performance across all variants.

---

## Implemented Algorithms

### CRYSTALS-Kyber (Key Encapsulation)
- **Kyber-512**: 128-bit quantum security, 800-byte public key
- **Kyber-768**: 192-bit quantum security, 1,184-byte public key
- **Kyber-1024**: 256-bit quantum security, 1,568-byte public key

### CRYSTALS-Dilithium (Digital Signatures)
- **Dilithium-2**: 128-bit quantum security, 1,312-byte public key
- **Dilithium-3**: 192-bit quantum security, 1,952-byte public key
- **Dilithium-5**: 256-bit quantum security, 2,592-byte public key

---

## Key Features

✓ **Quantum-Resistant**: Secure against Shor's algorithm  
✓ **NIST-Approved**: Based on FIPS 203 (Kyber) and FIPS 204 (Dilithium)  
✓ **Fast Performance**: Key generation 10-15x faster than RSA  
✓ **Production-Ready**: Comparable latency to RSA-2048  

---

## Performance Summary

| Algorithm | Key Gen | Operation | Quantum-Safe |
|-----------|---------|-----------|--------------|
| RSA-2048 | ~150ms | ~5ms | ✗ NO |
| Kyber-1024 | ~12ms | ~4ms | ✓ YES |
| Dilithium-5 | ~18ms | ~5ms | ✓ YES |

---

## Files

- `KyberCrypto.java` - Key encapsulation mechanism
- `DilithiumCrypto.java` - Digital signature algorithm
- `PQCDemo.java` - Interactive demonstration
- `PQCBenchmark.java` - Performance comparison tool

---

## Documentation

See [PROJECT2_REPORT.md](PROJECT2_REPORT.md) for complete documentation.

---

## Next Steps

**Project 3**: Hybrid Cryptography (RSA + PQC)
- Combine classical and post-quantum algorithms
- Provide backward compatibility
- Enable gradual migration path
