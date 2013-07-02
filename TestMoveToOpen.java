/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * This program does not take any input.  It runs TestMoveToOpen with
 * several different sets of paraneters on a hardwired grid.  It will
 * generate other a success or failure message as appropriate.
 *
 * Sample successful run:
 *   java TestMoveToOpen
 * yields
 *   Success on test/t1.txt @ (0, 2) => (0, 0)
 *   Success on test/t1.txt @ (1, 2) did not move
 *   Success on test/t1.txt @ (1, 2) => (2, 2)
 *   Success on test/t1.txt @ (0, 1) => (3, 3)
 */

public class TestMoveToOpen {
    /* mostlyTheSame: test to make sure other spots have not changed
     *  during the move
     */
    public static boolean mostlyTheSame(String f, int[][] updatedGrid, 
                                        int[][] originalGrid, 
                                        int i, int j, int k, int l) {

        // restore original values to (i,j) and (k,l) and then check
        // that the two grids are the same.
        updatedGrid[i][j] = originalGrid[i][j];
        updatedGrid[k][l] = originalGrid[k][l];
        return Utility.matchingPopulations(updatedGrid, originalGrid);
    }

    public static void testSwap(String f, int[][] grid, int[][] grid0, 
                                int myType, int i, int j, int k, int l) {
        if ((i == k) && (j == l)) {
            if (grid[i][j] != myType) {
                System.out.printf("Failed on %s @ (%d, %d) should not have moved\n", f, i, j);
            } else {
                if (mostlyTheSame(f, grid, grid0, i, j, k, l))
                    System.out.printf("Success on %s @ (%d, %d) did not move\n", f, i, j);
                else {
                    int x = Utility.unmatchedX;
                    int y = Utility.unmatchedY;
                    System.out.printf("Failed on %s @ (%d, %d) => (%d, %d): Unexpected change @ (%d, %d).   \n\tExpected %c  Got %c\n", 

                                      f, i, j, k, l, 
                                      x, y, 
                                      Utility.shortNames[grid0[x][y]],
                                      Utility.shortNames[grid[x][y]]);
                }
                
            }
            return;
        }
        
        if (grid[k][l] != myType) {
            System.out.printf("Failed on %s:   Expected %c @ (%d, %d)\tGot %c\n", f, 
                              Utility.shortNames[myType], k, l, 
                              Utility.shortNames[grid[k][l]]);
        } else if (grid[i][j] != Schelling.OPEN) {
            System.out.printf("Failed on %s:   Expected OPEN @ (%d, %d)\tGot %c\n", f, i, j,
                              Utility.shortNames[myType]);
        } else {
            if (mostlyTheSame(f, grid, grid0, i, j, k, l))
                System.out.printf("Success on %s @ (%d, %d) => (%d, %d)\n", f, i, j, k, l);
            else {
                int x = Utility.unmatchedX;
                int y = Utility.unmatchedY;
                System.out.printf("Failed on %s @ (%d, %d) => (%d, %d): Unexpected change @ (%d, %d).   \n\tExpected %c  Got %c\n", 

                                  f, i, j, k, l, 
                                  x, y, 
                                  Utility.shortNames[grid0[x][y]],
                                  Utility.shortNames[grid[x][y]]);
            }
        }
    }



    private static void test(String f, int i, int j, int k, int l, 
                             int threshold, int numOpen) {
        int[][] grid = Utility.readPopulation(f);
        int[][] grid0 = Utility.copyPopulation(grid);
        int myType = grid[i][j];

        Schelling.moveToOpen(grid, i, j, threshold, numOpen);
        testSwap(f, grid, grid0, myType, i, j, k, l);
    }


    public static void main(String[] args) {
        double[] rs = {.99};
        LocalRandom.initRandom(rs);
        String f = "test/t1.txt";

        // move B @ (0,2) to (0,0)
        test(f, 0, 2, 0, 0, 2, 6);

        // don't move R @ (1,2)
        test(f, 1, 2, 1, 2, 2, 6);

        // move R @ (1,2) to (2,2)
        test(f, 1, 2, 2, 2, 3, 6);

        // move B @ (0,1) to the 5th open spot
        test(f, 0, 1, 3, 3, 4, 6);
    }
}
