/*
Fancy Sequence — Core Model (Compressed)

State Transform:
Every element follows an affine transform:
    value = stored * A + B   (mod M)

Global State:
    A → cumulative multiplier
    B → cumulative addition

Operation Transitions:
    append(val):
        stored = (val - B) * inv(A) mod M
        // reverse-transform so future ops apply correctly

    addAll(inc):
        B = B + inc

    multAll(m):
        A = A * m
        B = B * m

Invariant:
    For every index i:
        current_value = stored[i] * A + B

This allows O(1) updates for addAll/multAll and O(1) retrieval.
*/

import java.util.*;

class Fancy {

    private static final long MOD = 1_000_000_007;

    private List<Long> arr = new ArrayList<>();

    private long mul = 1;   // global multiplier
    private long add = 0;   // global addition

    public Fancy() {}

    public void append(int val) {

        // Reverse transform so future ops apply correctly
        long inv = modInverse(mul);

        long stored = ((val - add) % MOD + MOD) % MOD;
        stored = (stored * inv) % MOD;

        arr.add(stored);
    }

    public void addAll(int inc) {

        // value = value + inc
        add = (add + inc) % MOD;
    }

    public void multAll(int m) {

        // value = value * m
        mul = (mul * m) % MOD;
        add = (add * m) % MOD;
    }

    public int getIndex(int idx) {

        if (idx >= arr.size()) return -1;

        long val = arr.get(idx);

        long res = (val * mul) % MOD;
        res = (res + add) % MOD;

        return (int) res;
    }

    // Fermat modular inverse
    private long modInverse(long x) {
        return modPow(x, MOD - 2);
    }

    private long modPow(long base, long exp) {

        long res = 1;

        base %= MOD;

        while (exp > 0) {

            if ((exp & 1) == 1)
                res = (res * base) % MOD;

            base = (base * base) % MOD;
            exp >>= 1;
        }

        return res;
    }
}