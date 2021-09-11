package com.rui;

import java.util.*;

public class TwoTreeNodeDifference {
    // 近期考了好多次

//    Node {
//   string key,
//   int value,
//   list<Node> children;
//}
//
//    curTree     a(4)
//     b(1)     c(5)
// d(3) e(5) f(8)    g(12)
//
//    newTree    a(4)
//     b(1)    m(5)
// e(5) d(3) w(8)   g(12)
//
//    find the difference.
//
//    解释： 一共5个diff .
//    c(5)变成了m(5‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌)所有的subTree 都认为变了，一共四个。 c(5), g(12), m(5), g(12)
//    f(8) 变成了 w(8).
//    虽然d(3) 和e(5)的顺序变了，但是不影响。

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

    // 目前这个解法对于test case 是对的

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

    public int countDifferentNode(TreeNode newTree, TreeNode oldTree) {
        if (newTree == null && oldTree == null) {
            return 0;
        } else if (newTree == null) {
            return countNode(oldTree);
        } else if (oldTree == null) {
            return countNode(newTree);
        } else if (!newTree.key.equals(oldTree.key)) { // 如果key 或者value
            return countNode(newTree) + countNode(oldTree);
        }

        // 目前这个解法是，如果value 不一样，那么只统计当前node。但是还要比较他们的child 节点
        int count = 0;
        if (newTree.value != oldTree.value) {
            count += 1;
        }

        // 为了确保所有的key 和value 都能对得上。
        // 我觉得排序不好。原因是，[1,2,3,4], [2,3,4] 这样[2,3,4] 不能对上
        // 所以要一个一个key 的匹配。
        Map<String, TreeNode> map = new HashMap<>();
        for (TreeNode n : newTree.children) {
            map.put(n.key, n);
        }
        for (TreeNode n : oldTree.children) {
            count += countDifferentNode(map.getOrDefault(n.key, null), n);
            map.remove(n.key);
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
