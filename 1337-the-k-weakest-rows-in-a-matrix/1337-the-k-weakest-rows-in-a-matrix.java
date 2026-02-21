class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        int m = mat.length;
        int[][] soldiers = new int[m][2]; // Number of soldiers in each row

        for(int i = 0; i < m; i++){
            soldiers[i][0] = countSoilders(mat[i]);
            soldiers[i][1] = i;
        }

        // Sort by soilder count, then by index
        Arrays.sort(soldiers, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        // Collect first k indices
        int [] result = new int[k];
        for(int i = 0; i < k; i++)
            result[i] = soldiers[i][1];

        return result;
    }

    // Helper: count soldiers using binary search
    private int countSoilders(int[] row){
        int left = 0, right = row.length;
        while(left < right){
            int mid = left + (right - left) / 2;
            if(row[mid] == 1) left = mid + 1; // soldier found
            else right = mid;                 // civilian 
        } 

        return left;
    }
}