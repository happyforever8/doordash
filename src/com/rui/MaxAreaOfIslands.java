package com.rui;

public class MaxAreaOfIslands {

    // follow-up：如果数组很大，存不进内存如何处理??
    public int maxAreaOfIsland(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j] && grid[i][j] > 0) {
                    max = Math.max(max, helper (grid, i, j, visited));
                }
            }
        }
        return max;
    }

    int[] d = {0, 1, 0, -1, 0};

    private int helper (int[][] grid, int i, int j, boolean[][] visited) {
        int res = 1;
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + d[k];
            int y = j + d[k + 1];
            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == grid[i][j] && !visited[x][y]) {
               res += helper (grid, x, y, visited);
            }
        }
        return res;
    }
}
