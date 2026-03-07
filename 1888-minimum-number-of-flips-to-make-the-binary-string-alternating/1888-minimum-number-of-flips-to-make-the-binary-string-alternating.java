class Solution {
    public int minFlips(String s) {
        int n = s.length();
        String s2 = s + s;

        int diff1 = 0;                                                //mismatches vs "0101..."
        int diff2 = 0;                                                //mismatches vs "1010..."

        int min = Integer.MAX_VALUE;

        for(int i = 0; i < s2.length(); i++){
            int exp1 = (i % 2 == 0) ? '0' : '1';
            int exp2 = (i % 2 == 0) ? '1' : '0';

            if(s2.charAt(i) != exp1) diff1++;
            if(s2.charAt(i) != exp2) diff2++;

            if(i >= n){                                               //Maintain window size n
                char prev = s2.charAt(i - n);

                char prevExp1 = ((i - n) % 2 == 0) ? '0' : '1';
                char prevExp2 = ((i - n) % 2 == 0) ? '1' : '0';

                if(prev != prevExp1) diff1--;
                if(prev != prevExp2) diff2--;
            }

            if(i >= n - 1)
                min = Math.min(min, Math.min(diff1, diff2));
        }

        return min;
    }
}