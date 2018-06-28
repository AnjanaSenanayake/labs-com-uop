import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MatrixGenerator 
{
	public static void main(String[]args)
	{
		Scanner ip = new Scanner(System.in);
		
		int rows = ip.nextInt();
		int cols = ip.nextInt();
		
		int[][] matrix = new int[rows][cols];
		
		Random rand = new Random();
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				matrix[i][j] = rand.nextInt(100); 
			}
		}
		
		try(BufferedWriter br = new BufferedWriter(new FileWriter(new File(args[0]))))
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
				{
					br.write(matrix[i][j]+" "); 
				}
				br.write("\n");
			}
		} 
		catch (IOException e) {
			System.out.println("Unable to Write the File ");
		}
	}
}
