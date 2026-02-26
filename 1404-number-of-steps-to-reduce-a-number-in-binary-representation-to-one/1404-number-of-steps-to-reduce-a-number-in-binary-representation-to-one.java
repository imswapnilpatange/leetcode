class Solution {
    public int numSteps(String s) {
        if("1".equals(0)) return 0;                 //Edge case: Already 1

        int steps = 0, carry = 0;
        for(int i = s.length() - 1; i > 0; i--){    //Excluding MSB
            int bit = s.charAt(i) - '0';            //'1' - '0' → 49 - 48 = 1 OR '0' - '0' → 48 - 48 = 0 
            int effectiveBit = bit + carry;
            if(effectiveBit == 0){
                steps += 1;                          //Even → only divide → only one step
            } 
            else if(effectiveBit == 1){
                steps += 2;                         //Odd  → add carry and then divide → two steps
                carry = 1;                          //Carry will be 1
            } else {                                //effectiveBit = bit + carry = 1 + 1
                steps += 1;                         //Even → only divide → only one step
                carry = 1;                          //Carry will still be 1
            }
        }

        if(carry == 1){                             //Carry is 1 when we reach MSB
            steps += 1;                             //MSB + carry → Even → only divide → only one step
        }
        
        return steps;
    }
}