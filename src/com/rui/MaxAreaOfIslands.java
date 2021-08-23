package com.rui;

public class MaxAreaOfIslands {

    // follow-up：如果数组很大，存不进内存如何处理??
    // Follow-up是问如果矩阵很大没法存到memory怎么办，可以存硬盘上然后maintain一个list of inputStream，
    //  每个row从硬盘里读。接着问DFS还能不能行？答说不得行因为会有Stack Overflow，应该用BFS，如果queue很大也可‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌以overflow到硬盘上。上周四面的，这周三通知pass。
    public int maxAreaOfIsland(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 如果0 不算是岛屿的话
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
