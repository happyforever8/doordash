package com.rui;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        shorestPath();
    }

    private static void shorestPath() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(1, new ArrayList<>(Arrays.asList(1,2,1)));
        map.put(2, new ArrayList<>(Arrays.asList(2,3,1)));
        map.put(3, new ArrayList<>(Arrays.asList(3,5,1)));
        map.put(4, new ArrayList<>(Arrays.asList(1,4,1)));
        map.put(5, new ArrayList<>(Arrays.asList(4,5,2)));
        map.put(6, new ArrayList<>(Arrays.asList(3,4,2)));
        map.put(7, new ArrayList<>(Arrays.asList(2,4,4)));
        List<Integer> cities = new ArrayList<>(Arrays.asList(1,5));
        ShortestRoute shortestRoute = new ShortestRoute();
        System.out.println(Arrays.toString(shortestRoute.shortestPathRui(map, cities)));
        System.out.println(Arrays.toString(shortestRoute.shortestPath(map, cities)));
    }

    private static void maxareaofisland() {
        MaxAreaOfIslands maxAreaOfIslands = new MaxAreaOfIslands();
        int r = maxAreaOfIslands.maxAreaOfIsland(new int[][]{{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}});
        System.out.println(r);
    }

    private static void shortestPath() {
        ShortestRoute shortestRoute = new ShortestRoute();
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(0, Arrays.asList(1, 2, 1));
        map.put(1, Arrays.asList(2, 3, 1));
        map.put(2, Arrays.asList(3, 4, 1));
        map.put(3, Arrays.asList(4, 5, 1));
        map.put(4, Arrays.asList(5, 1, 3));
        map.put(5, Arrays.asList(1, 3, 2));
        map.put(6, Arrays.asList(5, 3, 1));

        List<Integer> cities = Arrays.asList(1, 2, 3, 4, 5);

        boolean[] res = shortestRoute.shortestPath(map, cities);

        for (boolean used : res) {
            System.out.println(used + ", ");
        }
    }

    private static void pickanddeliver() {
        PickupAndDeliver pickupAndDeliver = new PickupAndDeliver();
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "P2", "D1", "D2"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "P2", "D2"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "D1"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D2", "P2", "D1"}));
        System.out.println(pickupAndDeliver.isValidPickupDeliverSequence(new String[]{"P1", "D1", "P1", "D1"}));
    }

    private static void testTreeNode() {
        TwoTreeNodeDifference twoTreeNodeDifference = new TwoTreeNodeDifference();
        TwoTreeNodeDifference.TreeNode root = new TwoTreeNodeDifference.TreeNode("1", "1", true);
        TwoTreeNodeDifference.TreeNode level1 = new TwoTreeNodeDifference.TreeNode("2", "2", true);
        TwoTreeNodeDifference.TreeNode level11 = new TwoTreeNodeDifference.TreeNode("4", "4", true);
        TwoTreeNodeDifference.TreeNode level12 = new TwoTreeNodeDifference.TreeNode("5", "5", true);
//        TwoTreeNodeDifference.TreeNode level121 =  new  TwoTreeNodeDifference.TreeNode(55, 55, true);
//        level12.children.add(level121);
        level1.children.add(level11);
        level1.children.add(level12);
        TwoTreeNodeDifference.TreeNode level2 = new TwoTreeNodeDifference.TreeNode("3", "3", true);
        TwoTreeNodeDifference.TreeNode level22 = new TwoTreeNodeDifference.TreeNode("6", "6", true);
        level2.children.add(level22);
        root.children.add(level1);
        root.children.add(level2);
        TwoTreeNodeDifference.TreeNode root2 = new TwoTreeNodeDifference.TreeNode("1", "1", true);
        TwoTreeNodeDifference.TreeNode l2 = new TwoTreeNodeDifference.TreeNode("3", "3", false);
        TwoTreeNodeDifference.TreeNode l22 = new TwoTreeNodeDifference.TreeNode("66", "66", true);
//        TwoTreeNodeDifference.TreeNode l221 = new  TwoTreeNodeDifference.TreeNode(66, 66, true);
//        l22.children.add(l221);
        l2.children.add(l22);
        root2.children.add(l2);
//        System.out.println(twoTreeNodeDifference.countDifferentNode(root, root2));

        System.out.println(twoTreeNodeDifference.compareTheTreeNodes(root, root2));
    }
}
