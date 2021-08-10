package com.rui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoTreeNodeDifference {
    // 近期考了好多次

    // followup 是问想要知道有多少点被改动了，


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
    // 这个题还有一个变化是加了个active
    // 思路：
    // 同时dfs 两棵树，如果遇到不一样的节点，那么直接分别计算每棵树从当前节点开始的子树的node 数量。
    static class TreeNode { // 这个是定义好的
        String value;
        String key;
        List<TreeNode> children;
        boolean active;
        public TreeNode (String key, String value, boolean active) {
            this.key = key;
            this.value = value;
            this.children = new ArrayList<>();
            // 这个active 是后来加的
            this.active = active;
        }
    }
    // 这个方法是用来判断两个tree 之间总共有多少个node 不一样，而不是相对于某一个tree

    public int findDifferentNode (TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return 0;
        } else if (root1 == null) {
            return countNode(root2);
        } else if (root2 == null) {
            return countNode(root1);
        } else if (!root1.key.equals(root2.key) || root1.active != root2.active || !root1.value.equals(root2.value)) { // 如果key 或者value
            return countNode(root1) + countNode(root2);
        }

        int count = 0;
//        if (root1.value != root2.value) {
//            count += 2;
//        }

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
        Map<String, List<TreeNode>> map = new HashMap<>();
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
                // 这里要问一下面试官，只是统计当前node 还是整个子树都要算进去。如果只算当前节点。改成+1 就行了
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

    // 这个解法是相对于root1 的，有多少个不一样的node。这样时间复杂度就是遍历完所有的root1 的节点。
    public int compareTheTreeNodes (TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null ? 0 : 1;
        }
        int count = 0;
        if (root1.active != root2.active || root1.value != root2.value || root1.key != root2.key) {
            count++;
        }

        // 因为children 是没有顺序的。所以要用个map 处理一下。
        System.out.println(root1);
        System.out.println(root2);
        Map<String, TreeNode> map1 = extractTheChildren(root1);
        Map<String, TreeNode> map2 = extractTheChildren(root2);
        for (String key : map1.keySet()) {
            count += compareTheTreeNodes(map1.get(key), map2.getOrDefault(key, null));
        }
        for (String key : map2.keySet()) {
            if(!map1.containsKey(key)) {
                count += compareTheTreeNodes(null, map2.get(key));
            }
        }
        return count;
    }

    Map<Integer, TreeNode> extractTheChildren(TreeNode root) {
        Map<Integer, TreeNode> map = new HashMap<>(); // <key, List<TreeNode>>, 因为并不知道key 是不是唯一的。
        if (root == null) {
            return map;
        }
        for (TreeNode n : root.children) {
            map.put(n.key, n);
        }
        return map;
    }
}
