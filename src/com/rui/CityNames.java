package com.rui;

import java.util.*;

public class CityNames {

    //给一组城市name 坐标x 坐标y 输入一系列query name 返回相同x或者相同y的最近city name
    //every city name is guaranteed to be unique and no 2 cities will have same coordinates
    //如果没有则返回'NONE'
    //注意如果有相同的最近的城市，返回alphabet更小的城市
    // 对应的是输入城市名字到临近城市中最近的城市名，如果没有就是“none”, 所谓的临近城市的定义是指两个城市只有x坐标或者y坐标有一个完全相同才可以被看作是‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌临近的城市。 这题做完后还有follow up问题但我没有来得及看。

    // 就是input 有 c x y q。
    //c是城市名字， xy是c的坐标值，q 是需要look up的城市的名字
    //据我理解就是：
    //返回所有q里面我们感兴趣的城市的corresponding最近的城市的名字。
    //然后最近的城市算的是曼哈顿距离，不是开根号的那个距离。两个城市只有或者x 坐标相同或者y坐标相同才构成临近城市的资格。
    // 请问这个题目query name给的是city name吗？ 已知一堆city 的x, y坐标， 比如说 [a, 0, 1] , [b,0,9], [c,0,2] , [d,0,2] 然后query是a， expected output是c,  对吗？ 因为b虽然x=0， 但距离a很远， c,d距离a一样，但c的alphabet更小，所以返回c。 是的 而且假设query name 都是存在的city 都是valid的
    //

    //    Nearest Neighbour City
    //
    //    A number of cities are arranged on a graph that has been divided up like an ordinary Cartesian plane. Each city is located at an integral (x, y) coordinate intersection. City names and locations are given in the form of three arrays: c, x, and y, which are aligned by the index to provide the city name (c[i]), and its coordinates, (x[i], y[i]). Determine the name of the nearest city that shares either an x or a y coordinate with the queried city. If no other cities share an x or y coordinate, return 'NONE'. If two cities have the same distance to the queried city, q[i], consider the one with an alphabetically shorter name (i.e. 'ab' < 'aba' < 'abb') as the closest choice. The distance is the Manhattan distance, the absolute difference in x plus the absolute difference in y.
    //
    //    The time complexity for my solution is O(NlogK) for processing input + O(QlogK) for returning the result for all the given queries,
    //    where N is the number of cities, K is the max number of cities with same x or y coordinate and Q is the number of queries.


    /*
       问问题：
       1。 这个距离指的是啥？怎么计算这个距离
       2。 这个坐标都都是整数坐标么？
       3。 数据有多大。给点constraints
       4。
       4。
     */
    // 曼哈顿距离是 |x1 - X2| + |y1 - y2|

    // 这个的目的是为了让不同类型的元素放在一起。
    class City {
        int x;
        int y;
        String name;
        public City (int x, int y, String name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }

    private Integer distance(City a, City b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public List<String> closestCity(List<String> cities, List<Integer> x, List<Integer> y, List<String> queries) {
        Map<Integer, TreeMap<Integer, String>> sameX = new HashMap<>();
        Map<Integer, TreeMap<Integer, String>> sameY = new HashMap<>();
        Map<String, City> cityCoordinate = new HashMap<>();
        for (int i = 0; i < cities.size(); i++) {
            cityCoordinate.put(cities.get(i), new City(x.get(i), y.get(i), cities.get(i)));

            // 这俩treemap 都是按照x 或者y 的值从小到大排的。
            // 目的是为了找到每个query 的floor key 和ceiling key，然后比较谁距离query 的这个更近。
            // 所以这样的话x 轴方向需要判断俩点，y轴方向需要判断俩点，外加lexical 的顺序。总共5个方向。
            sameX.putIfAbsent(x.get(i), new TreeMap<>());
            sameX.get(x.get(i)).put(y.get(i), cities.get(i));

            sameY.putIfAbsent(y.get(i), new TreeMap<>());
            sameY.get(y.get(i)).put(x.get(i), cities.get(i));
        }

        List<String> res = new ArrayList<>();
        for (String q : queries) {
            City city = cityCoordinate.get(q);
            // 如果距离相等，选lexical 小的那个。
            PriorityQueue<City> pq = new PriorityQueue<>((a, b) -> distance(a, city).compareTo(distance(b, city) == 0 ? a.name.compareTo(b.name) :  distance(a, city).compareTo(distance(b, city))));
            Map.Entry<Integer, String> prevXEntry = sameX.get(city.x).lowerEntry(city.y);
            if (prevXEntry != null){
                pq.offer(new City(city.x, prevXEntry.getKey(), prevXEntry.getValue()));
            }
            Map.Entry<Integer, String> nextXEntry = sameX.get(city.x).higherEntry(city.y);
            if (nextXEntry != null){
                pq.offer(new City(city.x, nextXEntry.getKey(), nextXEntry.getValue()));
            }

            Map.Entry<Integer, String> prevYEntry = sameY.get(city.y).lowerEntry(city.x);
            if (prevYEntry != null){
                pq.offer(new City(city.x, prevYEntry.getKey(), prevYEntry.getValue()));
            }
            Map.Entry<Integer, String> nextYEntry = sameY.get(city.y).higherEntry(city.x);
            if (nextYEntry != null){
                pq.offer(new City(city.x, nextYEntry.getKey(), nextYEntry.getValue()));
            }
            res.add(pq.poll().name);
        }
        return res;
    }

    // 这里补充一个leetcode 1779
    private int nearestValidPoint(int x, int y, int[][] points) {
        int dis = Integer.MAX_VALUE, index = points.length;
        for (int i = 0; i < points.length; i++) {
            int[] p = points[i];
            if (p[0] == x || p[1] == y) {
                int d = Math.abs(x - p[0]) + Math.abs(y - p[1]);
                if (d < dis) {
                    index = i;
                    dis = d;
                }
            }
        }
        return index == points.length ? -1 : index;
    }


}
