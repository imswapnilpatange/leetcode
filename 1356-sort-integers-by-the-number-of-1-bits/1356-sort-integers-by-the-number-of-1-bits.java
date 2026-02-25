import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int[] sortByBits(int[] arr) {
        if(arr == null || arr.length <= 1)
            return arr;                             // Edge Case

        Integer[] boxed = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++)
            boxed[i] = arr[i];                      // Boxing → Need for Comparator

        Arrays.sort(boxed, BIT_COMPARATOR);         // Given current contraints, Comparator option is optimal and clean

        for(int i = 0; i < boxed.length; i++)
            arr[i] = boxed[i];                      // Converting back to primitive

        return arr;
    }

    private static final Comparator<Integer> BIT_COMPARATOR = (a, b) -> {
        int countA = Integer.bitCount(a);
        int countB = Integer.bitCount(b);

        if(countA != countB)
            return countA - countB;                 // Primary criterion

        return a - b;                               // Secondary criterion
    };
}