//Bit Manipulation: Time Complexity - O(log n); Space Complexity - O(1)
class Solution {
    public int binaryGap(int n) {
        int lastIndex = -1;                                 // Last index of 1
        int currentIndex = 0;                               // Current index of 1
        int maxGap = 0;

        while(n > 0){                                       // Iterating from left to right 
            if((n & 1) == 1){                               // Check if current bit is 1
                if(lastIndex != -1){                        //If this is not first 1 (from left)
                    int gap = currentIndex - lastIndex;
                    maxGap = Math.max(maxGap, gap);
                }
                lastIndex = currentIndex;
            }

            n = n >> 1;                                     // Move to next bit
            currentIndex++;
        }
        
        return maxGap;
    }
}

/**
//Using Binary String: Time Complexity - O(log n); Space Complexity - O(log n)
class Solution {
    public int binaryGap(int n) {
        int count = 0, max = 0;
        String bs = Integer.toBinaryString(n);

        for(int i = 0; i < bs.length(); i++){
            
            if(bs.charAt(i) == '1'){
                max = Math.max(max, count);
                count = 1;
            } else {
                count++;
            }
        }
        
        return max;
    }
}
 */