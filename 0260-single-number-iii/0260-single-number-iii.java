class Solution {
    public int[] singleNumber(int[] nums) {

        int xor = 0;
        for(int num: nums) xor ^= num;   // Step 1: XOR all elements → duplicates cancel out.

        int diffBit = xor & -xor;       // Step 2: Isolate rightmost set bit       
        
        int a = 0, b = 0;
        for(int num: nums){              //Step 3: Partition elements using diffBit → Duplicates go in same group and cancel
           if((num & diffBit) == 0){
                a ^= num;               // If bit not set → Group A
           } else {
                b ^= num;               // If bit set → Group B
           }
        }

        return new int[]{a, b};
    }
}