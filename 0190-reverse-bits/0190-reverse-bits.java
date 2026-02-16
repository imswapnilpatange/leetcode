public class Solution {
    // Reverse bits of a 32-bit integer
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;       // Make space for the next bit
            result |= (n & 1);  // Add the least significant bit of n
            n >>= 1;            // Shift n right for the next bit
        }
        return result;
    }
}
