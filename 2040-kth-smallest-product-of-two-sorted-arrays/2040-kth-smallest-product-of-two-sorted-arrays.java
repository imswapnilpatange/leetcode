class Solution {
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {

        long left = -10000000000L; // Minimum product (Second Constraint)
        long right = 10000000000L; // Maximum product (Second Constraint)

        //Core Logic: Instead of generating all products, we count pairs having product <= mid
        while(left < right){
            long mid = left + (right - left) / 2;

            long count = countPairs(nums1, nums2, mid);

            if(count >=k) right = mid; // if atleast k products are <= mid, then answer lies towards left
            else left = mid + 1; // otherwise answer lies towards right

        }
        
        return left; // left == right is the k-th smallest product
    }

    private long countPairs(int[] nums1, int[] nums2, long mid){
        long count = 0;

        // Three cases
        for(int a : nums1){

            if(a > 0){
                // Need a*b <= mid → b <= mid/a → b >= floor(mid / a)
                long target = floorDiv(mid, a);
                count += upperBound(nums2, target); 
            } else if(a < 0){
                //Need a*b <=mid → b <= mid/a. Since a is negative, we need b => ceil(mid/a)
                long target = ceilDiv(mid, a);
                count += nums2.length - lowerBound(nums2, target);
            } else{
                // Here a == 0
                // If mid >= 0 → all paris are valid
                // If mid < 0 → None are valid
                if(mid >=0)
                    count += nums2.length;
            }
        }
        return count;
    }

    private int upperBound(int[]arr, long target){
        int l = 0, r = arr.length;
        while (l < r){
            int m = l + (r - l) / 2;
            if(arr[m] <= target) l = m + 1;
            else r = m;
        }
        return l;
    }

    private int lowerBound(int[]arr, long target){
        int l = 0, r = arr.length;
        while (l < r){
            int m = l + (r - l) / 2;
            if(arr[m] < target) l = m + 1;
            else r = m;
        }
        return l;
    }

    private long floorDiv(long a, long b) {
        long res = a / b;
        if (a % b != 0 && ((a ^ b) < 0)) {
            res--;
        }
        return res;
    }

    private long ceilDiv(long a, long b){
        long result = a / b;

        // If remainder exists and signs are same, increment result to achieve ceiling behavior
        if(a % b != 0 && ((a ^ b) >= 0))
            result ++;

        return result;
    }
}


/** 
import java.math.BigInteger;

//Brute Force - throws TLE
class Solution {
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        PriorityQueue<Long> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for(int i = 0; i < nums1.length; i++){
            for(int j = 0; j < nums2.length; j++){
                maxHeap.offer(Math.multiplyExact((long) nums1[i], (long) nums2[j]));
            }       
        }

        while(maxHeap.size() > k)
            maxHeap.poll();

        return maxHeap.peek();
    }
}

*/
