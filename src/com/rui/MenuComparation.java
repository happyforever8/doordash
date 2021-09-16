package com.rui;

import java.util.*;

public class MenuComparation {
    /*
    https://www.1point3acres.com/bbs/thread-798058-1-1.html


   At DoorDash, menus are updated daily even hourly to keep them up-to-date.
  Each menu can be regarded as a tree. When the merchant sends us the latest menu,
  can we calculate how many nodes has changed?

  Assume each Node structure is given
  Assume there are no duplicate nodes with the same key.
  Output: Return the number of changed nodes in the tree.

  Either value change or the active status change means the node has been changed.
  The new menu tree structure can be different from existing trees when some nodes are set to null.

  Example1:

  Existing tree
     a(1, T)
    /   \
   b(2, T) c(3, T)
    / \   \
  d(4, T) e(5, T) f(6, T)

  a(1, T): a is the key, 1 is the value, T is True for active status

  New Menu sent‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌ by the Merchant:

    a(1, T)
      \
     c(3, F)
       \
       f(66, T)

   Expected Answer: 5
   Explanation: Node b, d, e are automatically set to inactive.
      The active status of Node c and
      the value of Node f changed as well.

   Example 2
   Existing tree
    a(1, T)
     /   \
    b(2, T) c(3, T)
  /   \   \
  d(4, T) e(5, T)  g(7, T)

   New Menu sent by the Merchant:

      a(1, T)
      /   \
    b(2, T)   c(3, T)
   /  |  \   \
  d(4, T) e(5, T) f(6, T)  g(7, F)

  Expected Answer: 2
  Explanation: Node f is a newly-added node.
       Node g changed from Active to inactive
 */

    // 这个题的解法跟compareTwoTree 不一样的是，这个是按照tree 的结构进行比较的。
    // 如果两个node 的key或者active，或者value 不一样，只会统计一个节点不一样，而不是把他们的子树都算进去。
    // 面试的时候要问清楚。

    static class TreeNode {
        String key;
        int value;
        boolean active;
        List<TreeNode> children;
        public TreeNode (String key, int value, boolean active) {
            this.key = key;
            this.value = value;
            this.active = active;
            children = new ArrayList<>();
        }
    }

    public int findDifference(TreeNode oldMenu, TreeNode newMenu) {
        if(oldMenu == null && newMenu == null) {
            return 0;
        }
        int count = 0;
        if(oldMenu == null || newMenu == null || !oldMenu.key.equals(newMenu.key) || oldMenu.active != newMenu.active || oldMenu.value != newMenu.value) {
            count++;
        }

        Map<String, TreeNode> children1 = getChildNodes(oldMenu);
        Map<String, TreeNode> children2 = getChildNodes(newMenu);

        for(String key : children1.keySet()) {
            count += findDifference(children1.get(key), children2.getOrDefault(key, null));
        }

        for(String key : children2.keySet()) {
            if(!children1.containsKey(key)) {
                count += findDifference(null, children2.get(key));
            }
        }

        return count;
    }

    private static Map<String, TreeNode> getChildNodes(TreeNode menu) {
        Map<String, TreeNode> map = new HashMap<>();
        if(menu == null) {
            return map;
        }

        for(TreeNode node : menu.children) {
            map.put(node.key, node);
        }

        return map;
    }
}
