package com.example.kotlindemo;

public class Test {

    public String longestPalindrome(String s) {
        //动态规划
        /**
         * P(i,j)用来表示字符串s从i到j个字母组成的串是否为回文串
         * 由此可写出动态规划的转移方程
         * P(i,j) = P(i+1,j-1) && (S(i) == S(j))  S(i)代表的是字符串s第i个位置的字符
         *
         * 边界条件：当字串的长度为1或2时存在边界条件
         * 当长度为1 时，P(i,j)一定为true
         * 当长度为2 时，若S(i) == S(j)则P(i,j)为true，否则为false
         *
         * 状态表示用二维数组dp[i][j]
         * */

        int n = s.length();

        boolean[][] dp = new boolean[n][n];
        String ans = "";
        //l为字串的长度
        for (int l=0; l<n; l++) {
            //i为字串的起始位置
            for (int i=0; i+l<n; i++) {
                int j = i + l; //j为字串的终止位置
                if(l == 0) {
                    //即长度为1时，，肯定时true
                    dp[i][j] = true;
                }else if(l == 1) {
                    //长度为2时，
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                }else {
                    dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
                }
                //统计最长的字串,
                if(dp[i][j] && l+1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        return ans;
    }
}
