/*
EXAMPLE WALKTHROUGH

Input:
s = "0100"

Iteration Trace:

i=0 | actual=0 | exp0=0 exp1=1 | cost0=0 cost1=1
i=1 | actual=1 | exp0=1 exp1=0 | cost0=0 cost1=2
i=2 | actual=0 | exp0=0 exp1=1 | cost0=0 cost1=3
i=3 | actual=0 | exp0=1 exp1=0 | cost0=1 cost1=3

Result:
min(1,3) = 1
*/
class Solution {
    public int minOperations(String s) {
        int startCost0 = 0;                             //Pattern: 0101...
        int startCost1 = 0;                             //Pattern: 1010...

        for(int i = 0; i < s.length(); i++){
            int c = s.charAt(i);

            int expected0 = (i % 2 == 0) ? '0' : '1';
            int expected1 = (i % 2 == 0) ? '1' : '0';

            if(c != expected0) startCost0++;
            if(c != expected1) startCost1++;
        }
        
        return Math.min(startCost0, startCost1);
    }
}