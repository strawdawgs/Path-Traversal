package MP;
import java.io.IOException;
import java.io.PrintWriter;

public class topMapGen {

	public int row = 100;
	public int col = 100;
	public int[][] map;
	
	public static void main(String[] args) {
		topMapGen oIt = new topMapGen();
		oIt.topMap();
		oIt.writeFile();
	}
	
	public void topMap()
	{
		map = new int[322][1224];
		for (int i = 0; i < map.length; i++) {
		    for (int j = 0; j < map[i].length; j++) {
		        map[i][j] = (int)(getRandNum(1000, 1400));
		    }
		}
	}
	
	public double getRandNum(int min, int max)
	{
		return Math.floor(Math.random() * (max - min)) + min;
	}
	
	public void writeFile()
	{
		try
		{
			PrintWriter writer = new PrintWriter("etopoL.txt", "UTF-8");
			for (int i = 0; i < map.length; i++) {
			    for (int j = 0; j < map[i].length; j++) {
			        writer.write(map[i][j] + " ");
			    }
			    writer.write(System.getProperty("line.separator"));
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("Unhandled Exception while writing to the new file.");
			e.printStackTrace();
			return;
		}
		
	}

}
