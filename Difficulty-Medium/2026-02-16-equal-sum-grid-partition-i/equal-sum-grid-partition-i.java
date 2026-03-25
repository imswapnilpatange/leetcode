class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        long total = 0;

        // Step 1: Compute total sum
        for (int[] row : grid) {
            for (int val : row) {
                total += val;
            }
        }

        // Step 2: If total is odd → not possible
        if (total % 2 != 0) return false;

        long target = total / 2;

        // Step 3: Check horizontal cuts
        long prefix = 0;
        for (int i = 0; i < m - 1; i++) { // ensure non-empty split
            for (int j = 0; j < n; j++) {
                prefix += grid[i][j];
            }
            if (prefix == target) return true;
        }

        // Step 4: Check vertical cuts
        prefix = 0;
        for (int j = 0; j < n - 1; j++) { // ensure non-empty split
            for (int i = 0; i < m; i++) {
                prefix += grid[i][j];
            }
            if (prefix == target) return true;
        }

        return false;
    }
}