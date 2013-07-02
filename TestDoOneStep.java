/* CS121 A'11
 * HW2: Schelling Model of Housing Segregation
 *
 * Anne Rogers
 * Sept 2011
 *
 * The program runs DoOneStep on a variety of grids.  If the result of
 * running doOneStep on a grid with a specified threshold yields the
 * expected result, the program will output a success message.
 * Otherwise, it will output an error message.
 */

public class TestDoOneStep {
    private static void test(String f0, String f1, int threshold) {
        int[][] grid0 = Utility.readPopulation(f0, true);
        int[][] grid1 = Utility.readPopulation(f1);

        if ((grid0.length != grid1.length) || 
            (grid0[0].length != grid1[0].length)) {
            System.out.printf("Format error: input and expected output grids must be the same size\n\t%s is a %dx%d array\n\t%s is %dx%d array\n", f0, grid0.length, grid1[0].length,
                              f1, grid1.length, grid1[0].length);
            return;
        }


        int numOpen = Schelling.countNumOpen(grid0);
        Schelling.doOneStep(grid0, threshold, numOpen);

        if (!Utility.matchingPopulations(grid1, grid0)) {
            int x = Utility.unmatchedX;
            int y = Utility.unmatchedY;
            System.out.printf("Failed on %s =>%s:   Expected %c @ (%d, %d)\tGot %c\n", f0, f1,
                              Utility.shortNames[grid1[x][y]], x, y, 
                              Utility.shortNames[grid0[x][y]]);     
            return;
        }
        System.out.printf("Success on %s => %s\n", f0, f1);
    }


    public static void main(String[] args) {
        test("test/t6.txt", "test/t6-1-3.txt", 3);
        test("test/t2.txt", "test/t2-1-2.txt", 2);
        test("test/t3.txt", "test/t3-1-2.txt", 2);
        test("test/t4.txt", "test/t4-1-2.txt", 2);
        test("test/t5.txt", "test/t5-1-2.txt", 2);
    }
}
