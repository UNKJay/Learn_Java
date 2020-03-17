package hust.cs.javacourse.homework3;

import java.util.Scanner;

/**
 * count number of English lettter ignoring case
 *
 */
public class CountLetter {

    /**
     * @param args: command args
     */
    public static void main (String []args) {
        Scanner scanner = new Scanner( System.in );
        String s = scanner.nextLine();
        String str = s.replace(" ","");

        int []num = new int[26];            //one more for illegal letter

        for (int i = 0; i < str.length(); ++i ) {
            char tmp = str.charAt(i);
            if ( tmp >= 'a' && tmp <= 'z'){
                ++num[tmp - 'a'];
            } else if (tmp >= 'A' && tmp <= 'Z') {
                ++num[tmp - 'A'];
            } else continue;
        }

        for (int i = 0; i < num.length; ++i) {
            if (num[i] != 0){
                System.out.print( (char)(i+'a') + ":" + num[i] + "\t");
            }
        }
    }
}
