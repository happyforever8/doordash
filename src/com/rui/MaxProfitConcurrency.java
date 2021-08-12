package com.rui;

import java.util.*;

public class MaxProfitConcurrency {
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

//    public int jobScheduling(int[] startTime, int[] endTime, int[] profit, int k) {
//
//    }
}
