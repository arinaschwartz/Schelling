/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * The program runs DoSimualtion on a variety of small and large
 * grids.  If the result of running doSimulation on a grid with the
 * specified threshold yields the expected result, the program will
 * output a success message.  Otherwise, it will output an error
 * message.
 */

public class TestDoSimulation {
    private static boolean test(String f0, String f1, int threshold, int steps) {
        int[][] grid0 = Utility.readPopulation(f0, true);

        System.out.printf("Testing %s => %s @ threshold of %d\n", f0, f1, threshold);
        int ns = Schelling.doSimulation(grid0, threshold);
        if (ns != steps) {
            System.err.printf("    Failed: Got: %d step(s).   Expected %d step(s)\n",
                              ns, steps);
        }

        int[][] grid1 = Utility.readPopulation(f1);
        if (!Utility.matchingPopulations(grid0, grid1)) {
            int x = Utility.unmatchedX;
            int y = Utility.unmatchedY;
            System.out.printf("    Failed:   Expected %c @ (%d, %d)\tGot %c\n\n",
                              Utility.shortNames[grid1[x][y]], x, y, 
                              Utility.shortNames[grid0[x][y]]);     
            return false;
        }
        System.out.printf("    Success!\n\n");
        return true;
    }

    public static void main(String[] args) {
        int cnt = 0;
        if (test("test/t2.txt", "test/t2-final-2.txt", 2, 1)) {
            cnt++;
        }


        if (test("test/t3.txt", "test/t3-final-2.txt", 2, 1)) {
            cnt++;
        }
         
        if (test("test/t4.txt", "test/t4-final-2.txt", 2, 3)) {
            cnt++;
        }
         
        if (test("test/t5.txt", "test/t5-final-2.txt", 2, 1000)) {
            cnt++;
        }

        // do tests on larger grid
        if (cnt == 4) {
            test("test/t-150-45-45.txt", "test/t-150-45-45-3-final.txt", 3, 16);
            test("test/t-150-45-45.txt", "test/t-150-45-45-4-final.txt", 4, 18);
            test("test/t-150-45-45.txt", "test/t-150-45-45-5-final.txt", 5, 1000);
        }
    }
}
