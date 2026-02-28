//Optimized: Time Complexity - O(N): Space Complexity - O(1)
class Solution {
    public int concatenatedBinary(int n) {
        final int MOD = 1_000_000_007;
        
        long ans = 0;                               // Stores running concatenation
        int bitLength = 0;                          // Current bit length
        
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) == 0) bitLength++;    // If i is power of 2, bit length increases
            
            ans = ((ans << bitLength) + i) % MOD;   // Shift previous result and append current number
        }
        
        return (int) ans;
    }
}

/**
// Intuitive (Kind of Brute Force): Time Complexity - O(N logN): Space Complexity - O(N logN)
class Solution {
    public int concatenatedBinary(int n) {
        StringBuilder binaryString = new StringBuilder();
        for(int i = 1; i <= n; i++){
            binaryString.append(Integer.toBinaryString(i));
        }
    
        long mod = 1000000007L;
        long decimalResult = 0;

        for (int i = 0; i < binaryString.length(); i++) {
            // Convert character '0' or '1' to its integer value
            int bit = binaryString.charAt(i) - '0';
            
            // Apply (current_result * 2 + bit) % mod
            decimalResult = (decimalResult * 2 + bit) % mod;
        }
        return (int)decimalResult;
    }
}
 */
