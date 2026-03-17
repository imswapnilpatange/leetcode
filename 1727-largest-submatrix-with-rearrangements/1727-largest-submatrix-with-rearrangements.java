class Solution {
    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        int maxArea = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){                 //Step 1: Build histogram height
                if(matrix[r][c] == 0) height[c] = 0;
                else height[c] += 1;
            }

            int[] sorted = height.clone();              //Step 2: Copy height for sorting
            Arrays.sort(sorted);                        //Step 3: Sort height

            for(int i = 0; i < n; i++){                 //Step 4: Compute maxArea
                int h = sorted[n - 1 - i];
                int width = i + 1;

                int area = h * width;
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }
}