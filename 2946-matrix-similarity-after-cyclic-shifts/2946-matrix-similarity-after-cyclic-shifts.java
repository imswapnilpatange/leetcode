/**
 * Key Idea:
 * Avoid actual shifting. Compare each element with its expected position after k cyclic shifts using index mapping.
 *
 * Index Mapping (n = columns):
 * Even row (left shift):  newCol = (j + k) % n
 * Odd row (right shift): newCol = (j - k % n + n) % n
 *
 * Time: O(m*n), Space: O(1)
 */
class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        // Traverse each row
        for (int i = 0; i < m; i++) {

            // Traverse each column
            for (int j = 0; j < n; j++) {

                int newCol;

                if (i % 2 == 0) {
                    // Even row → left shift
                    newCol = (j + k) % n;
                } else {
                    // Odd row → right shift
                    newCol = (j - k % n + n) % n;
                }

                // Compare original with shifted position
                if (mat[i][j] != mat[i][newCol]) {
                    return false;
                }
            }
        }

        return true;
    }
}