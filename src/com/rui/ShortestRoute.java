package com.rui;


import java.util.*;
public class ShortestRoute {

    /*
    *
    * This was the question asked:

A dasher sometimes travels between cities.
* To avoid delays, the dasher first checks for the shortest routes.
*  Given a map of the cities and their bidirectional roads represented by a graph of nodes and edges, determine which given roads are along any shortest path.
* Return an array of strings, one for each road in order, where the value is YES if the road is along a shortest path or NO if it is not.The roads or edges are named using their 1-based index within the input arrays.

Example
given a map of g_nodes = 5 nodes, the starting nodes, ending nodes and road lengths are:

Road from/to/weight
1 (1, 2, 1)
2 (2, 3, 1)
3 (3, 4, 1)
4 (4, 5, 1)
5 (5, 1, 3)
6 (1, 3, 2)
7 (5, 3, 1)

Example Input: (5, [1, 2, 3, 4, 5, 1, 5], [
2, 3, 4, 5, 1, 3, 3], [1, 1, 1, 1, 3, 2, 1])
The traveller must travel from city 1 to city g_nodes, so from node 1 to node 5 in this case.
The shortest path is 3 units long and there are three paths of that length: 1 → 5, 1 → 2 → 3 → 5, and 1 → 3 → 5.
Return an array of strings, one for each road in order, where the value is YES if a road is along a shortest path or NO if it is not. In this case, the resulting array is ['YES', 'YES', 'NO', 'NO', 'YES', 'YES', 'YES']. The third and fourth roads connect nodes (3, 4) and (4, 5) respectively. They are not on a shortest path, i.e. one with a length of 3 in this case.

    *
    *
    *
    *
    *
    *
    * You need to travel between cities, but some roads may have been blocked by a recent storm. You want to check before you travel to make sure you avoid them. Given a map of the cities and their bidirectional roads, determine which roads are along any shortest path so you can check that they are not blocked. The roads or edges are named using their 1-based index within the input arrays.

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
    *
    *
    *
    *
    *
    *
    * *
    * */

    // 这个题一看就是路径规划。这他妈也考

    public static void main(String[] args) {
Solution shortestPath = new Solution();
         Map<Integer, List<Integer>> map = new HashMap<>();
         map.put(0, Arrays.asList(1, 2, 1));
         map.put(1, Arrays.asList(2, 3, 1));
         map.put(2, Arrays.asList(3, 4, 1));
         map.put(3, Arrays.asList(4, 5, 1));
         map.put(4, Arrays.asList(5, 1, 3));
         map.put(5, Arrays.asList(1, 3, 2));
         map.put(6, Arrays.asList(5, 3, 1));
          
         List<Integer> cities = Arrays.asList(1, 2, 3, 4, 5);
          
         boolean[] res = shortestPath.shortestPath(map, cities);
          
         for (boolean used : res) {
            System.out.println(used + ", ");
         }
     }
      
            // given a map of bidrectional routes
            // find a shortest path
            public boolean[] shortestPath(Map<Integer, List<Integer>> map, List<Integer> cities) {
        // 1 (1, 2, 1)
        // 1 -> [2, 1, 1]
        Map<Integer, List<int[]>> graph = new HashMap<>();
         
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int road = entry.getKey();
            List<Integer> value = entry.getValue();
            int from = value.get(0);
            int to = value.get(1);
            int weight = value.get(2);
             
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new int[]{to, weight, road});
            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(new int[]{from, weight, road});
        }
         
        // [city_id, sumOfWeight]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{cities.get(0), 0});
        int targetCity = cities.get(cities.size() - 1);
        int lowestWeight = 0;
         
        while (!pq.isEmpty()) {
            int[] entry = pq.poll();
             
            if (entry[0] == targetCity) {
                lowestWeight = entry[1];
                break;
            }
             
            visited.add(entry[0]);
             
            for (int[] neis : graph.get(entry[0])) {
                if (!v‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌isited.contains(neis[0])) {
                    pq.offer(new int[]{neis[0], entry[1] + neis[1]});
                }
            }
        }
         
        boolean[] res = new boolean[map.size()];
         
        dfs(cities.get(0), targetCity, graph, lowestWeight, res, new HashSet<>());
        return res;
    }
     
            private void dfs(int from, int target, Map<Integer, List<int[]>> graph, int weight, boolean[] res, Set<Integer> roads) {
        if (weight < 0) {
            return;      
        }
         
        if (from == target) {
            for (int road : roads) {
                res[road] = true;
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

}
