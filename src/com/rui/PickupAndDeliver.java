package com.rui;

import java.util.*;

public class PickupAndDeliver {
    // 有几种不同的题，都放在这。


/* ************************************************** ******* *   *********括号匹配的 *******************************************/
    // 简述就是pickup 的deliver。 有点像括号匹配。
    // https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/
    // leetcode 这个题是计算数量。面经里面都是输出所有的组合。


    // 首先是计算数量
    // lee215 的解法
    /*
    *
      Stage 1
        We decide the order of all the pickups. It is trivial to tell there are n! possibilities
      Stage 2
        Given one possibility. Let's say the pickups are ordered like this A B C
        We can now insert the corresponding deliveries one by one.
        We start with the last pickup we made, namely, insert c, and there is only 1 valid slot.
        A B C c
        We continue with the second last pickup we made, namely, insert b, and there are 3 valid slots.
        A B x C x c x (where x denotes the location of valid slots for b)
        Let's only consider one case A B C c b. We continue with the third last pickup we made, namely, insert a, and there are 5 valid slots.
        A x B x C x c x b x, (where x denotes the location of valid slots for a)
        In conclusion. we have in total 1 * 3 * 5 * ... * (2n-1) possibilities
        Thus, the final solution is n! * (1 * 3 * 5 * ... * (2n-1)) % 1000000007
        *
Pickup possible value = n! (permutation)
Delivery possible value = 135*..2n-1
=> (1×2×3×4×…(2𝑛))/ (2×4×6×…×(2𝑛))
=> (2𝑛)! /(2^𝑛)𝑛!
*
*
* 先不要展开，当n >= 2时，
S(n) = (2n - 1) * n * S(n - 1)
= (2n - 1) * n * (2(n - 1) -1) * (n - 1) * S(n - 2)
整理一下，
S(n) = ((2n - 1) * (2n - 3) * ... * 3) * (n * (n - 1) * ... * 2) * S(1) (S(1) 也符合通项公式，所以可以直接带入化简）
= (2n - 1)!/((2^( n - 1)) * (n - 1)!) * n!
    * */
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
