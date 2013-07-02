/*  CS121 A'11
 *  HW2: Schelling Model of Housing Segregation
 *
 *  Utility functions
 */


import java.util.*;
import java.io.File;

public class Utility {
    static char[] shortNames = {'R', 'B', 'O'};
    public static String[] longNames = {"RED", "BLUE", "OPEN"};

    /* readPopulation: read the population from the file f.  Do NOT set the seed  */
    public static int[][] readPopulation(String f) {
	return readPopulation(f, false);
    }

    /* readPopulation: read the population from the file f.  
     *   set the seed from the file if setSeed is true
     */
    public static int[][] readPopulation(String f, boolean setSeed) {
	Scanner scanner = null;
	int nv = 0;
	int[][] grid = null;
	
	// attempt to load file                                                            
	try {
	    scanner = new Scanner(new File(f), "UTF-8");
	} catch (NullPointerException e) {
	    System.out.print("Bad file name.");
	    System.exit(0);
	} catch (java.io.FileNotFoundException e) {
	    System.out.println("File " + f + " not found.");
	    System.exit(0);
	}

	int lineNum = 0;
	String line = "";

	try {
	    line = scanner.nextLine();
	    nv = Integer.parseInt(line);
	    grid = new int[nv][nv];
	    lineNum++;

	    for (int i = 0; i < nv; i++) {
		if (!scanner.hasNextLine()) {
		    System.out.println("Format error on line #" + lineNum);
		    System.out.println("  Rows missing");
		}
		line = scanner.nextLine();
		String[] entries = line.split(" ");
		for (int j = 0; j < entries.length; j++) {
		    if (entries[j].equals("R"))
			grid[lineNum-1][j] = Schelling.RED;
		    else if (entries[j].equals("B"))
			grid[lineNum-1][j] = Schelling.BLUE;
		    else if (entries[j].equals("O"))
			grid[lineNum-1][j] = Schelling.OPEN;
		    else {
			System.out.println("Format error on line #" + lineNum);
			System.out.println("line:" + line + ":");
			System.exit(0);
		    }
		}
		lineNum++;
	    }
	    if (scanner.hasNextLine() && setSeed) {
		line = scanner.nextLine();		
		long seed = Long.parseLong(line);
		LocalRandom.initRandom(seed);
	    } else if (setSeed) {
		System.out.println("Format error on line #" + lineNum);
		System.out.println("Seed missing");
	    }

	} catch (Exception e) {
	    System.out.println("Format error on line #" + lineNum);
	    System.out.println("line:" + line + ":");
	    e.printStackTrace();
	    System.exit(0);
	}

	return grid;

    }


    /* generatePopulation: generate a population of size grizSizexgridSize with pctRed red homeowners,
     *   pctBlue blue homeowners, and the remaining homes open.
     */
    public static int[][] generatePopulation(int gridSize, double pctRed, double pctBlue) {
	int[][] grid = new int[gridSize][gridSize];
	int redCnt = 0;
	int numRed = gridSize*gridSize; // (int) (gridSize*gridSize*redLimit);
	int numBlue = gridSize*gridSize;  // (int) (gridSize*gridSize*blueLimit);
	int blueCnt = 0;
	double redLimit = pctRed;
	double blueLimit = pctRed + pctBlue;

	for (int i = 0; i < gridSize; i++) {
	    for (int j = 0; j < gridSize; j++) {
		double r = Math.random();
		if (r < redLimit) {
		    if (redCnt < numRed) {
			grid[i][j] = Schelling.RED;
			redCnt++;
		    } else {
			grid[i][j] = Schelling.OPEN;
		    }
		}
		else if (r < blueLimit) {
		    if (blueCnt < numBlue) {
			grid[i][j] = Schelling.BLUE;
			blueCnt++;
		    } else {
			grid[i][j] = Schelling.OPEN;
		    }
		} else
		    grid[i][j] = Schelling.OPEN;
	    }
	}

	return grid;

    }


    /* printPopulation: output the population without the seed */
    public static void printPopulation(int[][] grid)  {
	printPopulation(grid, false);
    }


    /* printPopulation: output the population.  Print the seed if printSeed is true */
    public static void printPopulation(int[][] grid, boolean printSeed)  {
	System.out.println(grid.length);
	for (int i = 0; i < grid.length; i++) {
	    for (int j = 0; j < grid[0].length; j++) {
		System.out.printf("%c ", shortNames[grid[i][j]]);
	    }
	    System.out.println();
	}
	if (printSeed) {
	    System.out.println(LocalRandom.getSeed());
	} else {
	    System.out.println();
	    System.out.println();
	}
    }


    /* copyPopulation: make a copy of a population */
    public static int[][] copyPopulation(int[][] grid) {
	int[][] rv = new int[grid.length][grid[0].length];
	for (int i = 0; i < grid.length; i++) {	
	    for (int j = 0; j < grid[0].length; j++) {
		rv[i][j] = grid[i][j];
	    }
	}

	return rv;
    }


    /* matchingPopulations: returns true if the populations match and
     *   false otherwise.  If the populations do not match, unmatchedX
     *   and unmatchedY are set to indices of the first location at
     *   which they do not match.
     */
    public static int unmatchedX = -1;
    public static int unmatchedY = -1;
    public static boolean matchingPopulations(int[][] grid0, int[][] grid1) {
	for (int i = 0; i < grid0.length; i++) {	
	    for (int j = 0; j < grid0[0].length; j++) {
		if (grid0[i][j] != grid1[i][j]) {
		    unmatchedX = i;
		    unmatchedY = j;
		    return false;
		}
	    }
	}
	return true;
    }


    /* drawGrid: draw the grid on the screen */
    public static void drawGrid(int[][] grid) {
	StdDraw.setXscale(0.0, grid.length);
	StdDraw.setYscale(0.0, grid[0].length);


	StdDraw.clear();

	double y = grid.length-0.5;
	double r = 0.5;
	for (int i = 0; i < grid.length; i++) {
	    double x = 0.5;
	    for (int j = 0; j < grid[0].length; j++) {
		if (grid[i][j] == Schelling.OPEN) {
		    StdDraw.setPenColor(StdDraw.BLACK);
		} else if (grid[i][j] == Schelling.RED) {
		    StdDraw.setPenColor(StdDraw.RED);
		} else {
		    StdDraw.setPenColor(StdDraw.BLUE);
		}
		StdDraw.filledSquare(x,y,r);
		x = x + 2*r;
	    }
	    y = y - 2*r;
	}
        StdDraw.show(2000);
    }
}



