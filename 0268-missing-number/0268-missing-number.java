/*
XOR Core Table (0–9)
Unique pairs only (a < b)

With 1:        With 2:        With 3:        With 4:
1 ^ 2 = 3      2 ^ 3 = 1      3 ^ 4 = 7      4 ^ 5 = 1
1 ^ 3 = 2      2 ^ 4 = 6      3 ^ 5 = 6      4 ^ 6 = 2
1 ^ 4 = 5      2 ^ 5 = 7      3 ^ 6 = 5      4 ^ 7 = 3
1 ^ 5 = 4      2 ^ 6 = 4      3 ^ 7 = 4      4 ^ 8 = 12
1 ^ 6 = 7      2 ^ 7 = 5      3 ^ 8 = 11     4 ^ 9 = 13
1 ^ 7 = 6      2 ^ 8 = 10     3 ^ 9 = 10
1 ^ 8 = 9      2 ^ 9 = 11
1 ^ 9 = 8

With 5:        With 6:        With 7:        With 8:
5 ^ 6 = 3      6 ^ 7 = 1      7 ^ 8 = 15     8 ^ 9 = 1
5 ^ 7 = 2      6 ^ 8 = 14     7 ^ 9 = 14
5 ^ 8 = 13     6 ^ 9 = 15
5 ^ 9 = 12

Mental pattern:
- 0–7 follow earlier symmetry.
- 8 toggles the 8-bit (adds/removes 8).
- If a ^ b = c → then a ^ c = b and b ^ c = a.


=============================================================


Example:
nums = [4, 1, 3, 0, 6, 2, 7]   // n = 7
Missing = 5

Start:
missing = 7

i | nums[i] | Calculation                          | missing
--|---------|--------------------------------------|--------
- |   -     | Start                                | 7
0 |   4     | 7 ^ 0 ^ 4  = 7 ^ 4  = 3              | 3
1 |   1     | 3 ^ 1 ^ 1  = 3 ^ 0  = 3              | 3
2 |   3     | 3 ^ 2 ^ 3  = (3 ^ 3) ^ 2 = 0 ^ 2     | 2
3 |   0     | 2 ^ 3 ^ 0  = 2 ^ 3  = 1              | 1
4 |   6     | 1 ^ 4 ^ 6  = 5 ^ 6  = 3              | 3
5 |   2     | 3 ^ 5 ^ 2  = 6 ^ 2  = 4              | 4
6 |   7     | 4 ^ 6 ^ 7  = 2 ^ 7  = 5              | 5

Final missing = 5
*/

class Solution {
    public int missingNumber(int[] nums) {
        int missing = nums.length; // start with n

        for(int i = 0; i < nums.length; i++)
            missing ^= i ^ nums[i]; //xor operation

        return missing;
    }
}