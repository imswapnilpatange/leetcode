/**
    1. A, B length => 1
    2. ∀ a ∈ A, b ∈ B: a > 0 ^ b > 0
    3. In the exchange, let x is given by Alice and y is given by Bob → x ∈ A, y ∈ B
    4. Need to return [x, y] such that:
       sumA - + y = sumB - y + x
     → x - y = (sumA - sumB) / 2
     → x - y = delta
     → y = x - delta
 */
class Solution {
    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {

        //Step 1: Compute sums
        int sumA = 0, sumB = 0;

        for(int a: aliceSizes) sumA += a;
        for(int b: bobSizes) sumB += b;

        // Step 2: Compute delta
        int delta = (sumA - sumB) / 2;

        // Step 3: Store B elements in Set for O(1) lookup
        Set<Integer> setB = new HashSet<>();
        for(int b: bobSizes) setB.add(b);

        // Step 4: Find valid pair
        for(int x : aliceSizes){
            int y = x - delta;
            if(setB.contains(y))
                return new int[]{x, y};
        }
        
        return new int[0];
    }
}