/**
    Given n coins, build rows where row i contains i coins.
    Return maximum rows, say k, such that: 1 + 2 + ... + k <=n → k(k+1)/2 <= n
    n can be largest int (0 <= n <= 2^31 - 1), use long to avoid overflow. 
 */
class Solution {
    public int arrangeCoins(int n) {
        if(n == 0) return 0; // Edge Case

        long left = 0, right = n;
        while(left <= right){
            long mid = left + (right - left) / 2;
            long coinsNeeded = mid * (mid + 1) / 2;

            if(coinsNeeded == n) return (int) mid; // Exact coins. No row is incomplete
            else if(coinsNeeded < n) left = mid + 1; // Try bigger k
            else right = mid - 1; // Too many coins, reduce k
        }

        return (int) right; //right is largest valid k. Last row will be incomplete
    }
}