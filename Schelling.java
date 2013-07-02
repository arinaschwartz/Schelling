/*  CS121 A'11
 *  HW2: Schelling Model of Housing Segregation
 *
 *  Neighborhood is defined as the eight cells that surround a cell.
 *  Cells at the boundaries will have fewer than eight neighbors.
 *
 *  The lines of code listed with each function is the number of lines
 *  in our reference implementation.
 */

import java.util.*;
import java.io.File;


public class Schelling {
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int OPEN = 2;
    public static final int MAX_STEPS = 1000;

    // The variable should updated whenever a move is done.
    public static int movesCompleted = 0;


    /*  doSimulation: execute steps until all the homeowners are
     *    satisfied or MAX_STEPS steps have been executed.
     *
     *    int grid[][]: the grid
     *    int threshold: homeowner's threshold for the minimum 
     *      neighbors of the same color 
     *    int numOpen: the number of cells open in the grid.
     *
     *  (9 lines)
     */
    public static int doSimulation(int[][] grid, int threshold) 
    {
        int g = countNumOpen(grid);
        for(int x = 0; x <= MAX_STEPS; x++)
        {
            doOneStep(grid, threshold, g);
            boolean z = allSatisfied(grid, threshold);
            if(z == true)
            {
                return x;
            }
        }
               
        return MAX_STEPS;
    }
    
    public static boolean isSatisfied(int[][] grid, int i, int j, int threshold)
    {
        int solidarity = 0;
        //Sets Upper and Lower Bounds for the area around our given point
        int iLB = i - 1;
        int iUB = i + 1;
        int jLB = j - 1;
        int jUB = j + 1;
        
        
        //Accounting for open spaces

        //Taking border cases into account for i-coord
        
        if (i == 0)
        {
            iLB = i;
        }
        if (i == grid.length - 1)
        {
            iUB = i;
        }
        //Border cases for j-coord
        
        if (j == 0)
        {
            jLB = j;
        }
        if (j == grid[0].length - 1)
        {
            jUB = j;
        }
        
        
        for(int x = iLB; x <= iUB; x++)
        {
            for(int y = jLB; y <= jUB; y++)
            {
                if(grid[i][j] == grid[x][y])
                {
                solidarity++;
                }
            }
        }

        //Determining satisfaction
        if(solidarity <= threshold)
        {
            return false;
        }
        return true;
    }
    
