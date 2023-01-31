package com.cncounter.test.algorithm;

import org.junit.Assert;

/*
2319. 判断矩阵是否是一个 X 矩阵
如果一个正方形矩阵满足下述 全部 条件，则称之为一个 X 矩阵 ：

矩阵对角线上的所有元素都 不是 0
矩阵中所有其他元素都是 0
给你一个大小为 n x n 的二维整数数组 grid ，表示一个正方形矩阵。如果 grid 是一个 X 矩阵 ，返回 true ；否则，返回 false 。

https://leetcode.cn/problems/check-if-matrix-is-x-matrix/
 */
public class CheckXMatrix {
    public static void main(String[] args) {
        int[][] grid = {{2, 0, 0, 1}, {0, 3, 1, 0}, {0, 5, 2, 0}, {4, 0, 0, 2}};
        boolean result = new CheckXMatrix().checkXMatrix(grid);
        Assert.assertTrue(result);
    }

    /*
提示：
n == grid.length == grid[i].length
3 <= n <= 100
0 <= grid[i][j] <= 105
     */

    public boolean checkXMatrix(int[][] grid) {
        int n = grid.length;
        // 特点:
        // 对于 i=[0...(n-1)]:
        // 1. grid[i][i] 不能为0;
        // 2. grid[i][n-i-1]不能为0;
        // 3. 其他节点不能为0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                if (0 == v) {
                    // 检查0值是否在x节点上
                    if (j == i || j == (n - i - 1)) {
                        return false;
                    }
                } else {
                    // 检查非0值是否不在x节点上
                    if (j != i && j != (n - i - 1)) {
                        return false;
                    }
                }
            }
        }
        // 判断完毕: 返回true
        return true;
    }
}
