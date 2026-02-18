class Solution {
    public boolean hasAlternatingBits(int n) {
        String bs = Integer.toBinaryString(n);
        for(int i = 0; i < bs.length() - 1; i++){
            if(bs.charAt(i) == bs.charAt(i + 1))
                return false;
        }
        return true;
    }
}