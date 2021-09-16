package com.rui;


import java.util.*;

public class ShortestRoute {

    /*
    *A dasher sometimes travels between cities. To avoid delays, the dasher first checks for the shortest routes. Given a map of the cities and their bidirectional roads represented by a graph of nodes and edges, determine which given roads are along any shortest path. Return an array of strings, one for each road in order, where the value is YES if the road is along a shortest path or NO if it is not.The roads or edges are named using their 1-based index within the input arrays.*

*Given a map of g_nodes = 5 nodes, the starting nodes, ending nodes and road lengths are:*

*Road Index  from-city/to-city/weight*

  *1    (1, 2, 1) -> bi-directional*
  *2    (2, 3, 1)*
  *3    (3, 4, 1)*
  *4    (4, 5, 1)*
  *5    (5, 1, 3)*
  *6    (1, 3, 2)*
  *7    (5, 3, 1)*

*The traveller must travel from city 1 to city g_nodes, so from node 1 to node 5 in this case.*

*The shortest path is 3 units long and there are three paths of that length: [1 → 5], [1 → 2 → 3 → 5], and [1 → 3 → 5].*

*Return an array of strings, one for each road in order, where the value is YES if a road is along a shortest path or NO if it is not. In this case, the resulting array is*

*[roads 1, 2, 3, 4, 5, 6, 7] -> ['YES', 'YES', 'NO', 'NO', 'YES', 'YES', 'YES']. The third and fourth roads connect nodes (3, 4) and (4, 5) respectively. They are not on a shortest path, i.e. one with a length of 3 in this case.*
*
*
*
*
*
*
*
*
*
    * This was the question asked:
    Question

You need to travel between cities, but some roads may have been blocked by a recent storm. You want to check before you travel to make sure you avoid them. Given a map of the cities and their bidirectional roads, determine which roads are along any shortest path so you can check that they are not blocked. The roads or edges are named using their 1-based index within the input arrays.

For example, given a map of g_nodes = 5 nodes, the starting nodes, ending nodes and road lengths are:

Road from/to/weight
1 (1, 2, 1)
2 (2, 3, 1)
3 (3, 4, 1)
4 (4, 5, 1)
5 (5, 1, 3)
6 (1, 3, 2)
7 (5, 3, 1)

You always need to go from node 1 to node g_nodes, so from node 1 to node 5 in this case. The shortest path is 3, and there are three paths of that length: 1 → 5, 1 → 2 → 3 → 5, and 1 → 3 → 5. We create an array of strings, one for each road in order, where the value is YES if a road is along a shortest path or NO if it is not. In this case, the resulting array is [YES, YES, NO, NO, YES, YES, YES].

Function Description

Complete the function classifyEdges in the editor below. The function must return an array of g_edges strings where the value at ith index is YES if the ith edge is a part of a shortest path from vertex 1 to vertex g_nodes. Otherwise it should contain NO.

classifyEdges has the following parameter(s):

g_nodes: an integer, the number of nodes
g_from[g_from[1],...g_from[g_nodes]]: an array of integers, the start g_nodes for each road
g_to[to[1],...g_to[g_nodes]]: an array of integers, the end g_nodes for each road
g_weight[g_weight[1],...g_weight[g_nodes]]: an array of integers, the lengths of each road
Constraints

2 ≤ g_nodes ≤ 3000
1 ≤ g_edges ≤ min(105, (g_nodes x g_nodes - 1)/2)
1 ≤ g_weight[i] ≤ 105
1 ≤ g_from[i], g_to[i] ≤ g_nodes
There is at most one edge between any pair of g_nodes
The given graph is connected
Sample Input 1

4 5
1 2 1
2 4 1
1 3 1
3 4 2
1 4 2

Sample Output 1

YES
YES
NO
NO
YES

Sample Input 2

5 7
1 2 1
2 3 1
3 5 1
1 4 1
4 5 2
3 4 2
2 4 4

Sample Output 2

YES
YES
YES
YES
YES
NO
NO

Sample Input 3

4 5
1 2 1
1 3 1
1 4 1
2 3 1
2 4 1

Sample Output 3

NO
NO
YES
NO
NO
* */

//    给一个undirected connected graph，一个起点一个终点，返回从起点到终点的所有最短路径上的edges，最短路径不唯一，只要某个edge存在于某个最短路径之中，此edge就要被包含于结果之内。

    // 这个题一看就是路径规划。这他妈也考
    // 这个题是无向图

