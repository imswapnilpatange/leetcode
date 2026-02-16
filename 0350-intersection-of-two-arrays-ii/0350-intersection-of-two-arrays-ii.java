class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        // Make sure to use smaller array for HashMap
        if(nums1.length > nums2.length) 
            return intersect(nums2, nums1);

        Map<Integer, Integer> freq = new HashMap<>();
        for(int num : nums1)
            freq.put(num, freq.getOrDefault(num, 0) + 1);

        // Check intersection with nums2
        List<Integer> resultList = new ArrayList<>();
        for(int num : nums2){
            if(freq.containsKey(num) && freq.get(num) > 0){
                resultList.add(num);
                freq.put(num, freq.get(num) - 1);
            }
        }

        // Convert list to array
        int [] result = new int[resultList.size()];
        for(int i = 0; i < resultList.size(); i++)
            result[i] = resultList.get(i);

        return result;
        
    }
}