package com.rui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storesearch {
/*
    Problem Statement
    To find all the stores that are open in a user’s delivery radius, we need to check the store’s operating hours. We currently store this information in Elasticsearch but the query performance for our use-case is turning out to be not very efficient. The format that we store it in Elasticsearch is something like this:

    {
"store": {
  "store_name": "Tasty Pizzas",
  "store_id": "123345",
  "operating_hours": [
  {
  "day": "mon",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  },
  {
  "day": "tue",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  },
  {
  "day": "wed",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  },
  {
  "day": "thu",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  },
  {
  "day": "fri",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  },
  {
  "day": "sat",
  "hours.open": "10:00 am",
  "hours.close": "2:00 pm"
  }
  ]
}
    }
    We want to experiment to improve the performance of fetching open stores by converting the operating hours into encoded tokens which would be of fixed length of 5. The first digit would represent the day, the next 4 digits would represent the hours in 24 hours format.

    Monday maps to number 1, Tuesday to number 2 and so on.

            Ex: Monday, 10:00am transforms to 11000 Ex: Monday, 2:00pm transforms to 11400 (as 2pm -> 14:00 in 24 hr format)

    Write a function that takes in a start_time and end_time and gives back a list of encoded tokens. Ensure you validate the input Note: We round up the time to next 5 mins

    Ex: Input: ("mon 10:00 am", "mon 11:00 am")
    Output: [“11000”, “11005”, “11010”, “11015”, “11020”, “11025”, “11030”, “11035”, “11040”, “11045”, “110‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌50”, “11055”, “11100”]

    Input: ("mon 10:15 am", "mon 11:00 am") Output: [“11015”, “11020”, “11025”, “11030”, “11035”, “11040”, “11045”, “11050”, “11055”, “11100”]

*/
    // 首先，问问题
    // 跨越了一周该咋办？
    // 每5min 一次，涉及到了具体要多少个小时，多少个分钟。怎么样去round 这个5min

    // 需要处理的是，am 和pm 的后缀，转换成24小时。
    // 处理跨越一天的时间差
    // 假设是不会跨越一周。给定的时间区间是前面的比后面的早

    private Map<String, Integer> days;
    public String[] convertToToken(String[] time) {
        // 可能需要处理输入内容的合法性？ open 在close 之前？
        if (time == null || time.length == 0) {
            return new String[0];
        }
        List<String> res = new ArrayList<>();
        days = new HashMap<>();
        days.put("mon", 1);
        days.put("tue", 2);
        days.put("wed", 3);
        days.put("thu", 4);
        days.put("fri", 5);
        days.put("sat", 6);
        days.put("sun", 7);
        int[] start = convertToTwentyFourHoursFormat(time[0], true), end = convertToTwentyFourHoursFormat(time[1],false);
        while (start[0] != end[0] || start[1] != end[1] || start[2] != end[2]) {
            res.add(start[0]
                    + (start[1] / 10 == 0  ? "0" + start[1] : start[1] + "")
                    + (start[2] / 10 == 0 ? "0" + start[2] : start[2] + ""));
            start[2] += 5;
            if ( start[2] == 60) {
                start[1] ++;
                start[2] = 0;
            }
            if (start[1] == 24) {
                start[0]++;
                start[1] = 0;
            }
        }
        res.add(end[0]
                + (start[1] / 10 == 0 ? "0" + end[1] : end[1] + "")
                + (start[2] / 10 == 0 ? "0" + end[2] : end[2] + ""));
        return res.toArray(String[]::new);
    }

    private int[] convertToTwentyFourHoursFormat(String cur, boolean isStart) {
        if (cur == null ||cur.length() == 0) {
            return null;
        }
        String[] temp = cur.split(" ");
        if (temp.length != 3) {
            return null;
        }
        int day = days.get(temp[0]);
        String[] hourAndMinute = temp[1].split(":");
        Integer hour = Integer.parseInt(hourAndMinute[0]);
        Integer minute = Integer.parseInt(hourAndMinute[1]);
        if (temp[2].equals("pm")) {
            hour += 12;
        }
        // 这里有个问题就是到底该往那边靠，对于start，应该是变成
        minute = (int)(Math.ceil((minute + (isStart ? 5 : 0)) / 5))  * 5; // round 成5 的被
        if (minute == 60) {
            hour ++;
            minute = 0;
        }
        if (hour == 24) {
            day++;
            hour = 0;
        }
        return new int[]{day, hour, minute};
    }
}

