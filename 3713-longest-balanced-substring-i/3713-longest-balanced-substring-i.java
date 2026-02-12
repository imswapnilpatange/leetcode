class Solution {
    public int longestBalanced(String s) {
        int n = s.length();
        int ans = 0;

        for(int i = 0; i < n; i++){
            int[] freq = new int[26];

            for(int j = i; j < n; j++){
                char c = s.charAt(j);
                freq[ c - 'a']++;

                if(isBalanced(freq))
                    ans = Math.max(ans, j - i + 1);
            }
        }
        return ans;
    }

    private boolean isBalanced(int[] freq){
        int expected = 0;
        for(int f : freq){
            if(f > 0){
                if(expected ==0){
                    expected = f;
                } else if(f != expected){
                    return false;
                }
            }
        }
        return true;
    }
}