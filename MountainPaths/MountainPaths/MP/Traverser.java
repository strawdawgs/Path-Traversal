package MP;

import java.awt.Color;
import java.awt.Graphics;

/*
I got my DrawingPanel class from the Nifty assignments website,
it's at the bottom of the page in the sample code. Here's the link: 
http://nifty.stanford.edu/2016/franke-mountain-paths/
*/

public class Traverser {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		DrawingPanel panel = new DrawingPanel(844, 480);
		Graphics g = panel.getGraphics();
		PathMarker map = new PathMarker("Colorado_844x480.dat", 844, 480);

		int min = map.findMinValue();
		System.out.println("The min value in the map is: " + min);
		int max = map.findMaxValue();
		System.out.println("The max value in the map is: " + max);
		int minRow = map.minIndexOfCol(0);
		System.out.println("The row with the lowest value in the first column is: " + minRow);

		map.drawGrid(g, min, max);
		// Explanation of "indexOfMinPath" is above the function inside "PathMarker.java"
		PathMemory bestPath = map.indexOfMinPath(g);
		g.setColor(Color.GREEN);
		/* 
		"indexOfMiPath" returns the greediest path of least elevation from the greedy algorithm
		as a PathMemory object; the path is stored in the GridPair array. 
		*/
		for(int i=0; i<bestPath.getGridPairs().length; i++){
			g.fillRect(bestPath.getGridPairs()[i].getX(), bestPath.getGridPairs()[i].getY(), 1, 1);
		}
		System.out.println("The Lowest Elevation Change Path starts at row "
				+ bestPath.getRow() + " and has a total change of: " + bestPath.getWeight());

		/*
		To compare my result path you can create your own path and see what weight you get.
		One quick and easy path you can try is the path that starts at the minimum value (lowest elevation)
		in the first column; which I have saved as minRow.
		This could be a starting point for a hiker that doesn't want to climb to start.
		Below is the example:
		*/
		g.setColor(Color.BLUE);
		PathMemory minStartingPoint = map.drawMinPath(g, minRow, 0);
		for(int i=0; i<minStartingPoint.getGridPairs().length; i++){
			g.fillRect(minStartingPoint.getGridPairs()[i].getX(), minStartingPoint.getGridPairs()[i].getY(), 1, 1);
		}
		System.out.println("Elevation change from the minimum starting point "
				+ minRow + " has a total change of: " + minStartingPoint.getWeight());
		/*
		You can also see paths - and their weights - from every other starting point, 0-480, by replacing "minRow"
		with the column you want to start at.
		*/

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.print("Run time: " + totalTime + "ms");
	}
}