package com.rui;

public class Main {

    public static void main(String[] args) {
	// write your code here
        maxareaofisland();
    }

    private static void maxareaofisland() {
        MaxAreaOfIslands maxAreaOfIslands = new MaxAreaOfIslands();
        int r = maxAreaOfIslands.maxAreaOfIsland(new int[][]{{0,0,1,0,0,0,0,1,0,0,0,0,0}, {0,0,0,0,0,0,0,1,1,1,0,0,0}, {0,1,1,0,1,0,0,0,0,0,0,0,0}, {0,1,0,0,1,1,0,0,1,0,1,0,0}, {0,1,0,0,1,1,0,0,1,1,1,0,0}, {0,0,0,0,0,0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,0,1,1,1,0,0,0}, {0,0,0,0,0,0,0,1,1,0,0,0,0}});
        System.out.println(r);
     }

    private static void pickanddeliver() {
        PickupAndDeliver pickupAndDeliver = new PickupAndDeliver();
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "P2", "D1", "D2"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "P2", "D2"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "D1"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D2", "P2", "D1"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "P1", "D1"}));
    }

    private static void testTreeNode () {
        TwoTreeNodeDifference twoTreeNodeDifference = new TwoTreeNodeDifference();
        TwoTreeNodeDifference.TreeNode root = new  TwoTreeNodeDifference.TreeNode(1, 1, true);
        TwoTreeNodeDifference.TreeNode level1 =  new  TwoTreeNodeDifference.TreeNode(2, 2, true);
        TwoTreeNodeDifference.TreeNode level11 =  new  TwoTreeNodeDifference.TreeNode(4, 4, true);
        TwoTreeNodeDifference.TreeNode level12 =  new  TwoTreeNodeDifference.TreeNode(5, 5, true);
//        TwoTreeNodeDifference.TreeNode level121 =  new  TwoTreeNodeDifference.TreeNode(55, 55, true);
//        level12.children.add(level121);
        level1.children.add(level11);
        level1.children.add(level12);
        TwoTreeNodeDifference.TreeNode level2 =  new  TwoTreeNodeDifference.TreeNode(3, 3, true);
        TwoTreeNodeDifference.TreeNode level22 =  new  TwoTreeNodeDifference.TreeNode(6, 6, true);
        level2.children.add(level22);
        root.children.add(level1);
        root.children.add(level2);
        TwoTreeNodeDifference.TreeNode root2 = new  TwoTreeNodeDifference.TreeNode(1, 1, true);
        TwoTreeNodeDifference.TreeNode l2 =  new  TwoTreeNodeDifference.TreeNode(3, 3, false);
        TwoTreeNodeDifference.TreeNode l22 = new  TwoTreeNodeDifference.TreeNode(66, 66, true);
//        TwoTreeNodeDifference.TreeNode l221 = new  TwoTreeNodeDifference.TreeNode(66, 66, true);
//        l22.children.add(l221);
        l2.children.add(l22);
        root2.children.add(l2);
//        System.out.println(twoTreeNodeDifference.findDifferentNode(root, root2));

        System.out.println(twoTreeNodeDifference.compareTheTreeNodes(root, root2));
    }
}
