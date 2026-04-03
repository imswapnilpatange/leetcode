class Solution {
    public int maximumAmount(int[][] coins) {
        int m = coins.length, n = coins[0].length;

        // dp[i][j][k] -> max coins at (i,j) using k neutralizations
        int[][][] dp = new int[m][n][3];

        // Initialize with very small values
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        // Step 1: Initialize start cell
        int start = coins[0][0];

        dp[0][0][0] = start;

        if (start < 0) {
            dp[0][0][1] = 0; // neutralize
        }

        // Step 2: Fill DP table
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) continue;

                int val = coins[i][j];

                for (int k = 0; k < 3; k++) {

                    int bestPrev = Integer.MIN_VALUE;

                    // From top
                    if (i > 0) bestPrev = Math.max(bestPrev, dp[i - 1][j][k]);

                    // From left
                    if (j > 0) bestPrev = Math.max(bestPrev, dp[i][j - 1][k]);

                    // Option 1: take value normally
                    if (bestPrev != Integer.MIN_VALUE) {
                        dp[i][j][k] = Math.max(dp[i][j][k], bestPrev + val);
                    }

                    // Option 2: neutralize if negative
                    if (val < 0 && k > 0) {
                        int bestPrevNeutral = Integer.MIN_VALUE;

                        if (i > 0) bestPrevNeutral = Math.max(bestPrevNeutral, dp[i - 1][j][k - 1]);
                        if (j > 0) bestPrevNeutral = Math.max(bestPrevNeutral, dp[i][j - 1][k - 1]);

                        if (bestPrevNeutral != Integer.MIN_VALUE) {
                            dp[i][j][k] = Math.max(dp[i][j][k], bestPrevNeutral);
                        }
                    }
                }
            }
        }

        // Step 3: Take best among k = 0,1,2
        int ans = Integer.MIN_VALUE;
        for (int k = 0; k < 3; k++) {
            ans = Math.max(ans, dp[m - 1][n - 1][k]);
        }

        return ans;
    }
}