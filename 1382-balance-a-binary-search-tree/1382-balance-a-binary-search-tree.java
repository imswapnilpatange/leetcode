/**
Input BST:
1 → 2 → 3 → 4 → 5 → 6 → 7   (right-skewed)

------------------------------------------------
INORDER TRAVERSAL — CALL STACK TRACKING
------------------------------------------------

Call inorder(1)
 |
 |-- Call inorder(null) → return
 |-- Visit 1
 |
 |-- Call inorder(2)
      |
      |-- Call inorder(null) → return
      |-- Visit 2
      |
      |-- Call inorder(3)
           |
           |-- Call inorder(null) → return
           |-- Visit 3
           |
           |-- Call inorder(4)
                |
                |-- Call inorder(null) → return
                |-- Visit 4
                |
                |-- Call inorder(5)
                     |
                     |-- Call inorder(null) → return
                     |-- Visit 5
                     |
                     |-- Call inorder(6)
                          |
                          |-- Call inorder(null) → return
                          |-- Visit 6
                          |
                          |-- Call inorder(7)
                               |
                               |-- Call inorder(null) → return
                               |-- Visit 7
                               |-- Call inorder(null) → return

Result List: [1,2,3,4,5,6,7]

------------------------------------------------
BUILD BALANCED BST — CALL STACK TRACKING
------------------------------------------------

build(0,6)
 |
 |-- mid=3 → node=4
 |
 |-- build(0,2)
 |    |
 |    |-- mid=1 → node=2
 |    |
 |    |-- build(0,0)
 |    |    |
 |    |    |-- mid=0 → node=1
 |    |    |-- build(0,-1) → null
 |    |    |-- build(1,0) → null
 |    |
 |    |-- build(2,2)
 |         |
 |         |-- mid=2 → node=3
 |         |-- build(2,1) → null
 |         |-- build(3,2) → null
 |
 |-- build(4,6)
      |
      |-- mid=5 → node=6
      |
      |-- build(4,4)
      |    |
      |    |-- mid=4 → node=5
      |    |-- build(4,3) → null
      |    |-- build(5,4) → null
      |
      |-- build(6,6)
           |
           |-- mid=6 → node=7
           |-- build(6,5) → null
           |-- build(7,6) → null

------------------------------------------------
OUTPUT BST (BALANCED)
------------------------------------------------

        4
      /   \
     2     6
    / \   / \
   1   3 5   7

 */


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> sortedValues = new ArrayList<>();
        extractSortedValues(root, sortedValues);
        return buildBalanced(sortedValues, 0, sortedValues.size() - 1);
    }

    private void extractSortedValues(TreeNode node, List<Integer> sortedList){
        if(node == null) return;
        extractSortedValues(node.left, sortedList);
        sortedList.add(node.val);
        extractSortedValues(node.right, sortedList);
    }

    private TreeNode buildBalanced(List<Integer> sortedList, int left, int right){
        if(left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(sortedList.get(mid));
        root.left = buildBalanced(sortedList, left, mid - 1);
        root.right = buildBalanced(sortedList, mid + 1, right);
        return root;
    }
}