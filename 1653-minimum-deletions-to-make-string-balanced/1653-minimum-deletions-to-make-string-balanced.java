/*
Example: s = "aababb"

Idx | char | bCount | delCount | Explanation
----+------+--------+----------+------------------------------
0   |  a   |   0    |    0     | balanced
1   |  a   |   0    |    0     | balanced
2   |  b   |   1    |    0     | count b
3   |  a   |   1    |    1     | min(delCount+1=1, bCount=1)
4   |  b   |   2    |    1     | count b
5   |  b   |   3    |    1     | count b

Final answer = 1
*/

class Solution {
    public int minimumDeletions(String s) {

        int bCount = 0;
        int delCount = 0;

        for(char c : s.toCharArray()){
            if(c == 'b'){
                bCount++;
            } else {
                delCount = Math.min(delCount + 1, bCount);
            }
        }

        return delCount;
        
    }
}