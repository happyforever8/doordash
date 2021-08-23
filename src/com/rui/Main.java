package com.rui;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
//        testMenuComparation2();
        testPackageDependency();
    }

    private static void testPackageDependency() {
        PackageDependency packageDependency = new PackageDependency();
        Map<String, List<String>> dependencies = new HashMap<>();
//        dependencies.put("A", new ArrayList<>(Arrays.asList("B", "C")));
//        dependencies.put("B", new ArrayList<>(Arrays.asList("E")));
//        dependencies.put("C", new ArrayList<>(Arrays.asList("D", "E", "F")));
//        dependencies.put("D", new ArrayList<>());
//        dependencies.put("F", new ArrayList<>());
//        dependencies.put("G", new ArrayList<>(Arrays.asList("C", "F")));

        dependencies.put("A", new ArrayList<>(Arrays.asList("B")));
        dependencies.put("C", new ArrayList<>(Arrays.asList("A")));
        System.out.println(packageDependency.findDependienciesWithCircle(dependencies, "C"));
    }


    private static void testEmployeeFreeTimeWithRange () {
        EmployeeFreeTimeWithRange employeeFreeTimeWithRange = new EmployeeFreeTimeWithRange();
        List<EmployeeFreeTimeWithRange.Interval> l = new ArrayList<>();
        l.add(new EmployeeFreeTimeWithRange.Interval(1,3));
        l.add(new EmployeeFreeTimeWithRange.Interval(6,7));
        l.add(new EmployeeFreeTimeWithRange.Interval(2,4));
        l.add(new EmployeeFreeTimeWithRange.Interval(2,5));
        l.add(new EmployeeFreeTimeWithRange.Interval(9,12));
        List< List<EmployeeFreeTimeWithRange.Interval>> test = new ArrayList<>();
        test.add(l);
        System.out.println(employeeFreeTimeWithRange.employeeFreeTime(test, -1, 20));
    }

    private static void testStoresearch() {
        Storesearch storesearch = new Storesearch();
        System.out.println(Arrays.toString(storesearch.convertToToken(new String[]{"mon 00:01 am", "sun 11:59 pm"})));
    }

    private static void testMenuComparation2() {
        MenuComparation menuComparation = new MenuComparation();
        MenuComparation.TreeNode a = new MenuComparation.TreeNode("a", 1, true);
        MenuComparation.TreeNode b = new MenuComparation.TreeNode("b", 2, true);
        MenuComparation.TreeNode c = new MenuComparation.TreeNode("c", 3, true);
        MenuComparation.TreeNode d = new MenuComparation.TreeNode("d", 4, true);
        MenuComparation.TreeNode e = new MenuComparation.TreeNode("e", 5, true);
        MenuComparation.TreeNode g = new MenuComparation.TreeNode("g", 7, true);
        a.children.add(b);
        a.children.add(c);
        b.children.add(d);
        b.children.add(e);
        c.children.add(g);

        MenuComparation.TreeNode a1 = new MenuComparation.TreeNode("a", 1, true);
        MenuComparation.TreeNode b1 = new MenuComparation.TreeNode("b", 2, true);
        MenuComparation.TreeNode c1 = new MenuComparation.TreeNode("c", 3, true);
        MenuComparation.TreeNode d1 = new MenuComparation.TreeNode("d", 4, true);
        MenuComparation.TreeNode e1 = new MenuComparation.TreeNode("e", 5, true);
        MenuComparation.TreeNode f1 = new MenuComparation.TreeNode("f", 6, true);
        MenuComparation.TreeNode g1 = new MenuComparation.TreeNode("g", 7, false);
        a1.children.add(b1);
        a1.children.add(c1);
        b1.children.add(d1);
        b1.children.add(e1);
        b1.children.add(f1);
        c1.children.add(g1);

        System.out.println(menuComparation.findDifference(a, a1));


    }


