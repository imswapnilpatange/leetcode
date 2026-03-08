    /*
    1. State Modeling
       - Input: nums[] with n unique binary strings, each of length n.
       - Total possible binary strings of length n = 2^n.
       - Given strings = n.
       - Since 2^n > n, at least one binary string is guaranteed to be missing.

    2. State Transition Formula (Diagonal Construction)
       - Construct result string res such that:
         res[i] = flip(nums[i][i])

       - Flip rule:
         '0' → '1'
         '1' → '0'

    3. Invariant
       - For every index i:
         res[i] ≠ nums[i][i]

       - Therefore the constructed string differs from nums[i] at position i.
       - Hence:
         res ≠ nums[i] for all i
       - So the generated string cannot exist in nums.
    */

class Solution {
    public String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        char [] res = new char[n];

        for(int i = 0; i < n; i++){                             //Construction diagonal complement
            if(nums[i].charAt(i) == '0') res[i] = '1';          // flip '0' → '1'
            else res[i] = '0';                                  // flip '1' → '0'
        }
        
        return new String(res);
    }
}