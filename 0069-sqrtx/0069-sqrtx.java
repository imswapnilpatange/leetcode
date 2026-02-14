class Solution {
    public int mySqrt(int x) {
        if(x == 0) return 0;

        int left = 1;
        int right = x;
        while (left <= right){
            int mid = left + (right - left)/2;

            if(mid == x/mid) 
                return mid; // exact root found
            else if(mid < x/mid)
             left = mid + 1; // shift right
            else
             right = mid - 1; // shift left
        } 

        // When left > right, right is floor(sqrt(x))
        return right;
        
    }
}