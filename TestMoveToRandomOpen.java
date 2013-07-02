/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs TestMoveToRandomOpen with
 * several different sets of paraneters on a hardwired grid.  It will
 * generate other a success or failure message as appropriate.
 *
 * Sample successful run:
 *   java TestMoveToRandomOpen
 * yields
 *   Success on test/t1.txt @ (0, 1) => (2, 2)
 *   Success on test/t1.txt @ (0, 1) => (0, 0)
 *   Success on test/t1.txt @ (0, 1) => (3, 3)
 */

public class TestMoveToRandomOpen {
    private static void test(String f, int i, int j, int k, int l, int numOpen) {
        int[][] grid = Utility.readPopulation(f);
        int[][] grid0 = Utility.copyPopulation(grid);
        int myType = grid[i][j];

        Schelling.moveToRandomOpen(grid, i, j, numOpen);
        TestMoveToOpen.testSwap(f, grid, grid0, myType, i, j, k, l);
    }

    public static void main(String[] args) {
        double[] rs = {0.5, 0.0, .99};
        LocalRandom.initRandom(rs);
        String f = "test/t1.txt";

        // move B @ (0,1) to 3rd open spot
        test(f, 0, 1, 2, 2, 6);

        // move B @ (0,1) to the 0th open spot
        test(f, 0, 1, 0, 0, 6);

        // move B @ (0,1) to the 5th open spot
        test(f, 0, 1, 3, 3, 6);
    }
}
