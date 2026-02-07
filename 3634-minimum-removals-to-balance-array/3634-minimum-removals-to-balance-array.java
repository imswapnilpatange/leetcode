
    /*
     * Iteration Trace (nums = [1,2,3,4,6,8,9,12], k = 2)
     * Condition: while (A[L] * k < A[R])
     *
     * | R | A[R] | L0 | A[L0]*k < A[R]? | L1 | maxK |
     * | 0 |  1   | 0  | 1*2 < 1  → F    | 0  |  1   |
     * | 1 |  2   | 0  | 1*2 < 2  → F    | 0  |  2   |
     * | 2 |  3   | 0  | 1*2 < 3  → T    | 1  |  2   |
     * | 3 |  4   | 1  | 2*2 < 4  → F    | 1  |  3   |
     * | 4 |  6   | 1  | 2*2 < 6  → T    | 2  |  3   |
     * | 5 |  8   | 2  | 3*2 < 8  → T    | 3  |  3   |
     * | 6 |  9   | 3  | 4*2 < 9  → T    | 4  |  3   |
     * | 7 | 12   | 4  | 6*2 < 12 → F    | 4  |  4   |
     *
     * Legend:
     * R   = right index
     * L0  = left before while
     * L1  = left after while
     * maxK = maxKept
     *
     * Result: min removals = 8 - 4 = 4
     */

class Solution {
    public int minRemoval(int[] nums, int k) {
        if(nums.length < 2)
            return 0;

        Arrays.sort(nums);

        int n = nums.length;
        int left = 0;
        int maxKept = 0;

        for (int right = 0; right < n; right++) {
            while ((long) nums[left] * k < (long) nums[right])
                left++;

            maxKept = Math.max(maxKept, right - left + 1);
        }

        return n - maxKept;
    }
}