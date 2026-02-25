class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n <= 0) return false;            // If n = 2^k exists only for positive numbers

        return (n & (n - 1)) == 0;          // Condition to check if n = 2^k
    }
}
