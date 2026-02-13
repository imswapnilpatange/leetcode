class Solution {
    public int longestBalanced(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length;

        // Case 1: longest run of identical char
        int maxRun = 1, run = 1;
        for(int i = 1; i < n; i++){
            if (cs[i] == cs[i-1]) run++;
            else {
                maxRun = Math.max(maxRun, run);
                run = 1;
            }
        }
        maxRun = Math.max(maxRun, run);

        // Case 2: paris of distinct char ignoring the thrid
        maxRun = Math.max(maxRun, solvePair(cs, 'a', 'b', 'c'));
        maxRun = Math.max(maxRun, solvePair(cs, 'a', 'c', 'b'));
        maxRun = Math.max(maxRun, solvePair(cs, 'b', 'c', 'a'));

        // Case 3: all three distinct
        maxRun = Math.max(maxRun, solveThree(cs));
        
        return maxRun;
    }

    // Solve for two chars x,y ignoring z:we split at z boundaries
    private int solvePair(char[] cs, char x, char y, char z){
        int res = 0;
        int n = cs.length;
        int i = 0;
        while(i < n){
            // skip z segments
            while(i < n && cs[i] == z) i++;
            
            int start = i;
            while(i < n && cs[i] != z) i++;

            // process segment cs[start..i-1] for x,y
            res = Math.max(res, longestTwo(cs, start, i, x, y)); 
        }
        return res;
    }

    // In a substring[l, r), find longest where #x == #y
    private int longestTwo(char[] cs, int l, int r, char x, char y){
        // prefix diff -> first idx
        Map<Integer, Integer> firstIdx = new HashMap<>();
        firstIdx.put(0, l-1);
        int diff = 0;
        int best = 0;
        for(int i = l; i < r; i++){
            if(cs[i] == x) diff++;
            else if(cs[i] == y) diff--;
            if(!firstIdx.containsKey(diff)){
                firstIdx.put(diff, i);
            } else {
                best = Math.max(best, i - firstIdx.get(diff));
            }
        }
        return best;
    }

    // Solve for all three distinct a,b,c
    private int solveThree(char[] cs){
        int n = cs.length;
        //Map key: diff1 * 1000003 + diff2 (packed), map to first idx
        Map<Long, Integer> map = new HashMap<>();

        // prefix counts
        int a = 0, b = 0, c = 0;

        // initial diffs => index-1
        map.put(combine(0, 0), -1);
        int res = 0;
        for(int i = 0; i < n; i++){
            if(cs[i] == 'a') a++;
            else if(cs[i] == 'b') b++;
            else c++;

            int diffAB = a - b;
            int diffAC = a - c;
            long key = combine(diffAB, diffAC);
            if(!map.containsKey(key)){
                map.put(key, i);
            } else {
                res = Math.max(res, i - map.get(key));
            }
        }
        return res;
    }

    // Combine a pair of diffs into one key
    private long combine(int d1, int d2){
        // sufficiently large parime to avoid collisions in practice
        return ((long) d1) * 1000003L + d2;
    }
}