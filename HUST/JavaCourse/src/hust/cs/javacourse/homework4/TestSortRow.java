package hust.cs.javacourse.homework4;

import java.util.*;

public class TestSortRow {
    /**
     * 实现二维数组的行排序，不破坏原有数组
     * @param m 原数组
     * @return 行排序后的数组
     */
    public static double[][] sortRows(double[][] m) {
        double [][]newArray = m.clone();                //复制
        for (int i=0; i<newArray.length; ++i) {
            java.util.Arrays.sort(newArray[i]);
        }
        return newArray;
    }

    /**
     * main 函数
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a 3-by-3 matrix row by row:");
        double [][]m = new double[3][3];
        for (int i=0; i<m.length; ++i) {
            for (int j=0; j<m[i].length; ++j) {
                m[i][j] = scanner.nextDouble();
            }
        }

        double[][] oldArray = m.clone();
        double[][] newArray = sortRows(m);

        if (Arrays.equals(oldArray,m)) {                //判断是否改变
            for (int i=0; i<newArray.length; ++i) {
                for (int j=0; j<newArray[i].length; ++j) {
                    System.out.print(newArray[i][j]+" ");
                }
                System.out.println();
            }
        }else {
            System.out.println("Array has been changed!");
        }
    }

}

