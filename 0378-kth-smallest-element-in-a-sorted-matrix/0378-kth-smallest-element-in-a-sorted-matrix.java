class Solution {
    // Using Binary Search 
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];

        while(left < right){

            // Mathematically equivalent to 'mid = (left + right) / 2'
            // and safe since it prevents integer overflow.
            int mid = left + (right - left) / 2;

            int count = countLessEqual(matrix, mid);

            if(count < k){
              // k-th smallest element is larger than mid.
                left = mid + 1;
            } else {
                // k-th smallest element is <= mid
                right = mid;
            }
        }
        
        // When left == right, it points to k-th smallest element 
        return left;
    }

    private int countLessEqual(int[][] matrix, int x){
        int n = matrix.length;
        int count = 0;

        // Starting from left bottom corner 
        int row = n - 1;
        int col = 0;

        while(row >= 0 && col < n){
            if (matrix[row][col] <= x){
                // All elements above this in same column are <= x
                count += row + 1;

                // Move to next column
                col++;
            } else {
                // Since current value is > x, move up
                row--;
            }
        }
        return count;
    }
}