/**
Example Walkthrough

[11011000]
    → inside: 101100
        → splits into [10, 1100]
        → 1100 > 10 → reorder
        → 110010
    Wrap → 1 + 110010 + 0
    = 11100100
 */

class Solution {
    public String makeLargestSpecial(String s) {
        //Base case: length <= 2 cannot be improved
        if(s.length() <= 2) return s;

        List<String> parts = new ArrayList<>();
        int balance = 0;
        int start = 0;

        // Step 1: Split into primitive special substrings
        for(int i = 0; i < s.length(); i++){
            balance += (s.charAt(i) == '1') ? 1 : -1;

            // When balance becomes zero, we found one special block
            if(balance == 0){

                // Extract inner substring
                String inner = s.substring(start + 1, i);

                // Recursively maximize inner part
                String processedInner = makeLargestSpecial(inner);

                // Wrap again with outer 1 and 0
                parts.add("1" + processedInner + "0");

                start = i + 1;
            }
        }

        // Step 2: Sort in decending lexicographical order
        Collections.sort(parts, Collections.reverseOrder());

        // Step 3: Concatenate all parts
        StringBuilder result = new StringBuilder();
        for(String part : parts)
            result.append(part);

        return result.toString();
    }
}