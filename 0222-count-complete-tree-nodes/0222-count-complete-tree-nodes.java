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
    public int countNodes(TreeNode root) {
        if(root == null)
            return 0;
        
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        // If heights equal → left subtree is perfect.
        // Nodes = 2^h (left subtree + root) + recurse right.
        if(leftHeight == rightHeight)
            return (1 << leftHeight) + countNodes(root.right);
        else
            // If heights differ → right subtree is perfect.
            // Nodes = 2^h (right subtree + root) + recurse left.
            return (1 << rightHeight) + countNodes(root.left); 
    }

    // Count the height by going all the way left
    private int height(TreeNode node){
        int h = 0;
        while(node != null){
            h++;
            node = node.left;
        }
        return h;
    }
}