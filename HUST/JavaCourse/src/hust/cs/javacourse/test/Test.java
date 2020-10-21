package hust.cs.javacourse.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static int reverseInt(int n){
//        int tmp = n;
//        if (tmp<0) {
//            tmp=-tmp;
//        }
//        StringBuffer buf = new StringBuffer(tmp);
//        buf.reverse();
//        tmp = Integer.parseInt(buf.toString());
//        if (n<0){
//            tmp = -tmp;
//        }
//        return tmp;
        int tmp = n;
        if (tmp<0){
            tmp=-tmp;
        }
        String s= String.valueOf(tmp);
        StringBuffer buf = new StringBuffer(s);
        buf.reverse();
        tmp = Integer.parseInt(buf.toString());
        if (n<0) {
            return -tmp;
        }
        return tmp;
    }
    public static void main(String... args) {
//        A o1 = new A();
//        A o2 = new B();
//        o1.method1(); //1
//        o2.method1(); //2
//        o1.method1(1); //3
//        o2.method1(1); //4
//        o1.method1(1L); //5
//        o2.method1(1L); //6
//        ((B)o1).method2(); //7
//        ((B)o2).method2(); //8


        System.out.println(reverseInt(-123456));
        System.out.println(reverseInt(0));
        System.out.println(reverseInt(123456));

        String lineString = "I I a";
        String[] s = lineString.split("[\\s]");
        for (String str:s){
            System.out.println(str);
        }
        List<String> tmp = new ArrayList<>();
        Iterator<String> i = tmp.iterator();


    }
}
