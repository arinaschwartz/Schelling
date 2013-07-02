/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs countOpen on two
 * different grids.  It will generate other success or failure
 * messages as appropriate.
 *
 * Sample successful run:
 *   java TestCountNumOpen
 * yields
 *   Success on test/t0.txt:  Expected 2.Got 2
 *   Success on test/t1.txt:  Expected 6.Got 6
 *   Success on test/t5.txt:  Expected 18.Got 18
 */

public class TestCountNumOpen {
    private static void test(String f, int expected) {
        int[][] grid = Utility.readPopulation(f);
        int actual = Schelling.countNumOpen(grid);
        if ( actual == expected) {
            System.out.printf("Success on %s:  Expected %d.\tGot %d\n", f,
                              expected, actual);
        } else {
            System.out.printf("Failed on %s:   Expected %d.\tGot %d\n", f,
                              expected, actual);
        }
    }

    public static void main(String[] args) {
        test("test/t0.txt", 2);
        test("test/t1.txt", 6);
        test("test/t5.txt", 18);
    }

}
