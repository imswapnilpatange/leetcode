class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int count = 0;
        for(int i = 0; i < m; i++){                                         //Build prefix sum in-place
            for(int j = 0; j < n; j++){
                if(i > 0) grid[i][j] += grid[i - 1][j];                     //Step 1: Add top contribution
                if(j > 0) grid[i][j] += grid[i][j - 1];                     //Step 2: Add left contribution
                if(i > 0 && j > 0) grid[i][j] -= grid[i - 1][j - 1];        //Step 3: Remove double counted overlap 

                if(grid[i][j] <= k) count++;                                //Step 4: Check constraint
            }
        }
        
        return count;
    }
}