class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int longestBalancedSubarrayLength = 0;
        
        for(int i = 0; i < n; i++){
            Set<Integer> oddCount = new HashSet<>();
            Set<Integer> evenCount = new HashSet<>();

            // Optimization: If remainingElementsCount is less than maxLength, then we don't need to iterate those.
            if (longestBalancedSubarrayLength >= n - i) break;

            for(int j = i; j < n; j++){
                if(nums[j] % 2 == 1){
                    oddCount.add(nums[j]);
                } else {
                    evenCount.add(nums[j]);
                }

                if(oddCount.size() == evenCount.size())
                longestBalancedSubarrayLength = Math.max(longestBalancedSubarrayLength, j - i + 1);
            }
        }
        return longestBalancedSubarrayLength;       
    }
}