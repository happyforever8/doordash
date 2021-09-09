package com.rui;

import java.util.*;

public class BinaryTreeMaximumPathSum {
    // https://leetcode.com/problems/binary-tree-maximum-path-sum/
    // but variants
    //1.From leaf to leaf. 如果是root to leaf:https://leetcode.com/problems/path-sum/
    //2.Find the path too ?? 如果是root to leaf: https://leetcode.com/problems/path-sum-ii/
    //3.From active node to active node. 和上面一样的
    // 算是LC 124的轻微变种？问的是任意两个所谓的alive node之间的max path sum。一开始是说alive node就是leaf node，follow up是如果可以arbitrarily指定任意一个node是alive node。
    // https://www.1point3acres.com/bbs/thread-795289-1-1.html

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        boolean active;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, boolean active) {
            this.val = val;
            this.active = active;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    int maxLeafToLeaf = Integer.MIN_VALUE;

    Format maxSumLeafToLeafPath = new Format(new ArrayList<>(), Integer.MIN_VALUE);


    public void maxPathSum(TreeNode root) {
        if (root == null) {
            return;
        }
//        fromLeafToLeaf(root);
//        System.out.println(maxLeafToLeaf);
//        fromLeafToLeafPath(root);
        activeToActiveSum(root);
        System.out.println(maxActiveToActive);
    }

    private int fromLeafToLeaf(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        // 如果一定是leaf to leaf，那么这个必须是走到leaf 的
        int left = fromLeafToLeaf(root.left);
        int right = fromLeafToLeaf(root.right);
        maxLeafToLeaf = Math.max(maxLeafToLeaf, left + right + root.val);
        return Math.max(left, right) + root.val;
    }

    class Format {
        List<Integer> path;
        int sum;

        public Format(List<Integer> path, int sum) {
            this.path = path;
            this.sum = sum;
        }
    }

    private Format fromLeafToLeafPath(TreeNode root) {
        if (root == null) {
            return new Format(new ArrayList<>(), 0);
        }
        Format left = fromLeafToLeafPath(root.left);
        Format right = fromLeafToLeafPath(root.right);
        List<Integer> path = new ArrayList<>();
        path.addAll(left.path);
        path.addAll(right.path);
        path.add(root.val);
        Format cur = new Format(path, left.sum + right.sum + root.val);
        if (cur.sum > maxSumLeafToLeafPath.sum) {
            maxSumLeafToLeafPath = cur;
        }
        if (left.sum > right.sum) {
            left.path.add(root.val);
            return new Format(new ArrayList<>(left.path), left.sum + root.val);
        }
        right.path.add(root.val);
        return new Format(new ArrayList<>(right.path), right.sum + root.val);
    }

    int maxActiveToActive = Integer.MIN_VALUE;

    private Integer activeToActiveSum(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 这个题的要求是找到maximum path sum。所以如果active 的值为负数，那么就不要计算到sum 里面。
        // 每个active node 之间的node 都要记录到sum 里面去。
        // 如果当前node 是active， 看看有没有更深的。有更深的话，active 的sum path 也许会更长。
        // 我们需要计算的是从active 开始的path，也记忆是从active node 才开始返回sum
        // root 的value 也有可能是负数
        // 所以如果返回值是null， 则说明子树中没有active node
        Integer left = activeToActiveSum(root.left);
        Integer right = activeToActiveSum(root.right);
        if (!root.active && left == null && right == null) {
            return null;
        }
        int tempR = Math.max(0, right == null ? 0 : right);
        int tempL = Math.max(0, left == null ? 0 : left);

        if (left == null && right == null) {
            if (root.active) {
                maxActiveToActive = Math.max(maxActiveToActive, root.val);
                return root.val;
            }
            // 返回空表示当前path 没有遇到任何active node
            return null;
        }
        if (left != null || right != null) {
            // 单边的path
            if (left != null && right != null || root.active) {
                maxActiveToActive = Math.max(maxActiveToActive, tempL + tempR + root.val);
            }
        }
        return Math.max(tempR, tempL) + root.val;
    }


    private Format activeToActivePath(TreeNode root) {
        if (root == null) {
            return new Format(new ArrayList<>(), 0);
        }
        Format left = fromLeafToLeafPath(root.left);
        Format right = fromLeafToLeafPath(root.right);

        if (root.active) {
            List<Integer> path = new ArrayList<>();
            path.addAll(left.path);
            path.addAll(right.path);
            path.add(root.val);
            Format cur = new Format(path, left.sum + right.sum + root.val);
            if (cur.sum > maxSumLeafToLeafPath.sum) {
                maxSumLeafToLeafPath = cur;
            }
        }
        if (left.sum > right.sum) {
            left.path.add(root.val);
            return new Format(new ArrayList<>(left.path), left.sum + (root.active ? root.val : 0));
        }
        right.path.add(root.val);
        return new Format(new ArrayList<>(right.path), right.sum + (root.active ? root.val : 0));
    }


    class SolutionOrigin {

        int max = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            if (root == null) {
                return 0;
            }
            helper(root);
            return max;
        }

        int helper(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = Math.max(0, helper(root.left));
            int right = Math.max(0, helper(root.right));

            max = Math.max(max, left + right + root.val);
            return Math.max(left, right) + root.val;
        }
    }

}