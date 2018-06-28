import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixMultiplication {
	static int[][] matrixA;
	static int[][] matrixB;
	static int[][] matrixtransB;
	static int[][] matrixtransA;
	static int[][] resultMatrix;
	static int[][] resulttransMatrix;
	static int matArowsize;
	static int matAcolsize;
	static int matBrowsize;
	static int matBcolsize;

	public static void main(String[] args) {

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] lists = new ArrayList[2];
		lists[0] = new ArrayList<Integer>();
		lists[1] = new ArrayList<Integer>();
		int rows;
		int[] rowsize = new int[2];
		for (int i = 0; i < 2; i++) {
			rows = 0;
			String line;
			try (BufferedReader reader = new BufferedReader(new FileReader(new File(args[i])))) {
				while ((line = reader.readLine()) != null) {
					String[] tokens = line.split(" ");
					for (int j = 0; j < tokens.length; j++) {
						int n = Integer.parseInt(tokens[j]);
						lists[i].add(n);
					}
					rows++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			rowsize[i] = rows;
		}
		matArowsize = rowsize[0];
		matAcolsize = lists[0].size() / rowsize[0];
		matBrowsize = rowsize[1];
		matBcolsize = lists[1].size() / rowsize[1];

		matrixA = new int[matArowsize][matAcolsize];
		//matrixtransA = new int[matAcolsize][matArowsize];
		int countA = 0;
		for (int i = 0; i < matArowsize; i++) {
			for (int j = 0; j < matAcolsize; j++) {
				matrixA[i][j] = lists[0].get(countA);
				countA++;
			}
		}
		
		matrixB = new int[matBrowsize][matBcolsize];
		matrixtransB = new int[matBcolsize][matBrowsize];
		int countB = 0;
		for(int i = 0; i < matBrowsize; i++) {
			for (int j = 0; j < matBcolsize; j++) {
				matrixB[i][j] = lists[1].get(countB);
				countB++;
			}
		}
		
		for(int i=0;i<matBcolsize;i++) {
			for (int j=0;j<matBrowsize;j++) {
				matrixtransB[i][j] = matrixB[j][i];
			}
		}
		

		resultMatrix = new int[matArowsize][matBcolsize];
		
		Scanner ip = new Scanner(System.in);
		int threadNumber = ip.nextInt();
		
		Thread[] threads = new Thread[threadNumber];
		
		long st = System.currentTimeMillis();

		for (int i = 0; i < threadNumber; i++) {
			threads[i] = new Thread(new MatrixMultiplier(
					new Multiply((matArowsize / threadNumber) * i, (matArowsize / threadNumber) * (i + 1))));
			threads[i].start();
		}
			
		for (int i = 0; i < threadNumber; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		
		long ed = System.currentTimeMillis();

		File file = new File("test.txt");
		try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < matArowsize; i++) {
				for (int j = 0; j < matBcolsize; j++) {
					br.write(resultMatrix[i][j] + " ");
				}
				br.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Unable to Write the File ");
		}

		System.out.println("Elapsed Time: " + (ed - st) + "ms");
	}
}

class Multiply extends MatrixMultiplication {
	private int min;
	private int max;

	public Multiply(int min, int max) {
		this.min = min;
		this.max = max;
	}

	// Matrix Multiplication Function
	public void multiplyMatrix() {
		for (int x = min; x < max; x++) {
			for (int y = 0; y < matBcolsize; y++) {
				for (int count = 0; count < matAcolsize; count++) {
					resultMatrix[x][y] += matrixA[x][count] * matrixtransB[y][count];
				}
			}
		}
	}
}

class MatrixMultiplier implements Runnable 
{
	private final Multiply mul;

	public MatrixMultiplier(Multiply mul) {
		this.mul = mul;
	}

	public void run() {
		mul.multiplyMatrix();
	}
}