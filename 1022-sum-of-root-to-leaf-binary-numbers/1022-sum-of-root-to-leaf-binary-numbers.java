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
    public int sumRootToLeaf(TreeNode root) {
        if(root == null) return 0;

        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int currentValue){
        if(node == null) return 0;

        // Update currentValue: left shift + add current bit
        currentValue = (currentValue << 1) | node.val;

        // If left node, return computed value
        if(node.left == null && node.right == null) return currentValue;

        int leftSum = dfs(node.left, currentValue);
        int rightSum = dfs(node.right, currentValue);

        // Returned combined sum
        return leftSum + rightSum;
    }
}