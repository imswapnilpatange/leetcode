class Solution {
    public int numSpecial(int[][] mat) {
        if(mat == null || mat.length == 0 || mat[0].length == 0) 
            return 0;                                   // Edge Case
        
        int m = mat.length;
        int n = mat[0].length;


        int [] rowSum = new int [m];
        int [] colSum = new int [n];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(mat[i][j] == 1){                     // Precalculate rowSum and colSum
                    rowSum[i]++;                
                    colSum[j]++;
                }
            }
        }

        int count = 0;
        for(int i = 0; i < m; i++){
            if(rowSum[i] != 1) continue;                // rowSum has to be 1

            for(int j = 0; j < n; j++){
                if(colSum[j] == 1 && mat[i][j] == 1)    // colSum also has to be 1
                    count++;
            }
        }

        return count;
    }
}