    //number of open slots
    public static int countNumOpen(int[][] grid)
    {
        int numOpen = 0;
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                if(grid[x][y] == 2)
                {
                    numOpen++;
                }
            }
        }
        return numOpen;
    }
    
    //Are all the people satisfied?
    public static boolean allSatisfied(int[][] grid, int threshold)
    {
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                boolean q = isSatisfied(grid, x, y, threshold);
                if(q == false)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Moves unsatisfied person to random open slot
    
    public static void moveToRandomOpen(int[][] grid, int i, int j, int numOpen)
    {
        //Generates random number to index to an open slot
        double q = LocalRandom.rand();
        double s = q * (numOpen);
        int r = (int)s;
        int count = 0;
        
        //moves through array, counting open slots until it hits the indexed number
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                if(grid[x][y] == 2)
                {
                    if(count >= r)
                    {
                        grid[x][y] = grid[i][j];
                        grid[i][j] = 2;
                        return;
                    }
                    count++;
                }
            }
        }
    }
    
    //Moves unsatisfied person to FIRST open location that satisfies criterion
    public static void moveToOpen(int[][] grid, int i, int j, int threshold, int numOpen)
    {
        int countOpenUnsatisfied = 0;
        if(grid[i][j] != 2)
        {
            boolean q = isSatisfied(grid, i, j, threshold);
            if(q == false)
            {
                for(int x = 0; x < grid.length; x++)
                {
                    for(int y = 0; y < grid[0].length; y++)
                    {
                        if(grid[x][y] == 2)
                        {
                            grid[x][y] = grid[i][j];
                            boolean z = isSatisfied(grid, x, y, threshold);
                            if(z == true)
                            {
                                grid[i][j] = 2;
                                return;
                            }
                            else
                            {
                                grid[x][y] = 2;
                                countOpenUnsatisfied++;
                            }
                        }
                    }
                }
            }
        }
        if(countOpenUnsatisfied == numOpen)
        {
            moveToRandomOpen(grid, i, j, numOpen);
        }
    }
    
    public static boolean[][] identifyUnsatisfied(int[][] grid, int threshold)
    {
        boolean[][] unsatisfied = new boolean[grid.length][grid[0].length];
        
        for(int x = 0; x < unsatisfied.length; x++)
        {
            for(int y = 0; y < unsatisfied[0].length; y++)
            {
                if(grid[x][y] != 2)
                {
                    boolean q = isSatisfied(grid, x, y, threshold);
                    if(q == false)
                    {
                        unsatisfied[x][y] = true;
                    }
                }
              
            }
        }
        return unsatisfied;
    }
    
    public static void doOneStep(int[][] grid, int threshold, int numOpen)
    {
        for(int x = 0; x < grid.length; x++)
        {
            for (int y = 0; y < grid[0].length; y++)
            {
                if(grid[x][y] != 2)
                {
                    boolean q = isSatisfied(grid, x, y, threshold);
                    if(q == false)
                    {
                        moveToOpen(grid, x, y, numOpen, threshold);
                    }
                }
            }
        }
    }

    


    /* YOUR AUXILIARY FUNCTIONS GO HERE. */

    /*  Uncomment the code below, if you want to use our suggested structure for your code.

    // isSatisfied: is the homeowner at cell (i,j) satisfied?
    //   int grid[][]: the grid
    //   int i, int j: a grid cell
    //   int threshold: homeowner's threshold for the minimum neighbors of the same color 
    //
    //   Returns true, if the neighbor has at least threshold neighbors of the same color,
    //   false otherwise.
    //
    //  (25 lines)
    public static boolean isSatisfied(int[][] grid, int i, int j, int threshold) {
        return false;
    }



    // countNumOpen: count the number of open spots in the grid
    //
    // (10 lines)
    public static int countNumOpen(int[][] grid) { 
        return 0;
    }



    // allSatisfied: are all the homeowners satisfied?
    //  int grid[][]: the grid
    //  int threshold: homeowner's threshold for the minimum neighbors of the same color 
    //
    //  Returns true, if all the homeowners are satisfied with their locations,
    //  false otherwise.
    //
    // (12 lines)
    public static boolean allSatisfied(int[][] grid, int threshold) {
        return false;
    }



    // moveToRandomOpen: move the homeowner at cell (i,j) to a randomly chosen open location
    //  int grid[][]: the grid
    //  int i, int j: a grid cell
    //  int numOpen: the number of cells open in the grid.
    //
    // (17 lines)
    public static void moveToRandomOpen(int[][] grid, int i, int j, int numOpen) {
        return;
    }



    // moveToOpen: if the homeowner at cell (i, j) is unsatisfied, move
    //   him the first open location that satistfies his criteria.  If
    //   not such location exists, move him to a randomly chosen
    //   location.
    //
    //   This function verifies that the homeowner is unsatisfied before moving him.
    //
    //   int grid[][]: the grid
    //   int i, int j: a grid cell
    //   int threshold: homeowner's threshold for the minimum neighbors of the same color 
    //   int numOpen: the number of cells open in the grid.
    //
    // (19 lines)
    public static void moveToOpen(int[][] grid, int i, int j, int threshold, int numOpen) {
        return;
    }



    // indentifyUnsatisfied:
    //
    //   int grid[][]: the grid
    //   int threshold: homeowner's threshold for the minimum neighbors of the same color 
    //
    //   Return a two dimensional array where cell (i,j) is true, if
    //   the homeowner in corresponding grid cell is unsatisfied.
    //
    //  (11 lines)
    public static boolean[][] identifyUnsatisfied(int[][] grid, int threshold) {
        return null;
    }



    //  doOneStep: do one step of the simulation 
    //    int grid[][]: the grid
    //    int threshold: homeowner's threshold for the minimum neighbors of the same color 
    //    int numOpen: the number of cells open in the grid.
    //
    //  (10 lines)
    public static void doOneStep(int[][] grid, int threshold, int numOpen) {
        return;
    }

    */


    
    /* main function: do not change*/
    public static void main(String[] args) {
        int threshold = 0;
        int grid[][] = null;
        String usage = "usage: java Schelling <grid size> <% red> <% blue> <threshold>  OR \n       java Schelling <population file name> <threshold>";

    
        if (args.length == 2) {
            grid = Utility.readPopulation(args[0]);
            try {
                threshold = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.err.println(usage);
                System.exit(0);
            }
        } else if (args.length == 4) {
            try {
                int gridSize = Integer.parseInt(args[0]);
                double pctRed = Double.parseDouble(args[1]);
                double pctBlue = Double.parseDouble(args[2]);
                threshold = Integer.parseInt(args[3]);
                grid = Utility.generatePopulation(gridSize, pctRed, pctBlue);
            } catch (Exception e) {
                System.err.println(usage);
                System.exit(0);
            }
        } else {
            System.err.println(usage);
            System.exit(0);
        }


        // Draw the initial state of the grid
        Utility.drawGrid(grid);
        System.out.println("Steps done:" + doSimulation(grid, threshold));
        System.out.println("Moves: " + movesCompleted);
        // Draw the initial final of the grid
        Utility.drawGrid(grid);
    }
}
