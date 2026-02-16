class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // Check null input
        if(nums1 == null || nums2 == null) return new int[0];

        // Ensure we use smaller array for HashSet to save space
        if(nums1.length > nums2.length){
            int [] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        
        // Step 1: Store elements of smaller array in set
        Set<Integer> set1 = new HashSet<>();
        for(int num : nums1) set1.add(num);

        // Step 2: Find interaction with second array
        Set<Integer> resultSet = new HashSet<>();
        for(int num : nums2)
            if(set1.contains(num)) resultSet.add(num);

        // Step 3: Convert result set into a array
        int[] result = new int[resultSet.size()];
        int i = 0;
        for(int num : resultSet) result[i++] = num;

        return result;
    }
}