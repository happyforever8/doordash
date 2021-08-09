package com.rui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoTreeNodeDifference {

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
    class TreeNode { // 这个是定义好的
        int value;
        int key;
        List<TreeNode> children;
        public TreeNode (int key, int value) {
            this.key = key;
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    int findDifferentNode (TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return 0;
        } else if (root1 == null) {
            return countNode(root2);
        } else if (root2 == null) {
            return countNode(root1);
        } else if (root1.key != root2.key) {
            return countNode(root1) + countNode(root2);
        }

        int count = 0;
        if (root1.value != root2.value) {
            count += 2;
        }

        // 为了确保所有的key 和value 都能对得上。
        // 我觉得排序不好。原因是，[1,2,3,4], [2,3,4] 这样[2,3,4] 不能对上
        // 所以要一个一个key 的匹配。
        // 1。 假设每个key 都不一样
        //        Map<Integer, TreeNode> map = new HashMap<>();
        //        for (TreeNode n : root2.children) {
        //            map.put(n.key, n);
        //        }
        //
        //        for (TreeNode n : root1.children) {
        //            if (map.containsKey(n.key)) {
        //                count += findDifferentNode(n, map.get(n.key));
        //                map.remove(n.key);
        //            } else {
        //                count += countNode(n);
        //            }
        //        }
        //        for (TreeNode v : map.values()){
        //            count += countNode(v);
        //        }

        // 2. 如果key 有重复的
        Map<Integer, List<TreeNode>> map = new HashMap<>();
        for (TreeNode n : root2.children) {
            map.putIfAbsent(n.key, new ArrayList<>());
            map.get(n.key).add(n);
        }
        for (TreeNode n : root1.children) {
            if (map.containsKey(n.key)) {
                for (TreeNode t :  map.get(n.key)) {
                    count += findDifferentNode(n, t);
                }
                map.remove(n.key);
            } else {
                count += countNode(n);
            }
        }
        for (List<TreeNode> list : map.values()) {
            for (TreeNode t :  list) {
                count += countNode(t);
            }
        }


        return count;
    }

    int countNode(TreeNode root) {
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
