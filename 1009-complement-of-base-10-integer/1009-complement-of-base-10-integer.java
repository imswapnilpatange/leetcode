class Solution {
    public int bitwiseComplement(int n) {
        if(n == 0) return 1;                //Edge Case

        int bits = 0;
        int temp = n;
        while(temp > 0){                    //Step 1: Could number of bits
            bits++;
            temp >>= 1;
        }

        int mask = (1 << bits) - 1;          //Step 2: Build mask with all bits = 1

        return mask ^ n;                     //Step 3: flip bits within mask
    }
}