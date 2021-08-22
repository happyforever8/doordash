package com.rui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class FindMaxiumValueInAStreamInLastXSeconds {
    
//    *Given a streaming data of the form (timestamp, value), find the maximum value in the stream in the last X seconds.*
//
//            *Assume time is monotonically increasing.*
//            *Assume time is in the order of seconds.*
//            *max_value() function finds the max in the last X seconds.*
//
//            *StreamProcessor(5) // last 5 seconds*
//            *set_value(0, 5)*
//            *set_value(1, 6)*
//            *set_value(2, 4)*
//            *max_value(3) = 6 -> always the current time*
//
//            *class StreamProcessor:*
//            *def __init__(self, x):*
//              *self.x = x*
//
//            *def set_value(self, t, v):*
//              *pass*
//
//            *def max_value(self, cur_t):*
//              *pass*
    class Data {
        int time;
        int value;
        public Data(int time, int value) {
            this.time = time;
            this.value = value;
        }
    }

    class StreamProcessor {
        // 题目只说了时间上是单调递增的，没有说时间是连续的或者是间隔一定在X 之内。所以我觉得可以用单调队列。
        Deque<Data> queue;
        int X;
        public StreamProcessor (int X) {
            // 单调递减
            queue = new ArrayDeque<>();
            this.X = X;
        }

        void setValue(int time, int value) {
            while (!queue.isEmpty() && time - queue.peekFirst().time >= this.X) {
                queue.pollFirst();
            }
            while (!queue.isEmpty() && value > queue.peekLast().value) {
                queue.pollLast();
            }
            queue.offer(new Data(time, value));
        }

        int maxValue() throws Exception {
            if (queue.isEmpty()) {
                throw new Exception("no data");
            }
            return queue.peekFirst().value;
        }

    }
}
