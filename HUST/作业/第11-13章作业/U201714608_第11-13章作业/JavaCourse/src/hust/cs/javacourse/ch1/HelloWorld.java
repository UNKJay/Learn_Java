package hust.cs.javacourse.ch1;

import javax.swing.JOptionPane;
/**.
 * The first class
 *
 */

public class HelloWorld {
    /**.
     * The entrance of program
     * @param args: command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //JOptionPane.showMessageDialog(null,"Dialog message", "Dialog title",JOptionPane.QUESTION_MESSAGE);
        String string = JOptionPane.showInputDialog(null, "Prompting Message", "Dialog title", JOptionPane.QUESTION_MESSAGE);
        int i = Integer.parseInt(string);
        System.out.println("You enter is " + i);
    }
}
