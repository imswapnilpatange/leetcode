/*
Concrete Example:
a = "1010"
b = "1011"

| Step | i  | j  | a[i] | b[j] | Carry(in) | Sum | Bit(sum%2) | Carry(out) | Partial Result (reversed) |
|------|----|----|------|------|-----------|-----|------------|------------|----------------------------|
| 1    | 3  | 3  | 0    | 1    | 0         | 1   | 1          | 0          | 1                          |
| 2    | 2  | 2  | 1    | 1    | 0         | 2   | 0          | 1          | 10                         |
| 3    | 1  | 1  | 0    | 0    | 1         | 1   | 1          | 0          | 101                        |
| 4    | 0  | 0  | 1    | 1    | 0         | 2   | 0          | 1          | 1010                       |
| 5    | -1 | -1 | -    | -    | 1         | 1   | 1          | 0          | 10101                      |

Final Output (after reverse): "10101"
*/

class Solution {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        StringBuilder result = new StringBuilder();

        // Process both strings from right to left
        while(i >= 0 || j>= 0 || carry != 0){
            int sum = carry;

            if(i >= 0)
                sum += a.charAt(i--) - '0';

            if(j >= 0)
                sum += b.charAt(j--) - '0';

            result.append(sum % 2); // current bit
            carry = sum / 2;        // carry for next step
        }

         // Reverse because we appended from LSB to MSB
         return result.reverse().toString();
    }
}