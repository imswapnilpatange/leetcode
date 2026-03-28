class Solution {
    int[] parent;

    public String findTheString(int[][] lcp) {
        int n = lcp.length;
        parent = new int[n];

        // Initialize DSU
        for (int i = 0; i < n; i++) parent[i] = i;

        // Step 0: Basic validation (diagonal check)
        for (int i = 0; i < n; i++) {
            if (lcp[i][i] != n - i) return "";
        }

        // Step 1: Union all required equal positions
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int len = lcp[i][j];

                // Boundary validation (prevents ArrayIndexOutOfBounds)
                if (i + len > n || j + len > n) return "";

                for (int k = 0; k < len; k++) {
                    union(i + k, j + k);
                }
            }
        }

        // Step 2: Assign characters to each component
        char[] s = new char[n];
        Map<Integer, Character> map = new HashMap<>();
        char curr = 'a';

        for (int i = 0; i < n; i++) {
            int p = find(i);

            if (!map.containsKey(p)) {
                if (curr > 'z') return ""; // more than 26 groups not possible
                map.put(p, curr++);
            }

            s[i] = map.get(p);
        }

        // Step 3: Validate by recomputing LCP
        int[][] dp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (s[i] == s[j]) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                }

                if (dp[i][j] != lcp[i][j]) return "";
            }
        }

        return new String(s);
    }

    // DSU find with path compression
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // DSU union
    void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa != pb) parent[pa] = pb;
    }
}