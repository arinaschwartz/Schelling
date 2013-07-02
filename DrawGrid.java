public class DrawGrid {
    public static void main(String[] args) {
	if (args.length != 1) {
	    System.out.println("usage: java DrawGrid <grid file name>");
	    System.exit(0);
	}

	int[][] grid = Utility.readPopulation(args[0]);
	Utility.drawGrid(grid);
    }
}