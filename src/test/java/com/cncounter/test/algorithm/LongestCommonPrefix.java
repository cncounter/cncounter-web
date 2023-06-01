package com.cncounter.test.algorithm;

/*
leetcode: 14. 最长公共前缀
参考:
https://leetcode.cn/classic/problems/longest-common-prefix/description/

示例 1：

输入：strs = ["flower","flow","flight"]
输出："fl"

示例 2：

输入：strs = ["dog","racecar","car"]
输出：""
解释：输入不存在公共前缀。

提示：

1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] 仅由小写英文字母组成

 */

import java.util.Arrays;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder builder = new StringBuilder("");
        if (null == strs || strs.length < 1) {
            return builder.toString();
        }
        int maxLen = 200;
        for (int index = 0; index < maxLen; index++) {
            //
            char toCheckChar = 0;
            //
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                // 拦截字母缺少的情况
                if (str.length() < index + 1) {
                    return builder.toString();
                }
                //
                if (0 == i) {
                    toCheckChar = str.charAt(index);
                } else if (toCheckChar == str.charAt(index)) {
                    continue;
                } else {
                    return builder.toString(); // 不相等
                }
            }
            builder.append(toCheckChar);
        }
        return builder.toString();
    }


    public static void main(String[] args) {
        LongestCommonPrefix solution = new LongestCommonPrefix();
        //
        {
            String[] strs = {"flower", "flow", "flight"};
            String commonPrefix = solution.longestCommonPrefix(strs);
            System.out.println("strs=" + Arrays.toString(strs));
            System.out.println("commonPrefix=" + commonPrefix);
            System.out.println("==========");
        }
        {
            String[] strs = {"dog", "racecar", "car"};
            String commonPrefix = solution.longestCommonPrefix(strs);
            System.out.println("strs=" + Arrays.toString(strs));
            System.out.println("commonPrefix=" + commonPrefix);
            System.out.println("==========");
        }
    }
}
