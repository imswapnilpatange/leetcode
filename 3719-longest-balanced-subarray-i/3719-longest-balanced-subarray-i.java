class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int max = 0;
        
        for(int i = 0; i < n; i++){
            Set<Integer> oddCount = new HashSet<>();
            Set<Integer> evenCount = new HashSet<>();

            for(int j = i; j < n; j++){
                if(nums[j] % 2 == 1){
                    oddCount.add(nums[j]);
                } else {
                    evenCount.add(nums[j]);
                }

                if(oddCount.size() == evenCount.size())
                max = Math.max(max, j - i + 1);
            }
        }
        return max;       
    }
}