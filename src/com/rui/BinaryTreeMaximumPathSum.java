package com.rui;

import java.util.*;

public class BinaryTreeMaximumPathSum {
    // https://leetcode.com/problems/binary-tree-maximum-path-sum/
    // but variants
    //1.From leaf to leaf. 如果是root to leaf:https://leetcode.com/problems/path-sum/
    //2.Find the path too ?? 如果是root to leaf: https://leetcode.com/problems/path-sum-ii/
    //3.From active node to active node. 和上面一样的
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
    class Solution {
        int maxLeafToLeaf = Integer.MIN_VALUE;

        Format maxSumLeafToLeafPath = new Format(new ArrayList<>(), Integer.MIN_VALUE);

        int maxActiveToActive = Integer.MIN_VALUE;
        public void maxPathSum(TreeNode root) {
            if (root == null) {
                return;
            }
            fromLeafToLeaf(root);
            System.out.println(maxLeafToLeaf);
            fromLeafToLeafPath(root);
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
            public Format( List<Integer> path, int sum) {
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
                return new Format(new ArrayList<>(  left.path), left.sum+ root.val);
            }
            right.path.add(root.val);
            return new Format(new ArrayList<>(  right.path), right.sum+ root.val);
        }


        private int activeToActiveSum(TreeNode root) {
            if (root == null) {
                return 0;
            }
            // 如果当前node 是active， 看看有没有更深的；
            // 这个仅仅是所有active 的node 的和
            int left = activeToActiveSum(root.left);
            int right = activeToActiveSum(root.right);
            if (root.active) {
                maxActiveToActive = Math.max(maxActiveToActive, left + right + root.val);
            }
            return Math.max(left, right) + (root.active ? root.val : 0);
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
                return new Format(new ArrayList<>(left.path), left.sum+ (root.active ? root.val: 0));
            }
            right.path.add(root.val);
            return new Format(new ArrayList<>(  right.path), right.sum+ (root.active ? root.val: 0));
        }
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
