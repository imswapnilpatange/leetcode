class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;

        if(n1 > n2)
            //To obtain O(log(min(n1,n2))) instead of O(log(max(n1,n2))) 
            return findMedianSortedArrays(nums2, nums1);

        int n = n1 + n2;               //Total number of elements in combined array
        int totalLeft = (n + 1) / 2;  // 1 is added since array index starts from 0
        int low = 0;                  //First index of nums1
        int high = n1;

        while(low <= high){
            int mid1 = (low + high) >> 1; // Signed right shift operator instead of (low+high)/2 to handle negative values.
            int mid2 = totalLeft - mid1;

            //Initial values of l1,l2,r1 and r2
            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE, r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;

            if(mid1 < n1) r1 = nums1[mid1];
            if(mid2 < n2) r2 = nums2[mid2];

            //-1 because mid1/mid2 is number of elements in left partition(size).
            // At mid1/mid2=0 nums1[mid1-1] and nums2[mid2-1] will throw ArrayIndexOutOfBoundException
            if(mid1 - 1 >= 0) l1 = nums1[mid1 - 1];
            if(mid2 - 1 >= 0) l2 = nums2[mid2 - 1];

            if(l1 <= r2 && l2 <= r1){
                //Correct partirion is found.
                if(n % 2 == 1) return Math.max(l1,l2);

                return ((double) (Math.max(l1,l2) + Math.min(r1,r2)))/2.0;
            } else if(l1 > r2){
                //Left shift.
                high = mid1 - 1; //Minus 1 because mid1 is already tested and it cannot be the answer. Hence excluding it.
            } else {
                //Right shift.
                low = mid1 + 1; //Plus 1 because mid1 is already tested and it cannot be the answer. Hence excluding it.
            }
        }
        return 0; //Both input arrays are not sorted.
    }
}