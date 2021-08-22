package com.rui;

import java.util.*;

public class AccountMerge {
    //https://leetcode.com/problems/accounts-merge/

        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            // 这个题用union find。
            //
            Map<String, String> parent = new HashMap<>(); // like the parent[], parent[i] 在初始的时候指向他自己。
            Map<String, String> owner = new HashMap<>(); // 记录parent 和对应的account name 的关系

            // 初始化union find。对每个account 初始化，指向他自己
            for (List<String> account : accounts) {
                for (int i = 1; i < account.size(); i++) {
                    parent.put(account.get(i), account.get(i));
                    owner.put(account.get(i), account.get(0));
                }
            }

            // union 同一个account 下面的accounts 都给unit 到第一个元素上面去。
            for (List<String> account : accounts) {
                String first = find(account.get(1), parent);
                for (int i = 2; i < account.size(); i++) {
                    String second = find(account.get(i), parent);
                    parent.put(second, first);
                }
            }

            // 把已经union 的email 都集合起来。
            // 这里要注意，可能同一个名字的两个emails set 没有交集，所以无法把他们统一起来。
            Map<String, TreeSet<String>> map = new HashMap<>();
            for (List<String> account : accounts) {
                String first = find(account.get(1), parent);
                map.putIfAbsent(first, new TreeSet<>());
                for (int i = 1; i < account.size(); i++) {
                    map.get(first).add(account.get(i));
                }
            }
            List<List<String>> res = new ArrayList<>();
            for (String key : map.keySet()) {
                List<String> t = new ArrayList<>();
                t.add(owner.get(key));
                t.addAll(map.get(key));
                res.add(t);
            }
            return res;
        }

        String find (String account, Map<String, String> map) {
            return account != map.get(account) ? find(map.get(account), map) : account;
        }

}
