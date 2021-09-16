package com.rui;

import java.util.*;

public class PickupAndDeliver {
    // 有几种不同的题，都放在这。


/* ************************************************** ******* *   *********括号匹配的 *******************************************/
    // 简述就是pickup 的deliver。 有点像括号匹配。
    // https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/
    // leetcode 这个题是计算数量。面经里面都是输出所有的组合。


//    Intuition 2
//    We consider the first element in all 2n elements.
//    The first must be a pickup, and we have n pickups as chioce.
//    Its pair can be any position in the rest of n*2-1 positions.
//    So it's (n * 2 - 1) * n.
//
//
//    Intuition 3
//    The total number of all permutation obviously eauqls to 2n!.
//    For each pair, the order is determined, so we need to divide by 2.
//    So the final result is (2n)!/(2^n)
// length of output。 f(n-1)*(‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌ (2n-1) + (2n-2) + ... + 1) = f(n-1)(n(2n-1)) = n!*(factorial of odd number) = n!((2n)!/n!2^n) = (2n)!/2^n
//                n=1: 1; n=2: 2!*3!; n=3: 3!*5!
    public int countOrders(int n) {
        long res = 1, mod = (long)1e9 + 7;
        for (int i = 1; i <= n; ++i)
            res = res * (i * 2 - 1) * i % mod;
        return (int)res;
    }

    //  followup是给定订单数目，打印所有合格的序列。
    // 这个是获得所有的combination
    // 2n! 时间复杂度。
    // 这个题做combination的时候唯一需要注意的是，一定是先pick，在deliver。这一块很类似parenthesis。但是区别是，每个pick 或者deliver，都不用别的pick 或者deliver 代替。。因此，每一个pick 对应一个deliver
    public List<List<String>> countOrdersCombination(int n) {
        List<List<String>> res = new ArrayList<>();
        boolean[] pick = new boolean[n];
        boolean[] deliver = new boolean[n];
        helper (n, pick, deliver, res, new LinkedList<>());
        return res;
    }

    void helper (int n, boolean[] pick, boolean[] deliver, List<List<String>> res, LinkedList<String> cur) {
        if (cur.size() == 2 * n) {
            res.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i <n; i++) {
            if (!pick[i]) {
                cur.add("P");
                pick[i] = true;
                helper(n, pick, deliver, res, cur);
                cur.removeLast();
                pick[i] = false;
            }
        }
        for (int i = 0; i < n; i++) {
            if (pick[i] && !deliver[i]) {
                cur.add("D");
                deliver[i] = true;
                helper(n, pick, deliver, res, cur);
                cur.removeLast();
                deliver[i] = false;
            }
        }

    }
/******************************************************************** 判断path 是不是valid的 ***********************************************************/

    /*
    * A driver's route can be represented as follows:
Given a set list of pickups and deliveries for order, figure out if the given list is valid or not.

A delivery cannot happen for an order before pickup.
The same order cannot be delivered or picked up twice
The car must be empty at the end of the drive.
Examples below:
[P1, P2, D1, D2]==>valid
[P1, D1, P2, D2]==>valid
[P1, D2, D1, P2]==>invalid
[P1, D2]==>invalid
[P1, P2]==>invalid
[P1, D1, D1]==>invalid
[]==>valid
[P1, P1, D1]==>invalid
[P1, P1, D1, D1]==>invalid
[P1, D1, P1]==>invalid
[P1, D1, P1, D1]==>invalid

Follow up: Find the longest valid subarray. O(n^2) is obvious. O(n) involves careful consideration of all the cases of invalidity.
    *
    * */

    public boolean isValidPickupDeliverSequence(String[] path) {
        if (path.length == 0) {
            return false;
        }
        Set<String> picked = new HashSet<>();
        Set<String> delivered = new HashSet<>();
        for (String p : path) {
            if (p.startsWith("P")) {
                if (delivered.contains(p)) {
                    return false;
                }
                picked.add(p);
            } else {
                String pick = "P" + p.substring(1);
                if (!picked.contains(pick)) {
                    return false;
                }
                picked.remove(pick);
                delivered.add(pick);
            }
        }
        return picked.isEmpty();
    }

//    public
}
