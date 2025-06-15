class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int [] ans = new int [nums1.length];

        for(int i = 0; i<nums1.length; i++){
            int finalElement = -1;
            int j = nums2.length-1;

            while(j >= 0 && nums1[i] != nums2[j]){
                if(nums1[i] < nums2[j]){
                    finalElement = nums2[j];
                }
                j--;
            }

            ans[i] = finalElement;
        }
        return ans;
    }
}