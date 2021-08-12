package com.rui;

import java.util.*;

public class TwoTreeNodeDifference {
    // 近期考了好多次

    // followup 是问想要知道有多少点被改动了，
    // 这个方法是确定了两棵树加起来有多少个点不一样。

    // 题目：给定两个n叉树，求这两个树之间有多少个节点差异。
    // 树的node 是定义好的。
    // 时间复杂度就是树node 的个数。两棵树加起来
    /*
     * 问几个问题：
     * 1. Key 不一样算不算做相同的node
     * Key 是unique 的么
     * 2。Value 不一样算不算做相同的node
     * 3。children 数量不一样，算不算做相同的node
     * 4。 children 的顺序不一样，但是内容一样，算不算做相同的node
     * 5。 这个不一样的节点差异，是算两棵树的，还是算一棵树的？当前解法是算两棵树的。
     * */
    // 思路：
    // 同时dfs 两棵树，如果遇到不一样的节点，那么直接分别计算每棵树从当前节点开始的子树的node 数量。

    /*
    * 一些判定规则如下（面试时请与面试官clarify，不要上来就说是这样，至少要会演）：
        1. 如果node key一样，value一样，视为同一个节点。
        2. 如果node key一样，value不同，视为不同节点，但树的结构保持不变。
        3. 如果node key不同，则视为完全不同的两棵树，答案应该返回这两棵树里一共有多少节点。
        4. children数组里的顺序无关。
    *
    *
    * */
    static class TreeNode { // 这个是定义好的
        String value;
        String key;
        List<TreeNode> children;

        public TreeNode(String key, String value) {
            this.key = key;
            this.value = value;
            this.children = new ArrayList<>();
        }
    }
    // 这个方法是用来判断两个tree 之间总共有多少个node 不一样，而不是相对于某一个tree

    public int countDifferentNode(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return 0;
        } else if (root1 == null) {
            return countNode(root2);
        } else if (root2 == null) {
            return countNode(root1);
        } else if (!root1.key.equals(root2.key)) { // 如果key 或者value
            return countNode(root1) + countNode(root2);
        }

        int count = 0;
        if (root1.value != root2.value) {
            count += 1;
        }

        // 为了确保所有的key 和value 都能对得上。
        // 我觉得排序不好。原因是，[1,2,3,4], [2,3,4] 这样[2,3,4] 不能对上
        // 所以要一个一个key 的匹配。
        // 1。 假设每个key 都不一样
        Map<String, TreeNode> map = new HashMap<>();
        for (TreeNode n : root2.children) {
            map.put(n.key, n);
        }
        for (TreeNode n : root1.children) {
            if (map.containsKey(n.key)) {
                count += countDifferentNode(n, map.get(n.key));
                map.remove(n.key);
            } else {
                count += countNode(n);
            }
        }
        for (TreeNode v : map.values()) {
            count += countNode(v);
        }
        return count;
    }

    private int countNode(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        for (TreeNode n : root.children) {
            res += countNode(n);
        }
        return res + 1;
    }



}
