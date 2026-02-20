class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        // Defensive: Leetcode guarantees valid input
        if(letters == null || letters.length == 0) return 0;

        int left = 0;
        int right = letters.length - 1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            // Move right if mid is not stricty greater
            if(letters[mid] <= target) left = mid + 1;
            
            // Potential answer found, try to find smaller index
            else right = mid - 1;
        }

        //Char greater than target doesn't exist
        if(left == letters.length) return letters[0];

        // Return computed upper bound
        return letters[left];
    }
}