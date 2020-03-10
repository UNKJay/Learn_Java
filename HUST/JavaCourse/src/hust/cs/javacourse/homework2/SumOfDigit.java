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
        System.out.print("Enter a number between 0 and 1000:");
        Scanner scanner = new Scanner(System.in);

        //enter an interger
        int n = scanner.nextInt();

        //invalid value
        if (n<0 || n>1000){
            System.out.println("Invalid value!");
            return;
        }
        int sum = 0;
        while (n!=0){
            sum += n % 10;
            n /= 10;
        }
        System.out.println("The sum of the digits is " + sum);
    }
}
