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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 如果前序为空，说明树为空
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        // 前序遍历的第一个元素一定是根节点
        TreeNode root = new TreeNode(preorder[0]);

        // 栈里存“当前路径上还没处理完右子树的节点”
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);

        // inorderIndex 指向当前中序遍历中“应该完成处理”的位置
        int inorderIndex = 0;

        // 从 preorder[1] 开始，依次处理剩余节点
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];

            // 看栈顶节点，也就是最近创建、尚未确定右孩子的节点
            TreeNode node = stack.peek();

            // 情况 1：
            // 如果栈顶节点的值 != 当前 inorder[inorderIndex]
            // 说明我们还在不断往左走，
            // 当前 preorderVal 一定是它的左孩子
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);

            } else {
                // 情况 2：
                // 如果栈顶节点值 == inorder[inorderIndex]
                // 说明这个节点的左子树已经建完了，
                // 现在要一路回退，找第一个还没接右孩子的祖先
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }

                // 回退停止后，当前 preorderVal 就是这个 node 的右孩子
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }

        return root;
    }
}