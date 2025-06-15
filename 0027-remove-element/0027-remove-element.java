import java.util.List;
import java.util.ArrayList;

class Solution {
    public int removeElement(int[] nums, int val) {
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < nums.length; i++){
            if(nums[i] != val)
                result.add(nums[i]);
        }

        for (int i = 0; i < result.size(); i++) {
            nums[i] = result.get(i);
        }

        return result.size();
        
    }
}