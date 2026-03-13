/*
    FORMAL MODELING
    ---------------
    Worker i removes height in increasing time cost: t_i, 2t_i, 3t_i, ... , k*t_i
    Total time for worker i to remove k units: T = t_i * (1 + 2 + ... + k) = t_i * k(k + 1) / 2
    
    For a given total time T: t_i * k(k+1)/2 ≤ T
    Rearrange: k(k+1) ≤ 2T / t_i → k² + k − (2T/t_i) ≤ 0

    Largest valid integer k: k = floor((√(1 + 8T/t_i) − 1) / 2)
    Total removed height across workers: totalRemoved(T) = Σ k_i
    
    We need: totalRemoved(T) ≥ mountainHeight

    INVARIANTS
    ----------
    1. Monotonicity: If time T is sufficient, any T' > T is also sufficient.
    2. Reachability: Total height removed is sum of independent worker contributions.
    3. Search Space: Time range = [0, very large upper bound].

    ALGORITHM
    ---------
    Binary Search on Time.

    1. Set search range: left = 0; right = large upper bound (1e18 safe)
    2. Compute total removed height using formula for each worker.
        While left ≤ right:
        mid = (left + right) / 2
    3. If totalRemoved ≥ mountainHeight:
        record answer
        search left half
    4. Else:
        search right half
 
    Complexity:
        Time  = O(N log T)
        Space = O(1)
*/

class Solution {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        long left = 0;
        long right = (long) 1e18; //large upper bound (1e18 safe)
        long ans = right;

        while(left <= right){
            long mid = left + (right - left) / 2;
            if(can(mid, mountainHeight, workerTimes)){
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean can(long time, int mountainHeight, int[] workerTimes){

        long removed = 0;

        for(int t: workerTimes){
            double val = 1 + (8.0 * time / t);
            long k = (long) ((Math.sqrt(val) - 1) / 2);
            removed += k;

             if (removed >= mountainHeight)
                return true;
        }
        return false;
    }
}