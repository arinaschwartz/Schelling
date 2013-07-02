/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs TestAllSatisfied
 * with two different sets of parameters on a hardwired grid.  It will
 * generate other success or failure messages as appropriate.
 *
 * Sample successful run:
 *   java TestAllSatisfied
 * yields
 *   Success on test/t1.txt:  Expected false.Got false
 *   Success on test/t1.txt:  Expected true.Got true
 */

public class TestAllSatisfied {
    private static void test(String f, int threshold, boolean expected) {
        boolean failed = false;
        int[][] grid = Utility.readPopulation(f);
        boolean actual = Schelling.allSatisfied(grid, threshold);
        if (actual != expected) {
            System.out.printf("Failed on %s:   Expected %b.\tGot %b\n", f,
                              expected, actual);
        } else {
            System.out.printf("Success on %s:  Expected %b.\tGot %b\n", f, expected, 
                              actual);
        }
    }

    public static void main(String[] args) {
        String f = "test/t1.txt";
        test(f, 2, false);
        test(f, 0, true);
    }
}