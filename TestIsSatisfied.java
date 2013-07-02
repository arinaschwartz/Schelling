/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs isSatisfied with a
 * variety of parameter settings on a hardwired test grid.  It will generate other
 * success or failure messages as appropriate.
 *
 * Sample successful run:
 *   java TestIsSatisfied
 * yields:
 *   Success on (0, 0)  Expected: true .Got true
 *   Success on (0, 0)  Expected: false .Got false
 *   Success on (0, 1)  Expected: true .Got true
 *   Success on (0, 1)  Expected: false .Got false
 *   Success on (0, 3)  Expected: true .Got true
 *   Success on (0, 3)  Expected: false .Got false
 *   Success on (1, 0)  Expected: true .Got true
 *   Success on (1, 0)  Expected: false .Got false
 *   Success on (1, 2)  Expected: true .Got true
 *   Success on (1, 2)  Expected: false .Got false
 *   Success on (1, 3)  Expected: true .Got true
 *   Success on (1, 3)  Expected: false .Got false
 *   Success on (2, 1)  Expected: true .Got true
 *   Success on (2, 1)  Expected: false .Got false
 *   Success on (3, 0)  Expected: true .Got true
 *   Success on (3, 0)  Expected: false .Got false
 *   Success on (3, 1)  Expected: true .Got true
 *   Success on (3, 1)  Expected: false .Got false
 *   Success on (3, 3)  Expected: true .Got true
 *   Success on (3, 3)  Expected: false .Got false
 */

public class TestIsSatisfied {
    private static void test(int[][] grid, int i, int j, int threshold, boolean expected) {
        boolean actual = Schelling.isSatisfied(grid, i, j, threshold);
        if ( actual == expected) {
            System.out.printf("Success on (%d, %d)  Expected: %b.\tGot %b\n", i, j, 
                              expected, actual);
        } else {
            System.out.printf("Failed on (%d, %d)   Expected: %b.\tGot %b\n", i, j, 
                              expected, actual);
        }
    }



    public static void main(String[] args) {
        int[][] grid = Utility.readPopulation("test/t0.txt");
        test(grid, 0, 0, 2, true);
        test(grid, 0, 0, 3, false);

        test(grid, 0, 1, 3, true);
        test(grid, 0, 1, 4, false);

        test(grid, 0, 3, 2, true);
        test(grid, 0, 3, 3, false);

        test(grid, 1, 0, 2, true);
        test(grid, 1, 0, 3, false);

        test(grid, 1, 2, 3, true);
        test(grid, 1, 2, 4, false);

        test(grid, 1, 3, 2, true);
        test(grid, 1, 3, 3, false);

        test(grid, 2, 1, 5, true);
        test(grid, 2, 1, 6, false);

        test(grid, 3, 0, 3, true);
        test(grid, 3, 0, 4, false);

        test(grid, 3, 1, 4, true);
        test(grid, 3, 1, 5, false);

        test(grid, 3, 3, 1, true);
        test(grid, 3, 3, 2, false);
    }
}