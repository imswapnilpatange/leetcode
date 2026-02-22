/**
    Key Insight: XOR is assiciative and commutative
    a ^ a = 0
    a ^ 0 = a

    Example:
    [4, 1, 2, 1, 2]

    4 ^ 1 ^ 2 ^ 1 ^ 2
   =4 ^ (1 ^ 1) ^ (2 ^ 2) 
   =4 ^ 0 ^ 0
   =4
 */
class Solution {
    public int singleNumber(int[] nums) {
        //Defensive: LC gurantees at least 1 element
        if(nums == null || nums.length == 0) return -1;

        int result = 0;

        // XOR all elements
        for(int num : nums)
            result ^= num; // duplicates cancel out
        
        return result;
    }
}