class Solution {
    public boolean isPerfectSquare(int num) {
        if(num == 1) return true; // Edge Case

        long low = 1, high = num;
        while(low <= high){
            long mid = low + (high - low) / 2;
            long sq = mid * mid;

            // Check if it is a perfect square
            if(sq == num) return true;
            else if (sq < num) low = mid + 1; // Search higher
            else high = mid - 1; // Search lower
        }
        return false; // Not a perfect square
    }
}