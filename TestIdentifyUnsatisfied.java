/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs
 * TestIdentifyUnsatisfied on a hardwired grid.  It will generate
 * other a success or failure message as appropriate.
 *
 * Sample successful run:
 *   java TestIdentifyUnsatisfied
 * yields
 *   Success on test/t1.txt
 */

public class TestIdentifyUnsatisfied {
    private static void test(int[][] grid, String f, int threshold, boolean[][] expected) {
        boolean failed = false;
        boolean[][] actual = Schelling.identifyUnsatisfied(grid, threshold);
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[0].length; j++) {
                if (actual[i][j] != expected[i][j]) {
                    System.out.printf("Failed on %s @ (%d, %d):   Expected %b.\tGot %b\n", f, i, j,
                                      expected[i][j], actual[i][j]);
                    failed = true;
                }
            }
        }

        if (!failed) 
            System.out.printf("Success on %s\n", f);
    }

    public static void main(String[] args) {
        String f = "test/t1.txt";
        int[][] grid = Utility.readPopulation(f);
        boolean[][] expected = new boolean[grid.length][grid[0].length];
        expected[0][2] = true;
        expected[1][0] = true;
        expected[1][3] = true;
        expected[2][3] = true;
        test(grid, f, 2, expected);
    }

}