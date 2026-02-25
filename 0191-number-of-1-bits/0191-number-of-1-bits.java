class Solution {
    public int hammingWeight(int n) {
        if(n == 0) return 0;        // Edge Case

        int count = 0;
        while(n != 0){              // For all iteration, remove rightmost bit
            n = n & (n - 1);        // Core Operation: removes lowest set bit
            count++;                // Increment count for every removed bit
        }

        return count;
    }
}

/**
// Another Option: Time Complexity - O(1); Space Complexity - O(1)
class Solution {
    public int hammingWeight(int n) {
        return Integer.bitCount(n);
        
    }
}
*/