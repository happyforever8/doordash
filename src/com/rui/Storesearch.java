package com.rui;

import java.util.*;

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

    // 这个题改了个错，感觉现在是对的，12:00 pm 是大中午。这里还要问一下，会不会出现12：00 am 这个情况，12:00 am 就是00：00 am

    public static class Day {
        String day;
        String open;
        String close;
        public Day(String day, String open, String close) {
            this.day = day;
            this.open = open;
            this.close = close;
        }
    }

    private Map<String, String> days;
    // 这个是每天的营业时间不一样
    public String[] convertToTokenByDay(Day[] day) {
        if (day == null || day.length == 0) {
            return new String[0];
        }
        List<String> res = new ArrayList<>();
        days = new HashMap<>();
        days.put("mon", "1");
        days.put("tue", "2");
        days.put("wed", "3");
        days.put("thu", "4");
        days.put("fri", "5");
        days.put("sat", "6");
        days.put("sun", "7");
        for (Day d : day) {
            String[] tempOpen = d.open.split(" ");
            String[] openHourAndMinute = tempOpen[0].split(":");

            String[] tempClose = d.close.split(" ");
            String[] closeHourAndMinute = tempClose[0].split(":");
            int[] start = convertToTwentyFourHoursFormat(days.get(d.day), openHourAndMinute[0], openHourAndMinute[1], tempOpen[1].toLowerCase().equals("am"), true),
                    end = convertToTwentyFourHoursFormat(days.get(d.day),closeHourAndMinute[0], closeHourAndMinute[1], tempClose[1].toLowerCase().equals("am"), false);
            // 从start 开始每隔5min 就写入一个timestamp，直到遇到end
            while (start[0] != end[0] || start[1] != end[1] || start[2] != end[2]) {
                res.add(start[0]
                        + (start[1] / 10 == 0  ? "0" + start[1] : start[1] + "")
                        + (start[2] / 10 == 0 ? "0" + start[2] : start[2] + ""));
                start[2] += 5;
                if (start[2] == 60) {
                    start[1] ++;
                    start[2] = 0;
                }
                if (start[1] == 24) {
                    start[0]++;
                    start[1] = 0;
                }
            }
            // 不要忘了把end 写进去，因为while 的条件是不等于end，因为所有的时间都是字符串，不方便用小于等于判断。
            res.add(end[0]
                    + (start[1] / 10 == 0 ? "0" + end[1] : end[1] + "")
                    + (start[2] / 10 == 0 ? "0" + end[2] : end[2] + ""));
        }
        return res.toArray(String[]::new);
    }

    // 这个是从周一到周五，所有的营业时间
    public String[] convertToToken(String[] time) {
        // 可能需要处理输入内容的合法性？ open 在close 之前？
        if (time == null || time.length == 0) {
            return new String[0];
        }
        List<String> res = new ArrayList<>();
        days = new HashMap<>();
        days.put("mon", "1");
        days.put("tue", "2");
        days.put("wed", "3");
        days.put("thu", "4");
        days.put("fri", "5");
        days.put("sat", "6");
        days.put("sun", "7");

        String[] startTime = time[0].split(" ");
        String[] startHourAndMinutes = startTime[1].split(":");

        String[] endTime = time[1].split(" ");
        String[] endHourAndMinutes = endTime[1].split(":");

        int[] start = convertToTwentyFourHoursFormat(days.get(startTime[0]), startHourAndMinutes[0], startHourAndMinutes[1], startTime[2].equals("am"),  true),
                end = convertToTwentyFourHoursFormat(days.get(endTime[0]),endHourAndMinutes[0], endHourAndMinutes[1], endTime[2].equals("am"), false);
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

    private int[] convertToTwentyFourHoursFormat(String day, String hour, String minute, boolean isAm, boolean isStart) {
        if (day == null ||day.length() == 0 || hour == null ||hour.length() == 0 || minute == null || minute.length() == 0 ) {
            return null;
        }
        Integer d = Integer.parseInt(day);
        Integer h = Integer.parseInt(hour);
        Integer m = Integer.parseInt(minute);
        if (!isAm && h != 12) { // 转成24小时制, 这里要注意，正午是12：00 pm
            h += 12;
        }
        // 这里要问问题，就是怎么去round 这个5min
        if (isStart) {
            m = m % 5 != 0 ? m + 5 : m;
        }

        m = m / 5  * 5;
        if (m == 60) {
            h ++;
            m = 0;
        }
        if (h == 24) {
            d++;
            h = 0;
        }
        return new int[]{d, h, m};
    }
}

