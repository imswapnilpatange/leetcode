/**
Assumptions:
    - Only adjacent row swaps allowed
    - For row i, trailingZero[i] >= (n - 1 - i) must hold.
    - Return -1 if impossible

Key Idea:
    1) Precompute trailing zeros for each row.
    2) For each position i (top → bottom):
        - required = n - 1 - i
        - Find first row j >= i with trailingZeros[j] >= required.
        - If none found → return -1.
        - Bubble row j up to i using adjacent swaps and count swaps.

Time: O(n^2)
Space: O(n) 

 */
class Solution {
    public int minSwaps(int[][] grid) {
        int n = grid.length;

        // Step 1: Compute trailing zeros for each row
        int[] trailingZeros = new int[n];

        for(int i = 0; i < n; i++){
            int count = 0;
            for(int j = n - 1; j >= 0; j--){
                if(grid[i][j] == 0) count++;
                else break;
            }
            trailingZeros[i] = count;
        }
        
        int swaps = 0;

        // Step 2: Greedily place rows
        for(int i = 0; i < n; i++){
            int required = n - 1 - i;
            int j = i;

            // Find first row satisfying requirement
            while(j < n && trailingZeros[j] < required) j++;

            // Impossible case
            if(j == n) return -1;

            // Bubble row up to position i
            while(j > i){
                int temp = trailingZeros[j];
                trailingZeros[j] = trailingZeros[j - 1];
                trailingZeros[j - 1] = temp;
                swaps++;
                j--;
            }
        }
        return swaps;
    }
}