    // map 里面的list 存的是，<from, to, end>, cities 里面存的是str 和dst
    // dijkstra 的意义是把从str 到dst 所有经过的点的到str 的最短路径都求出来了

    public boolean[] shortestPath (Map<Integer, List<Integer>> edges, List<Integer> cities) {
        // 这个题不知道可不能有重复路径出现，例如1 -> 2 有三条不同的路。
        // 所以可以保存成from to roadIndex -> weight
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (Integer key : edges.keySet()) {
            List<Integer> edge = edges.get(key);
            int start = edge.get(0), end = edge.get(1), weight = edge.get(2);
            map.putIfAbsent(start, new ArrayList<>());
            map.putIfAbsent(end, new ArrayList<>());
            map.get(start).add(new int[]{end, weight, key});
            map.get(end).add(new int[]{start, weight, key});
        }
        int shortestPath = Integer.MAX_VALUE;
        // bfs 的目的是为了计算从str 到dst 的最短路径。
        Set<Integer> visited = new HashSet<>();
        // 这里一定要注意是按照最短路径排序。 Dijkstra 的时间复杂度位O（(n + E) * logn）, n 是所有的点，E 是for 循环执行的次数。
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // <node, shortestRoute>
        pq.offer(new int[]{cities.get(0), 0});
        while (!pq.isEmpty()){
            int[] cur = pq.remove();
            if (cur[0] == cities.get(1)) {
                shortestPath = cur[1];
                break;
            }
            visited.add(cur[0]);

            for (int[] edge : map.get(cur[0])) {
                if (!visited.contains(edge[0])) {
                    pq.offer(new int[]{edge[0], cur[1] + edge[1]});
                }
            }
        }
        // O(2^shortestPath), 因为要遍历所有的从str 到des 的路径。找到所有路径中经过的city。
        boolean[] res = new boolean[edges.size()];
        helper(cities.get(0), cities.get(1), map, shortestPath, res, new HashSet<>());
        return res;

    }


    private void helper (int start, int desination,  Map<Integer, List<int[]>> map, int shortestPath, boolean[] res, Set<Integer> roads) {
        if (shortestPath < 0) {
            return;
        }
        if (start == desination) {
            for (Integer r : roads) {
                res[r - 1] = true;
            }
        }

        for (int[] edge : map.get(start)) {
            if (!roads.contains(edge[2])) {
                roads.add(edge[2]);
                helper(edge[0], desination, map, shortestPath - edge[1], res, roads);
                roads.remove(edge[2]);
            }
        }
    }


    private void dfs(int from, int target, Map<Integer, List<int[]>> graph, int weight, boolean[] res, Set<Integer> roads) {
        if (weight < 0) {
            return;
        }

        if (from == target) {
            for (int road : roads) {
                res[road - 1] = true;
            }
        }

        for (int[] neis : graph.get(from)) {
// add the road
            if (!roads.contains(neis[2])) {
                roads.add(neis[2]);
                dfs(neis[0], target, graph, weight - neis[1], res, roads);
                roads.remove(neis[2]);
            }
        }
    }









    // given a map of bidrectional routes
    // find a shortest path
//    public boolean[] shortestPath(Map<Integer, List<Integer>> map, List<Integer> cities) {
//        // 1 (1, 2, 1)
//        // 1 -> [2, 1, 1]
//        Map<Integer, List<int[]>> graph = new HashMap<>();
//
//        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
//            int road = entry.getKey();
//            List<Integer> value = entry.getValue();
//            int from = value.get(0);
//            int to = value.get(1);
//            int weight = value.get(2);
//
//            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new int[]{to, weight, road});
//            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(new int[]{from, weight, road});
//        }
//
//// [city_id, sumOfWeight]
//        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
//        Set<Integer> visited = new HashSet<>();
//        pq.offer(new int[]{cities.get(0), 0});
//        int targetCity = cities.get(cities.size() - 1);
//        int lowestWeight = 0;
//
//        while (!pq.isEmpty()) {
//            int[] entry = pq.poll();
//
//            if (entry[0] == targetCity) {
//                lowestWeight = entry[1];
//                break;
//            }
//
//            visited.add(entry[0]);
//
//            for (int[] neis : graph.get(entry[0])) {
//                if (!visited.contains(neis[0])) {
//                    pq.offer(new int[]{neis[0], entry[1] + neis[1]});
//                }
//            }
//        }
//
//        boolean[] res = new boolean[map.size()];
//
//        dfs(cities.get(0), targetCity, graph, lowestWeight, res, new HashSet<>());
//        return res;
//    }

}
