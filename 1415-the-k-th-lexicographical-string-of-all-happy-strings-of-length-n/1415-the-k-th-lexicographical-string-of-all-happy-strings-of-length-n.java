/*
    State Transition:
    Total happy strings of length n = 3 * 2^(n-1)
    First position → 3 choices
    Every next position → 2 choices (cannot repeat previous char)

    Branch Size:
    At position i, each valid next character contributes 2^(n-i-1) strings.

    Invariants:
    1. Adjacency: s[i] != s[i-1]
    2. Lexicographic order: a < b < c
    3. Block skipping: if k > blockSize → skip that branch
*/
class Solution {
    public String getHappyString(int n, int k) {
        int total = 3 * ( 1 << (n - 1));
        if(k > total) return "";

        StringBuilder res = new StringBuilder();
        char prev= '#';

        for(int pos = 0; pos <= n; pos++){
            int remaining = n - pos - 1;
            int block = 1 << remaining;

            for(char c: new char[]{'a', 'b', 'c'}){
                if(c == prev) continue;                 //invariant - adjacent chars must differ
                if( k > block) k -= block;              //skip entire block
                else{
                    res.append(c);
                    prev = c;
                    break;
                }
            }
        }
        
        return res.toString();
    }
}