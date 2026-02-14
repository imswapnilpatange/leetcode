/*
Iteration 0 (initial)
        [ poured ]

Iteration 1
        [  1  ]
      [ x/2  x/2 ]

Iteration 2
        [  1  ]
      [  1  1 ]
    [ y/2  z  y/2 ]

Iteration 3
        [  1  ]
      [  1  1 ]
    [  1  1  1 ]
  [ a/2  b  c  b  a/2 ]

(Where x, y, a = overflow from parent glass: (amount - 1) / 2)
*/
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {

        // rowAmount[i] = champagne amount at index i of current row
        double [] rowAmounts = new double[query_row + 1];

        // Initialize top glass
        rowAmounts[0] = poured;

        // Build row by row until target row
        for(int row = 1; row <= query_row; row++){

            // Traverse right → left to safely reuse same array
            for(int i = row - 1; i >= 0; i--){

                double overflow = 0.0;
                if(rowAmounts[i] > 1.0)
                    overflow = (rowAmounts[i] - 1.0)/2.0;

                // Clear current cell for next row
                rowAmounts[i] = 0.0;

                // Distribute overflow equally to next row positions
                rowAmounts[i]   += overflow;  //left child
                rowAmounts[i+1] +=overflow; //right child


            }
        }
        
        // Return final glass amount capped at 1.0
        return Math.min(1.0, rowAmounts[query_glass]);
    }
}