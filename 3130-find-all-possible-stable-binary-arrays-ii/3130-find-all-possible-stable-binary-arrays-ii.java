class Solution {

    static final int MOD = 1_000_000_007;

    public int numberOfStableArrays(int zero, int one, int limit) {

        long[][] dp0 = new long[zero + 1][one + 1];
        long[][] dp1 = new long[zero + 1][one + 1];

        long[][] pref0 = new long[zero + 1][one + 1];
        long[][] pref1 = new long[zero + 1][one + 1];

        // Base states
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            dp0[i][0] = 1;
        }

        for (int j = 1; j <= Math.min(one, limit); j++) {
            dp1[0][j] = 1;
        }

        // Initialize prefix
        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {
                pref0[i][j] = dp0[i][j];
                pref1[i][j] = dp1[i][j];
            }
        }

        // Build DP
        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {

                if (i > 0) {
                    int l = Math.max(0, i - limit);
                    int r = i - 1;

                    long val = pref1[r][j];
                    if (l > 0) val = (val - pref1[l - 1][j] + MOD) % MOD;

                    dp0[i][j] = (dp0[i][j] + val) % MOD;
                }

                if (j > 0) {
                    int l = Math.max(0, j - limit);
                    int r = j - 1;

                    long val = pref0[i][r];
                    if (l > 0) val = (val - pref0[i][l - 1] + MOD) % MOD;

                    dp1[i][j] = (dp1[i][j] + val) % MOD;
                }

                pref0[i][j] = ((j > 0 ? pref0[i][j - 1] : 0) + dp0[i][j]) % MOD;
                pref1[i][j] = ((i > 0 ? pref1[i - 1][j] : 0) + dp1[i][j]) % MOD;
            }
        }

        return (int)((dp0[zero][one] + dp1[zero][one]) % MOD);
    }
}