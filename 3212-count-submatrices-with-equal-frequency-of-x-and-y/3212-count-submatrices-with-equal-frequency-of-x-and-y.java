/**
----------STEP BY STEP LOGIC----------
    1. Any valid submatrix must include (0,0), so only prefix matters
    2. For each cell (x,y) track:
        → x count from (0,0) to (i,j)
        → y count from (0,0) to (i,j)
    3. Use column-wise accumulation to avoid full 2D prefix array
    4. While iterating row:
        → keep running count of x and y
        → update column prefix
    5. At each (i,j): if(x == y && x > 0) → Valid submatrix
    6. Count all such positions
 */

class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;

        int[][] col = new int[n][2];                            //[x count, y count] for that column

        for(int i = 0; i < m; i++){
            int x = 0, y = 0;                                   //row cumulative

            for(int j = 0; j < n; j++){
                if(grid[i][j] == 'X') x++;
                else if(grid[i][j] == 'Y') y++;

                col[j][0] += x;                                 //Add x contribution to column profile
                col[j][1] += y;                                 //Add y contribution to column profile

                if(col[j][0] == col[j][1] && col[j][0] > 0)
                    ans++;
            }
        }

        return ans;
    }
}