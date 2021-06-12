package MP;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class PathMarker {
	// 2d array to store topograph values
	public int[][] grid;
	public PathMarker(String filename, int cols, int rows)
	{
		//              y     x
		grid = new int [rows][cols];
		Scanner scan;
		try
		{
			scan = new Scanner(new File(filename));
			for(int i = 0; i < rows; ++i)
			{
				for(int j = 0; j < cols; ++j)
				{
					if(scan.hasNextInt())
						grid[i][j] = scan.nextInt();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	// return the row number
	public int getRows()
	{
		return grid.length;
	}
	// return the column number
	public int getCols()
	{
		return grid[0].length;
	}
	// find the minimum value in the grid
	public int findMinValue()
	{
		int min = grid[0][0];
		for(int i = 0; i < grid.length; ++i)
		{
			for(int j = 0; j < grid[0].length; ++j)
			{
				if(grid[i][j] < min)
					min = grid[i][j];
			}
		}
		return min;
	}
	// returns the max value in the grid
	public int findMaxValue()
	{
		int max = grid[0][0];
		for(int i = 0; i < grid.length; ++i)
		{
			for(int j = 0; j < grid[0].length; ++j)
			{
				if(grid[i][j] > max)
					max = grid[i][j];
			}
		}
		return max;
	}
	// find the minimum value (elevation) in the given column
	public int minIndexOfCol(int col)
	{
		int min = grid[0][col];
		int minRow = 0;
		for(int i = 1; i < grid.length; ++i)
		{
			if(grid[i][col] < min)
			{
				min = grid[i][col];
				minRow = i;
			}
		}
		return minRow;
	}
	// to draw the topographic map from the data
	public void drawGrid(Graphics g, int min, int max)
	{
		// used for finding the gray scale values
		double scale = 255.0 / (max - min);
		// scale the raw grid numbers to legible gray scale values
		for(int i = 0; i < grid.length; ++i)
		{
			for(int j = 0; j < grid[0].length; ++j)
			{
				int value = (int) ((grid[i][j] - min) * scale);
				g.setColor(new Color(value, value, value));
				g.fillRect(j, i, 1, 1);
			}
		}
	}

	/*
	This is where the "Greedy Walk" takes place, the next best step is chosen
	by looking at each of the 3 adjacent values - right, up-right and down-right.
    The next step is chosen based on which one of the three values is closest in
	elevation to the current step.
	*/
	public PathMemory drawMinPath(Graphics g, int currRow, int bestWeight)
	{
		int totalWeight = 0;
		// custom class "GridPair" to store a pair of values (x and y) in an array
		GridPair[] gridPairs = new GridPair[grid[0].length-1];
		for(int i = 0; i < grid[0].length-1; i++)
		{
			// create a new GridPair array for memoization
			gridPairs[i] = new GridPair(i, currRow);

			/* 
			the next pixel to the immediate right
			- grid[currRow][i+1];
			the diagonal pixel to the right and up
			- grid[currRow-1][i+1];
			the diagonal pixel to the right and down
			- grid[currRow+1][i+1];
			*/

			// variables to determine the difference between next steps
			int right = Math.abs(grid[currRow][i] - grid[currRow][i+1]);
			int upRight = Integer.MAX_VALUE;
			int downRight = Integer.MAX_VALUE;
			
			// check to see if we're on the top most part of the graph
			if(currRow != 0)
				upRight = Math.abs(grid[currRow][i] - grid[currRow-1][i+1]);
			// check to see if we are on the bottom most part of the graph
			if(currRow != grid.length - 1)
				downRight = Math.abs(grid[currRow][i] - grid[currRow+1][i+1]);
			
			// the next step variable
			int nextStep = right;
			if(upRight < right && upRight < downRight){
				nextStep = upRight;
				currRow--;
			}
			else if(downRight < right && downRight < upRight){
				nextStep = downRight;
				currRow++;
			}
			
			// add the total to change
			totalWeight += nextStep;

			// if the totalWeight is ever higher than any of our previously memoized weights,
			// break the loop and don't continue with the current path
			if(bestWeight != 0 && totalWeight > bestWeight)
				break;
		}
		// save the PathMemory object with the row, the gridpair array and the weight
		return new PathMemory(currRow, gridPairs, totalWeight);
	}
	
	/* 
	"indexOfMinPath" goes through every row in the grid and finds the "best" least 
	resistant path in the grid using a greedy algorithm
	*/
	public PathMemory indexOfMinPath(Graphics g)
	{
		// find the first minimum path from the top of the grid (row = 0)
		PathMemory resultPath = drawMinPath(g, 0, 0);
		for(int i = 1; i < grid.length; ++i)
		{
			// find all other minimum paths in the graph and compare them to the previous path
			PathMemory comPath = drawMinPath(g, i, resultPath.getWeight());
			if(comPath.getWeight() < resultPath.getWeight())
			{
				resultPath = comPath;
				// "i" is the row where the path started
				resultPath.setRow(i);
			}
		}
		return resultPath;
	}
}

class GridPair {
    private int x;
    private int y;
    public GridPair(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){return this.x;}
    public int getY(){return this.y;}
}

class PathMemory {
	private int row;
	private GridPair[] gp;
	private int weight;
	public PathMemory(int row, GridPair[] gp, int weight){
		this.row = row;
		this.gp = gp;
		this.weight = weight;
	}
	public int getRow(){return this.row;}
	public GridPair[] getGridPairs(){return this.gp;}
	public int getWeight(){return this.weight;}
	public void setRow(int row){this.row = row;}
	public void setGridPairs(GridPair[] gp){this.gp = gp;}
	public void setWeight(int weight){this.weight = weight;}
}