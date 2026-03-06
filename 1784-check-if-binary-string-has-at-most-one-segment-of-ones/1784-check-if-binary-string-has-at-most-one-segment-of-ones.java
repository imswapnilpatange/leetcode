class Solution {
    public boolean checkOnesSegment(String s) {
        boolean zeroAfterOne = false;

        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == '0' && s.charAt(i - 1) == '1')    //Detect Transision 1 → 0
                zeroAfterOne = true;

            if(zeroAfterOne && s.charAt(i) == '1')              //Later if we find 0 → 1,second segment completes
                return false;
        }

        return true;
    }
}