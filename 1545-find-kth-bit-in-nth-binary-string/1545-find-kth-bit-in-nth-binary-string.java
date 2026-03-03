class Solution {

    /*
    LENGTH FORMULA:
    Given: S_n = S_{n-1} + "1" + reverse(invert(S_{n-1}))
    Let L(n) = |S_n|
    L(n) = L(n-1) + 1 + L(n-1) = 2L(n-1) + 1
    Pattern: L(1) = 1=2^1-1, L(2) = 3=2^2-1, L(3) = 7=2^3-1
    Induction: assume L(n-1)=2^{n-1}-1
    L(n)=2(2^{n-1}-1)+1=2^n-2+1=2^n-1 ✔

    MIDDLE INDEX FORMULA:
    Since L(n)=2^n-1
    mid = L(n)/2 + 1 = (2^n-1)/2 + 1 = 2^{n-1}
    Because (2^n-1)/2 = 2^{n-1} - 1/2; +1 → 2^{n-1} + 1/2; integer ⇒ mid=2^{n-1}
    Thus position 2^(n-1) is always middle; that bit is always '1' (structural invariant).

    STATE TRANSITION:
    Let L = 2^n - 1, mid = 2^(n-1)

    If k == mid → return '1'
    If k < mid  → move to (n-1, k)
    If k > mid  → reflect: k' = L - k + 1, flip inversion
    */
    public char findKthBit(int n, int k) {
        
        boolean invert = false; // Tracks parity of inversion
        
        while (n > 1) {
            
            int len = (1 << n) - 1;
            int mid = (len / 2) + 1;   // 2^(n-1)
            
            // Case 1: Middle element
            if (k == mid) {
                return invert ? '0' : '1';
            }
            
            // Case 2: Right half
            if (k > mid) {
                k = len - k + 1;   // Reflect
                invert = !invert;  // Inversion flips
            }
            
            // Move to smaller problem
            n--;
        }
        
        // Base case S1 = "0"
        return invert ? '1' : '0';
    }
}