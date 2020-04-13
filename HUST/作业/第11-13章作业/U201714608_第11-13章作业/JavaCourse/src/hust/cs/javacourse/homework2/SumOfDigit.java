package hust.cs.javacourse.homework2;

import java.util.Scanner;

/**.
 * get sum of all digits of an interger
 */
public class SumOfDigit {
    /**.
     *
     * @param args: command param
     */
    public static void main(String []args){

        int value = getInput();
        int sum = sumOfBits(value);
        System.out.println("Input integer is : " + value + " , sum of bits is :" + sum);

    }

    public static int getInput() {
        int value = -1;
        Scanner scanner = new Scanner(System.in);
        while (!(value >= 0 && value <= 1000)) {
            try {
                System.out.print("Enter a number between [0,1000]:");
                value = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Input is not a valid value:" + e.toString());
                //Scanner 的 nextInt 方法抛出异常后,内部的缓存区没有清空
                //如果继续调用 nextInt, 会继续解析缓冲区里错误格式的内容,导致反复抛出异常
                //因此要调用 input.next()方法把缓冲区内容取出丢弃
                scanner.next();
            }
        }
        return  value;
    }

//    public static int getInput(){
//        int value = -1;
//        Scanner input = new Scanner(System.in);
//        while(!((value >= 0) && (value <= 1000))){
//            try {
//                System.out.print("Enter a number between [0,1000]:");
//                value = input.nextInt();
//            } catch (Exception e) {
//                System.out.println("Input is not a valid value:" + e.toString());
//                //Scanner 的 nextInt 方法抛出异常后,内部的缓存区没有清空
//                //如果继续调用 nextInt, 会继续解析缓冲区里错误格式的内容,导致反复抛出异常
//                //因此要调用 input.next()方法把缓冲区内容取出丢弃
//                input.next();
//            }
//        }
//        return value;
//    }

    public static int sumOfBits(int value){
        int sum = 0;
        while( value > 0){
            int lastBit = value % 10;
            sum += lastBit;
            value = value / 10;
        }
        return sum;
    }

}
