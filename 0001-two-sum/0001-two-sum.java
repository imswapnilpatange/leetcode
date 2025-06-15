import java.util.Map;
import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> pairIndex = new HashMap<>();

        for(int i = 0; i <= nums.length; i++){
            if(pairIndex.containsKey(target - nums[i])){
                return new int [] {i, pairIndex.get(target - nums[i])};
            } else {
                pairIndex.put(nums[i], i);
            }
        }
        return new int [] {};
    }
}