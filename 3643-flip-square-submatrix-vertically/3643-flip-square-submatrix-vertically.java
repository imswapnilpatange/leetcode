/**
    Step 1: Understand Indices
    - Submatrix start at (x,y)
    - Rows range: x → x + k - 1
    - Column range: y → y + k - 1

    Step 2: Decide number of swaps
    - Only need to process k/2 rows
    - Each swap fixes two rows (top and bottom)

    Step 3: Find opposite row
    - For current row i: i2 = x + k - 1 - (i - x)
    - This mirrows the row within the submatrix

    Step 4: Swap element column wise
    - For each column j from y → y + k - 1
    - Swap grid[i][j] → grid[i2][j]
 */
class Solution {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        for(int i = x; i < x + k/2; i++){
            int i2 = x + k - 1 - (i - x);

            for(int j = y; j < y + k; j++){
                int temp = grid[i][j];
                grid[i][j] = grid[i2][j];
                grid[i2][j] = temp;
            }
        }

        return grid;
    }
}