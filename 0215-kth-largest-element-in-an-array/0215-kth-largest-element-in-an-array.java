class Solution {
    // Returns k-th largest element using Min Heap
    public int findKthLargest(int[] nums, int k) {

        // minHeap to store the top k largest elements seen so far
        // The smallest element among these k will be at the root
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Step 1: Insert the first k elements into the heap
        for(int i = 0; i < k; i++)
            minHeap.offer(nums[i]);

        // Step 2: Process remaining elements
        for(int i = k; i < nums.length; i++){

            // If current element is larger than the smallest element in heap
            // it belongs to the top k largest elements
            if(nums[i] > minHeap.peek()){

                // Remove the smallest element
                minHeap.poll();

                // Add the current element to the heap
                minHeap.offer(nums[i]);
            }
        }
        
        // Step 3: The root of the minHeap is the k-th largest element
        return minHeap.peek();
    }
}

/*
 ********************** QuickSelect: O(n^2); Θ(n) => Throws TLE (Time Limit Exceeded) for one test case. **********************
public class Solution {
    public int findKthLargest(int[] nums, int k) {
        int left = 0;
		int right = nums.length - 1;
		int targetIndex = nums.length - k;
        Random rand = new Random();

        while (true) {
			// Pick a random pivot index within current range (To avoid Time Limit Exceeded) - rand.nextInt(nums.length)
            int randomPivot = left + rand.nextInt(right - left + 1);
			
            int finalPivotIndex = partition(nums, left, right, randomPivot);
            if (finalPivotIndex == targetIndex) {
				// Pivot is exactly k-th largest element
                return nums[finalPivotIndex];
            } else if (finalPivotIndex > targetIndex) {
				// Target lies on the right side
                right = finalPivotIndex - 1;
            } else {
				// Target lies on the left side
                left = finalPivotIndex + 1;
            }
        }
    }

    // Lumoto Partition Scheme
    private int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivot = nums[pivotIndex];
        swap(nums, pivotIndex, right);
        int storedIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, i, storedIndex);
                storedIndex++;
            }
        }
        swap(nums, right, storedIndex);
        return storedIndex;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
*/