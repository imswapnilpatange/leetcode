// Optimum - O(n)
class Solution {
    public boolean checkIfExist(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        for(int x: arr){
            if(seen.contains(2 * x) || (x % 2 == 0 && seen.contains(x / 2)))
                return true;
            
            seen.add(x);
        }
        return false;
    }
}

// Brute Force - O(n^2)
/**
    class Solution {
        public boolean checkIfExist(int[] arr) {

            for(int i = 0; i < arr.length; i++){
                for(int j = i + 1; j < arr.length; j++){
                    if(arr[i] == 2 * arr[j] || arr[j] == 2 * arr[i])
                        return true;
                }
            }
            return false;
        }
    }
 */
