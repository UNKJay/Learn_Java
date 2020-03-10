package hust.cs.javacourse.ch4;

import javax.swing.JOptionPane;
import java.util.Scanner;

/**.
 *
 *
 */
public class CheckPalindrome {
    /**.
     * Check Palindrome
     * @param s : String waiting for check
     * @return result of checking
     */
    public static boolean isPalindrome(String s){
        /*int low = 0;
        int high = s.length()-1;

        while (low < high){
            if (s.charAt(low) != s.charAt(high) ){
                return false;
            }
            low++;
            high--;
        }
        return true;*/

        String s2 = reverse(s);
        return s2.equals(s);
    }

    /**.
     *
     *
     * @param s: String wait for reversing
     * @return reverse result
     */
    public static String reverse( String s){
        StringBuffer strBuf = new StringBuffer(s);
        strBuf.reverse();
        return strBuf.toString();
    }

    /**.
     *
     * Main Method
     * @param args: command param
     */
    public static void main(String []args){
        // Prompt user to enter a string

        String s = JOptionPane.showInputDialog("Enter a string");
        String output="";
        if (isPalindrome(s)){
            output = s + " is a palindrome";
        }
        else {
            output = s + " is not a palindrome";
        }
        //Display the result
        JOptionPane.showMessageDialog(null,output);
        System.out.printf("boolean : %6b\n", false);    // false
        System.out.printf("boolean : %6b\n", true);     //  true
        System.out.printf("character : %4c\n", 'a');    //   a
        System.out.printf("integer : %6d, %6d\n", 100, 200);    //   100,   200
        System.out.printf("double : %7.2f\n", 12.345);      //  12.35
        System.out.printf("String : %7s\n", "hello");       //  hello
    }
}
