class Solution {
    public int countBinarySubstrings(String s) {
        // Edge Case
        if(s == null || s.length() < 2) return 0;

        int prevGroupSize = 0;
        int currGroupSize = 1;
        int result = 0;

        for(int i = 1; i < s.length(); i++){

            // same character → expand current group
            if(s.charAt(i) == s.charAt(i - 1)) currGroupSize++;
            else { // new group boundary
                result += Math.min(prevGroupSize, currGroupSize);
                prevGroupSize = currGroupSize;
                currGroupSize = 1;
            }
        }

        // Add final boundary contribution
        result += Math.min(prevGroupSize, currGroupSize);
        
        return result;
    }
}