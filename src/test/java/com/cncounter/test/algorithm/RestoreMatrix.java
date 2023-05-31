package com.cncounter.test.algorithm;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;

import java.util.Random;

public class RestoreMatrix {


    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int rows = rowSum.length;
        int cols = colSum.length;
        // 初始化空矩阵
        int[][] resultMatrix = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            int[] row = new int[cols];
            resultMatrix[r] = row;
        }
        // ... 填充 resultMatrix 的值;
        //
        return resultMatrix;
    }

    public static void main(String[] args) {
        RestoreMatrix instance = new RestoreMatrix();
        int count = 4;
        for (int i = 1; i <= count; i++) {
            // 随机矩阵
            int[][] randomMatrix = randomMatrix(i);
            int[] rowSum = calcRowSum(randomMatrix);
            int[] colSum = calcColSum(randomMatrix);
            // 计算得到的结果
            int[][] resultMatrix = instance.restoreMatrix(rowSum, colSum);
            // 校验
            int[] resultRowSum = calcRowSum(resultMatrix);
            int[] resultColSum = calcColSum(resultMatrix);
            //
            System.out.println(":::================:::" + i);
            System.out.println("randomMatrix=:\n" + JSON.toJSON(randomMatrix));
            System.out.println("================");
            System.out.println("rowSum=:\n" + JSON.toJSON(rowSum));
            System.out.println("================");
            System.out.println("colSum=:\n" + JSON.toJSON(colSum));
            System.out.println("================");
            System.out.println("resultMatrix=:\n" + JSON.toJSON(resultMatrix));
            System.out.println("================");

            // 期望: 结果相等
            for (int r = 0; r < rowSum.length; r++) {
                //Assert.assertEquals(rowSum[r], resultRowSum[r]);
            }
            for (int c = 0; c < colSum.length; c++) {
                //Assert.assertEquals(colSum[c], resultColSum[c]);
            }
        }
    }

    private static int[][] randomMatrix(int i) {
        Random random = new Random(i);
        int cols = random.nextInt(i) + 1;
        int rows = i;
        //
        int[][] matrix = new int[rows][];
        //
        for (int r = 0; r < rows; r++) {
            //
            int[] randomRow = new int[cols];
            for (int c = 0; c < cols; c++) {
                randomRow[c] = random.nextInt(i);
            }
            matrix[r] = randomRow;
        }
        return matrix;
    }

    private static int[] calcRowSum(int[][] matrix) {
        int rows = matrix.length;
        int[] rowSum = new int[rows];
        for (int r = 0; r < rows; r++) {
            int sum = 0;
            int[] row = matrix[r];
            int cols = row.length;
            for (int c = 0; c < cols; c++) {
                sum += row[c];
            }
            rowSum[r] = sum;
        }
        return rowSum;
    }

    // 计算矩阵每个列的和
    private static int[] calcColSum(int[][] matrix) {
        //
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] colSum = new int[cols];
        for (int c = 0; c < cols; c++) {
            int sum = 0;
            for (int r = 0; r < rows; r++) {
                sum += matrix[r][c];
            }
            colSum[c] = sum;
        }
        //
        return colSum;
    }
}
