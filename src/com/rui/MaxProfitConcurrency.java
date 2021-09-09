package com.rui;

import java.util.*;

public class MaxProfitConcurrency {
    //
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int[][] combination = new int[startTime.length][3];
        for (int i = 0; i < startTime.length; i++) {
            combination[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(combination, (a, b) -> a[1] - b[1]);
        Arrays.sort(endTime); // 从小到大
        int[] dp = new int[startTime.length + 1];
        for (int i = 1; i <= startTime.length; i++) {
            // 选择不加入这个profit 的最大的profit
            dp[i] = Math.max(dp[i], dp[i - 1]);
            // 选择加入profit
            int prevIndex = Arrays.binarySearch(endTime, 0, i - 1, combination[i - 1][0]);
            // 如果有个profit 的end 刚好是当前profit 的start
            if (prevIndex >= 0) {
                // prevIndex + 1 是因为dp 总长是length + 1 的缘故
                dp[i] = Math.max(dp[i], dp[prevIndex + 1] + combination[i - 1][2]);
            } else {
                // 这个-prevIndex - 1 原因是采用了 Arrays.binarySearch（）； 如果找不到， 返回 (-(insertion point) - 1)
                dp[i] = Math.max(dp[i], dp[-prevIndex - 1] + combination[i - 1][2]);
            }
        }
        return dp[combination.length];
    }

    // 这个有time 的range
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit, int s, int e) {
        int[][] tuples = new int[startTime.length][3];
        for (int i = 0; i < startTime.length; i++) {
            if (startTime[i] >= s && endTime[i] <= e) {
                tuples[i] = new int[]{startTime[i], endTime[i], profit[i]};
            }
        }
        Arrays.sort(tuples, (a, b) -> a[1] - b[1]);
        TreeMap<Integer, Integer> map = new TreeMap<>(); // 用了treemap 的好处就是当我想要寻找当前tuple 之前的所有的profit
        for (int[] t : tuples) {
            Integer prevProfit = map.floorEntry(t[0]) == null ? 0 : map.floorEntry(t[0]).getValue();
            Integer p = prevProfit + t[2];
            if (map.isEmpty() || p > map.lastEntry().getValue()) { // 如果比最大的值大，则改变最大值。这里要注意，是改变遍历到当前index 的全局的最大值。相当于是更新了[0, i] 这一段的最大值。
                map.put(t[1], p);
            }
        }
        return map.isEmpty() ? 0 : map.lastEntry().getValue();
    }


}
