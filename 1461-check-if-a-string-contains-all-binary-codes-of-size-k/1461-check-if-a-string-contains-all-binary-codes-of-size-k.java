class Solution {
    public boolean hasAllCodes(String s, int k) {
        if(s.length() < k) return false;                            // Edge Case
        
        int total = 1 << k;                                         // Total number of substrings → 2^k
        if((s.length() - k + 1) < total) return false;              // s.length() - k + 1 → substrings of length k in any s

        boolean[] seen = new boolean[total];

        int mask = 0;                                               // Rolling k-bit window
        int allOnes = total - 1;                                    // Bitmask to keep last k bits
        int count = 0;                                              // Unique patterns found

        for(int i = 0; i < s.length(); i++){
            mask = ((mask << 1) & allOnes | s.charAt(i) - '0');     // Shift left and add current bit

            if(i >= k - 1){                                         // Start when window size >= k
                if(!seen[mask]){
                    seen[mask] = true;
                    count++;

                    if(count == total) return true;                 // Early exit if all found
                }

            }

        }

        return false;
    }
}