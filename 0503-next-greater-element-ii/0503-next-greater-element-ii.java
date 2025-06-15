import java.util.Arrays;

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int [] result = new int [nums.length];
        Arrays.fill(result, -1);

        for(int i = 0; i < nums.length; i++){
            int j = (i+1)%nums.length;

            while(i != j){
                if(nums[i] < nums[j]) {
                    result[i] = nums[j];
                    break;
                } else {
                    j = (j+1)%nums.length;
                }
            }
        }
        return result;
        
    }
}