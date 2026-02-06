/*
Example:

mat = [
  [1,3,11],
  [2,4,6],
  [1,5,10]
]
k = 5

Execution Table (rIt - rowIteration; st - step number; op - operation):

| rIt | st | op   | sums        | minHeap(s,i,j)                       | res         |
|-----|----|------|-------------|--------------------------------------|-------------|
| 0   | 0  | init | [1,3,11]    | —                                    | []          |
| 1   | 1  | hInit| [1,3,11]    | (3,0,0)(5,1,0)(13,2,0)               | []          |
| 1   | 2  | pop  | [1,3,11]    | (5,1,0)(13,2,0)                      | [3]         |
| 1   | 3  | push | [1,3,11]    | (5,1,0)(5,0,1)(13,2,0)               | [3]         |
| 1   | 4  | pop  | [1,3,11]    | (5,0,1)(13,2,0)                      | [3,5]       |
| 1   | 5  | push | [1,3,11]    | (5,0,1)(7,1,1)(13,2,0)               | [3,5]       |
| 1   | 6  | pop  | [1,3,11]    | (7,1,1)(13,2,0)                      | [3,5,5]     |
| 1   | 7  | push | [1,3,11]    | (7,1,1)(7,0,2)(13,2,0)               | [3,5,5]     |
| 1   | 8  | pop  | [1,3,11]    | (7,0,2)(13,2,0)                      | [3,5,5,7]   |
| 1   | 9  | push | [1,3,11]    | (7,0,2)(9,1,2)(13,2,0)               | [3,5,5,7]   |
| 1   |10  | popK | [1,3,11]    | (9,1,2)(13,2,0)                      | [3,5,5,7,7] |
| 2   |11  | nRow | [3,5,5,7,7] | —                                    | []          |
| 2   |12  | hInit| [3,5,5,7,7] | (4,0,0)(6,1,0)(6,2,0)(8,3,0)(8,4,0)  | []          |
| 2   |13  | pop  | [3,5,5,7,7] | (6,1,0)(6,2,0)(8,3,0)(8,4,0)         | [4]         |
| 2   |14  | push | [3,5,5,7,7] | (6,1,0)(6,2,0)(8,3,0)(8,4,0)(8,0,1)  | [4]         |
| 2   |15  | pop  | [3,5,5,7,7] | (6,2,0)(8,0,1)(8,3,0)(8,4,0)         | [4,6]       |
| 2   |16  | push | [3,5,5,7,7] | (6,2,0)(8,0,1)(8,3,0)(8,4,0)(10,1,1) | [4,6]       |
| 2   |17  | pop  | [3,5,5,7,7] | (8,0,1)(8,3,0)(8,4,0)(10,1,1)        | [4,6,6]     |
| 2   |18  | pop  | [3,5,5,7,7] | (8,3,0)(8,4,0)(10,1,1)               | [4,6,6,8]   |
| 2   |19  | popK | [3,5,5,7,7] | (8,4,0)(10,1,1)                      | [4,6,6,8,8] |

Final Answer = 8
*/
import java.util.PriorityQueue;
class Solution {
    public int kthSmallest(int[][] mat, int k) {
        int [] sums = mat[0];

        for(int rowNumber = 1; rowNumber < mat.length; rowNumber++){
            sums = mergeKSmallest(sums, mat[rowNumber], k);
        }
        
        return sums[k - 1];
    }

    private int[] mergeKSmallest(int[] sums, int[] row, int k){
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((x,y) -> x[0] - y[0]);

        for(int col = 0; col < Math.min(sums.length, k); col++)
            minHeap.offer(new int[] {sums[col] + row[0], col, 0});

        int[] res = new int[Math.min(k, sums.length * row.length)];
        int idx = 0;

        while(idx < k && !minHeap.isEmpty()){
            int[] cur = minHeap.poll();
            res[idx++] = cur[0];
            int i = cur[1];
            int j = cur[2];

            if(j + 1 < row.length)
                minHeap.offer(new int[]{sums[i] + row[j + 1], i, j + 1});
        }

        return res;
    }
}