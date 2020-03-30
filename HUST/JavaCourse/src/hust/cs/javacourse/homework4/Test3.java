package hust.cs.javacourse.homework4;

import java.util.Scanner;

public class Test3 {
    /**
     *  创建一个不规则二维数组
     *  第一行row列
     *  第二行row - 1列
     *  ...
     *  最后一行1列
     *	数组元素值都为默认值
     * @param row 行数
     * @return 创建好的不规则数组
     */
    public static  int[][] createArray(int row){
        if (row <= 0 ){
            return null;    //返回空引用
        }

        int [][]intArray = new int[row][];
        for (int i=0;i<row;++i) {
            intArray[i] = new int[row-i];
        }
        return intArray;
    }

    /**
     * 逐行打印出二维数组，数组元素之间以空格分开
     * @param a
     */
    public static  void printArray(int[][] a){
        for (int i=0; i<a.length; ++i) {
            for (int j=0; j<a[i].length; ++j) {
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     * 程序入口 main 函数
     * @param args 命令行参数
     */
    public static void main(String...args) {
        System.out.print("Please enter the row (>0):");
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int [][] testArray = createArray(row);
        if (testArray == null) {
            System.out.println("Row is illegal!");
            return;
        }
        printArray(testArray);
    }
}