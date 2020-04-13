package hust.cs.javacourse.homework3;

/**
 *
 * Generate 5 random car number
 * a legal car number should be made up of 3 capital letter with 5 number
 */
public class RandomCarNumber {
    /**
     *
     * @param args: command param
     */

    public static void main(String[] args) {
        final int N = 5;                    //total
        for (int n = 1; n <= N; ++n){
            String carNum = "";
            for (int i = 1; i <= 7; ++i) {
                if (i <= 3) {
                    char tmp = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
                    carNum += tmp;
                } else {
                    int tmp  = (int)(Math.random()*10);
                    carNum += tmp;
                }
            }
            System.out.println("Car Number " + n + ": " + carNum);
        }
    }
}
