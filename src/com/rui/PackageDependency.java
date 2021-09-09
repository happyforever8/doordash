package com.rui;
import java.util.*;

public class PackageDependency {
    /*
    *
    这个是找到的amazon的类似的日
    a) You have a package repository in which there are dependencies between packages for building like package A has to be built before package B. If you are given dependencies between the packages and package name x, we have find the build order for x.
        Ex: A → {B,C}
        B → {E}
        C → {D,E,F}
        D → {}
        F → {}
        G → {C}

        For package A, build order is E B F D C A (may not unique)

        Given a function Set getDependencies (Package packageName) which returns a set of dependencies for a given package name, write a method List getBuildOrder(Package packageName) which returns the build order

     b) How would you handle cyclic dependencies (Algo only)

     * 大神解法：拓扑排序，package dependency 问题， 给定一个package x，需要以怎样的顺序安装 x 需要的dependency libs，需要注意的是，这个问题需要先正向 dfs/bfs 找到dependecy graph 中的 connected component，再reverse graph 用 topological sort 找顺序，这里要注意有环的情况要单独判断
    * */
    // 需要问的问题
    // dependecy 里面有没有相互依赖。
    // 应该是没有自己指向自己的边。
    // 这个题看来不是连通图，可能有环。
    // 然后找到的路径也有可能不止一条。
    // 如何只 print circular dependency.

    // 每个包会给出一些prerequisite。 最终是要求某一个包的安装序列 所以不是所有出现的包都会用到。基本是topological sorting，地里有人说用单纯bfs也行 不过我没写出来。
    // 解法就是，首先找到这个dependency 所在的连通区域，然后从dependency 出发，按照dependency 的顺序，找到所有的节点。再从把图翻过来。容这些节点出发，直到走到这个package


    // 这个题的follow up 就是有没有环， 有环用拓扑排序，没有环的情况用bfs
    // 这个题应该就是类似于course schedule
    public List<String> findDependienciesWithCircle (Map<String, List<String>> dependencies, String pack) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        for (String key : dependencies.keySet()) {
            indegree.putIfAbsent(key, 0);
            for (String v : dependencies.get(key)) {
                indegree.putIfAbsent(v, 0);
                graph.putIfAbsent(v, new ArrayList<>());
                graph.get(v).add(key);
                indegree.put(key, indegree.get(key) + 1);
            }
        }
        Queue<String> queue = new LinkedList<>();
        List<String> res = new ArrayList<>();
        for (String key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                queue.offer(key);
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int l = 0; l < size; l++) {
                String cur = queue.remove();
                res.add(cur);
                if (pack.equals(cur)) {
                    return res;
                }
                if (!graph.containsKey(cur)) {
                    continue;
                }
                for (String neighbor : graph.get(cur)) {
                    indegree.put(neighbor, indegree.get(neighbor) - 1);
                    if (indegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        // 没找到pack。 或者有环，也就是A depends on B, B depends on A
        return res.size() == indegree.size() ? res : new ArrayList<>();
    }

    public List<String> findDependienciesWithoutCircle (Map<String, List<String>> dependencies, String pack) {
//         没有环用BFS 就行了。走到有pack 的这一层, indegree 用来确定起始点还是需要的。
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        for (String key : dependencies.keySet()) {
            indegree.putIfAbsent(key, 0);
            for (String v : dependencies.get(key)) {
                indegree.putIfAbsent(v, 0);
                graph.putIfAbsent(v, new ArrayList<>());
                graph.get(v).add(key);
                indegree.put(key, indegree.get(key) + 1);
            }
        }
        Queue<String> queue = new LinkedList<>();
        List<String> res = new ArrayList<>();
        for (String key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                queue.offer(key);
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int l = 0; l < size; l++) {
                String cur = queue.remove();
                res.add(cur);
                if (pack.equals(cur)) {
                    return res;
                }
                if (!graph.containsKey(cur)) {
                    continue;
                }
                for (String neighbor : graph.get(cur)) {

                        queue.offer(neighbor);

                }
            }
        }
        return res;
    }


}
