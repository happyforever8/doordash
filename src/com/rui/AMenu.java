package com.rui;

import java.util.*;

public class AMenu {
    public static class Node {
        String key;
        int value;
        boolean isActive;
        List<Node> children;

        public Node(String key, int value, boolean isActive) {
            this.key = key;
            this.value = value;
            this.isActive = isActive;
            this.children = new ArrayList<>();
        }

        public boolean equals(Node node) {
            if (node == null) {
                return false;
            }

            return this.key == node.key
                    && this.value == node.value
                    && this.isActive == node.isActive;
        }

        public String toString() {
            return this.key;
        }
    }

    public static int getModifiedItems(Node oldMenu, Node newMenu) {
        if (oldMenu == null && newMenu == null) {
            return 0;
        }
        int count = 0;
        if (oldMenu == null || newMenu == null || !oldMenu.equals(newMenu)) {
            System.out.println(oldMenu + " " + newMenu);
            count++;
        }

        Map<String, Node> children1 = getChildNodes(oldMenu);
        Map<String, Node> children2 = getChildNodes(newMenu);

        for (String key : children1.keySet()) {
            count += getModifiedItems(children1.get(key), children2.getOrDefault(key, null));
        }

        for (String key : children2.keySet()) {
            if (!children1.containsKey(key)) {
                count += getModifiedItems(null, children2.get(key));
            }
        }

        return count;
    }

    private static Map<String, Node> getChildNodes(Node menu) {
        Map<String, Node> map = new HashMap<>();
        if (menu == null) {
            return map;
        }

        for (Node node : menu.children) {
            map.put(node.key, node);
        }

        return map;
    }
}

