/*
    1. State Modeling
        Define a rhombus using:
        - Top vertex = (i, j)
        - Edge length = edge

        Other vertices:
            Left   : (i + edge, j - edge)
            Right  : (i + edge, j + edge)
            Bottom : (i + 2*edge, j)
        
        Rhombus height = 2 * edge
        Validity constraints (If violated → rhombus outside grid):
            i + 2*edge < m
            j - edge >= 0
            j + edge < n

        Special case: edge = 0 → rhombus = single cell (grid[i][j]).

    2. Border Traversal
        Border has four diagonal edges:
            Top → Right   : (i + k, j + k)
            Right → Bottom: (i + edge + k, j + edge - k)
            Bottom → Left : (i + 2*edge - k, j - k)
            Left → Top    : (i + edge - k, j - edge + k)
        
        k range: 0 ≤ k < edge
        Each edge contributes edge cells.
        Total border cells: 4 * edge

    3. Invariants
        Invariant 1 — Boundary Safety
        Valid iff:
            i + 2*edge < m
            j - edge >= 0
            j + edge < n

        Invariant 2 — Unique Border Coverage
        k runs 0..edge-1 → each border cell visited once; vertices not double-counted.

        Invariant 3 — Distinct Maximum Tracking
        TreeSet stores unique rhombus sums.
        If size > 3 → remove smallest element.
*/

class Solution {
    int m;
    int n;
    public int[] getBiggestThree(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int maximumEdge = (Math.min(m, n)+1)/2;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                for (int edge = 0; edge <= maximumEdge; edge++){
                    int sum = getSum(grid, i, j, edge);
                    if (sum != 0) set.add(sum);
                    if (set.size() > 3) set.pollFirst();
                }
            }
        }
        int[] ans = new int[set.size()];
        int index = set.size()-1;
        for (int i : set){
            ans[index--] = i;
        }
        return ans;
    }
    private int getSum(int[][] grid, int i,int j,int edge){
        if (edge == 0) return grid[i][j];
        if (i+2*edge>=m || j-edge<0 || j+edge>=n) return 0;
        int sum = 0;        
        for (int k = 0; k < edge; k++){
            sum += grid[i+k][j+k]
            + grid[i+edge+k][j+edge-k]
            + grid[i+2*edge-k][j-k]
            + grid[i+edge-k][j-edge+k];            
        }
        return sum;
    }
}