//    private static void testMenu() {
//        AMenu.Node a = new AMenu.Node("a", 1, true);
//        AMenu.Node b = new AMenu.Node("b", 2, true);
//        AMenu.Node c = new AMenu.Node("c", 3, true);
//        AMenu.Node d = new AMenu.Node("d", 4, true);
//        AMenu.Node e = new AMenu.Node("e", 5, true);
//        AMenu.Node g = new AMenu.Node("g", 7, true);
//
//        a.children.add(b);
//        a.children.add(c);
//
//        b.children.add(d);
//        b.children.add(e);
//
//        //c.children.add(g);
//
//        AMenu.Node a1 = new AMenu.Node("a", 1, true);
//        AMenu.Node b1 = new AMenu.Node("b", 2, true);
//        AMenu.Node c1 = new AMenu.Node("c", 3, true);
//        AMenu.Node d1 = new AMenu.Node("d", 4, true);
//        AMenu.Node e1 = new AMenu.Node("e", 5, true);
//        AMenu.Node f1 = new AMenu.Node("f", 6, true);
//        AMenu.Node g1 = new AMenu.Node("g", 7, false);
//
//        a1.children.add(b1);
//        a1.children.add(c1);
//
//        b1.children.add(d1);
//        //b1.children.add(e1);
//        //b1.children.add(f1);
//
//        c1.children.add(e1);
//        AMenu aMenu = new AMenu();
//        int count = aMenu.getModifiedItems(a, a1);
//        System.out.println("Changed Items are: " + count);
//    }

    private static void testMenuComparation() {
        MenuComparation menuComparation = new MenuComparation();
        MenuComparation.TreeNode a = new MenuComparation.TreeNode("a", 1, true);
        MenuComparation.TreeNode b = new MenuComparation.TreeNode("b", 2, true);
        MenuComparation.TreeNode c = new MenuComparation.TreeNode("c", 3, true);
        MenuComparation.TreeNode d = new MenuComparation.TreeNode("d", 4, true);
        MenuComparation.TreeNode e = new MenuComparation.TreeNode("e", 5, true);
        MenuComparation.TreeNode f = new MenuComparation.TreeNode("f", 3, true);
        a.children.add(b);
        a.children.add(c);
        b.children.add(d);
        b.children.add(e);
        c.children.add(f);

        MenuComparation.TreeNode a1 = new MenuComparation.TreeNode("a", 1, true);
        MenuComparation.TreeNode c1 = new MenuComparation.TreeNode("c", 3, false);
        MenuComparation.TreeNode f1 = new MenuComparation.TreeNode("f", 66, true);
        a1.children.add(c1);
        c1.children.add(f1);

        System.out.println(menuComparation.findDifference(a, a1));


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
        TwoTreeNodeDifference.TreeNode root = new TwoTreeNodeDifference.TreeNode("1", "1");
        TwoTreeNodeDifference.TreeNode level1 = new TwoTreeNodeDifference.TreeNode("2", "2");
        TwoTreeNodeDifference.TreeNode level11 = new TwoTreeNodeDifference.TreeNode("4", "4");
        TwoTreeNodeDifference.TreeNode level12 = new TwoTreeNodeDifference.TreeNode("5", "5");
//        TwoTreeNodeDifference.TreeNode level121 =  new  TwoTreeNodeDifference.TreeNode(55, 55, true);
//        level12.children.add(level121);
        level1.children.add(level11);
        level1.children.add(level12);
        TwoTreeNodeDifference.TreeNode level2 = new TwoTreeNodeDifference.TreeNode("3", "3");
        TwoTreeNodeDifference.TreeNode level22 = new TwoTreeNodeDifference.TreeNode("6", "6");
        level2.children.add(level22);
        root.children.add(level1);
        root.children.add(level2);
        TwoTreeNodeDifference.TreeNode root2 = new TwoTreeNodeDifference.TreeNode("1", "1");
        TwoTreeNodeDifference.TreeNode l2 = new TwoTreeNodeDifference.TreeNode("3", "3");
        TwoTreeNodeDifference.TreeNode l22 = new TwoTreeNodeDifference.TreeNode("66", "66");
//        TwoTreeNodeDifference.TreeNode l221 = new  TwoTreeNodeDifference.TreeNode(66, 66, true);
//        l22.children.add(l221);
        l2.children.add(l22);
        root2.children.add(l2);
//        System.out.println(twoTreeNodeDifference.countDifferentNode(root, root2));

        System.out.println(twoTreeNodeDifference.countDifferentNode(root, root2));
    }
}
