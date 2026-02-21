class Solution {
    public int countPrimeSetBits(int left, int right) {

        //10^6 ~ 2^20. So, Maximum 1 that can be binary from of input can be 20.
        // Hence, we need to consider prime number upto 20.
        int primeMask = 0;
        primeMask |= (1 << 2);
        primeMask |= (1 << 3);
        primeMask |= (1 << 5);
        primeMask |= (1 << 7);
        primeMask |= (1 << 11);
        primeMask |= (1 << 13);
        primeMask |= (1 << 17);
        primeMask |= (1 << 19);

        int count = 0;
        for(int num = left; num <= right; num++){
            // Count number of set bits i.e. 1s
            int bitCount = Integer.bitCount(num);

            //Check if bitCount is prime using mask
            int check = primeMask & (1 << bitCount);

            //check != 0 → bitCount is prime
            if(check != 0)
                count++;
        }
        
        return count;
    }
}
/**
Initial:
primeMask = 0
Binary  = 0000 0000 0000 0000 0000 0000 0000 0000


1 << 2   → 0000 0000 0000 0000 0000 0000 0000 0100
primeMask |= (1 << 2)
primeMask → 0000 0000 0000 0000 0000 0000 0000 0100


1 << 3   → 0000 0000 0000 0000 0000 0000 0000 1000
primeMask |= (1 << 3)
primeMask → 0000 0000 0000 0000 0000 0000 0000 1100


1 << 5   → 0000 0000 0000 0000 0000 0000 0010 0000
primeMask |= (1 << 5)
primeMask → 0000 0000 0000 0000 0000 0000 0010 1100


1 << 7   → 0000 0000 0000 0000 0000 0000 1000 0000
primeMask |= (1 << 7)
primeMask → 0000 0000 0000 0000 0000 0000 1010 1100


1 << 11  → 0000 0000 0000 0000 0000 1000 0000 0000
primeMask |= (1 << 11)
primeMask → 0000 0000 0000 0000 0000 1000 1010 1100


1 << 13  → 0000 0000 0000 0000 0010 0000 0000 0000
primeMask |= (1 << 13)
primeMask → 0000 0000 0000 0000 0010 1000 1010 1100


1 << 17  → 0000 0000 0000 0010 0000 0000 0000 0000
primeMask |= (1 << 17)
primeMask → 0000 0000 0000 0010 0010 1000 1010 1100


1 << 19  → 0000 0000 0000 1000 0000 0000 0000 0000
primeMask |= (1 << 19)
primeMask → 0000 0000 0000 1010 0010 1000 1010 1100



----------------------------------------------
Checking: if ((primeMask & (1 << count)) != 0)
----------------------------------------------

Case 1: count = 5  (prime)

1 << 5
0000 0000 0000 0000 0000 0000 0010 0000

primeMask & (1 << 5)

0000 0000 0000 1010 0010 1000 1010 1100
&
0000 0000 0000 0000 0000 0000 0010 0000
------------------------------------------------
0000 0000 0000 0000 0000 0000 0010 0000

result != 0 → TRUE



Case 2: count = 6  (not prime)

1 << 6
0000 0000 0000 0000 0000 0000 0100 0000

primeMask & (1 << 6)

0000 0000 0000 1010 0010 1000 1010 1100
&
0000 0000 0000 0000 0000 0000 0100 0000
------------------------------------------------
0000 0000 0000 0000 0000 0000 0000 0000

result != 0 → FALSE
 */