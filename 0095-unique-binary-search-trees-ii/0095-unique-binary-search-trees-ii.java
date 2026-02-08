/**
==================== TREES CREATED FOR n = 3 ====================
Tree 1        Tree 2        Tree 3        Tree 4        Tree 5

1             1              2              3              3
 \             \            / \            /              /
  2             3          1   3          1              2
   \           /              \             \            /
    3         2                2             2          1

 */

/**
==================== RECURRSION TREE FOR n = 3 ====================
generate(1,3)
 ├─ root = 1   → creates 2 trees
 │   ├─ generate(1,0) → [null]
 │   └─ generate(2,3) → 2 trees
 │       ├─ root = 2  → creates 1 tree
 │       │   ├─ generate(2,1) → [null]
 │       │   └─ generate(3,3)
 │       │       └─ root = 3 → creates 1 tree
 │       │           ├─ generate(3,2) → [null]
 │       │           └─ generate(4,3) → [null]
 │       └─ root = 3  → creates 1 tree
 │           ├─ generate(2,2)
 │           │   └─ root = 2 → creates 1 tree
 │           │       ├─ generate(2,1) → [null]
 │           │       └─ generate(3,2) → [null]
 │           └─ generate(4,3) → [null]
 │
 ├─ root = 2   → creates 1 tree
 │   ├─ generate(1,1)
 │   │   └─ root = 1 → creates 1 tree
 │   │       ├─ generate(1,0) → [null]
 │   │       └─ generate(2,1) → [null]
 │   └─ generate(3,3)
 │       └─ root = 3 → creates 1 tree
 │           ├─ generate(3,2) → [null]
 │           └─ generate(4,3) → [null]
 │
 └─ root = 3   → creates 2 trees
     ├─ generate(1,2) → 2 trees
     │   ├─ root = 1  → creates 1 tree
     │   │   ├─ generate(1,0) → [null]
     │   │   └─ generate(2,2)
     │   │       └─ root = 2 → creates 1 tree
     │   │           ├─ generate(2,1) → [null]
     │   │           └─ generate(3,2) → [null]
     │   └─ root = 2  → creates 1 tree
     │       ├─ generate(1,1)
     │       │   └─ root = 1 → creates 1 tree
     │       │       ├─ generate(1,0) → [null]
     │       │       └─ generate(2,1) → [null]
     │       └─ generate(3,2) → [null]
     └─ generate(4,3) → [null]

Final at generate(1,3): 2 + 1 + 2 = 5 trees
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
    // Memo table: key = "start-end", value = list of BST roots
    private Map<String, List<TreeNode>> memo = new HashMap<>();

    public List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        return generate(1, n);
    }

    private List<TreeNode> generate(int start, int end) {
        String key = start + "-" + end;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        List<TreeNode> result = new ArrayList<>();
        if (start > end) {
            result.add(null);  // empty subtree
            memo.put(key, result);
            return result;
        }

        // Try each value as root
        for (int rootVal = start; rootVal <= end; rootVal++) {
            List<TreeNode> leftTrees = generate(start, rootVal - 1);
            List<TreeNode> rightTrees = generate(rootVal + 1, end);

            // Combine left and right subtrees with this root
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(rootVal);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }

        memo.put(key, result);
        return result;
    }
}
