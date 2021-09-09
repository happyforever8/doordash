package com.rui;

public class KAnagram {
//    是easy。不过有两个小题，第一个是类似一个edit distance 的问题。但都是easy。
//
//    拓展是用一个list不是用map存。
//    KAnagra : https://www.geeksforgeeks.org/check-two-strings-k-anagrams-not/
//    Given two strings of lowercase alphabets and a value k, the task is to find if two strings are K-anagrams of each other or not.
//    Two strings are called k-anagrams if following two conditions are true.
//
//    Both have same number of characters.
//    Two strings can become anagram by changing at most k characters in a string.
    // https://www.1point3acres.com/bbs/thread-794253-1-1.html 这个第二题第一问没找到。

    public boolean isKAnagram(String a, String b, int k) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        int[] countA = new int[26];
        int[] countB = new int[26];
        for (int i = 0; i < a.length(); i++) {
            countA[a.charAt(i) - 'a']++;
            countB[b.charAt(i) - 'a']++;
        }
        int diff = 0;
        for (int i = 0; i < 26; i++) {
            // 这里只需要考虑把a 变成b。所欲i
            if (countA[i] > countB[i]) {
                diff += countA[i] - countB[i];
            }

        }
        return diff <= k;
    }
}
