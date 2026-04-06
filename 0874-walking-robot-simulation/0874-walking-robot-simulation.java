import java.util.HashSet;
import java.util.Set;

class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        Set<String> set = new HashSet<>();
        for (int[] obs : obstacles) {
            set.add(obs[0] + "," + obs[1]);
        }
        
        int x = 0, y = 0;
        int dir = 0; // 0=N,1=E,2=S,3=W
        int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
        
        int maxDist = 0;
        
        for (int cmd : commands) {
            if (cmd == -1) {
                dir = (dir + 1) % 4;
            } else if (cmd == -2) {
                dir = (dir + 3) % 4;
            } else {
                for (int i = 0; i < cmd; i++) {
                    int nx = x + directions[dir][0];
                    int ny = y + directions[dir][1];
                    
                    if (set.contains(nx + "," + ny)) break;
                    
                    x = nx;
                    y = ny;
                    maxDist = Math.max(maxDist, x * x + y * y);
                }
            }
        }
        
        return maxDist;
    }
}