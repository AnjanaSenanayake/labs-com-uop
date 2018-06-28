package prj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mandelbrot extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static boolean bound;
	private double x_start;
	private double x_end;
	private double y_start;
	private double y_end;
	private double x;
	private double y;
	private int iterations;
	private int niter;
	private double[][][] pixelArray = new double[801][801][2];
	JFrame frame;

	// Constructor Mandelbrot
	public Mandelbrot(double x_s, double x_e, double y_s, double y_e, int iterations) 
	{
		this.x_start = x_s;
		this.x_end = x_e;
		this.y_start = y_s;
		this.y_end = y_e;
		this.iterations = iterations;
		fillArray();
	}

	// Maps the x,y coordinates into complex number within the region of interest
	public void complexValue(int i, int j) {
		x = (((double) i * (x_end - x_start)) / 800) - Math.abs(x_start);
		y = (((double) j * (y_end - y_start)) / 800) - Math.abs(y_start);
	}

	// Checks the complex coordinates are in the set of Mandelbrot
	public int boundCheck(double x,double y,int iterations) 
	{
		niter = 0;
		double znow_x = 0;
		double znow_y = 0;
		while (iterations-- > 0)
		{
			double znext_x = (znow_x * znow_x) - (znow_y * znow_y) + x;
			double znext_y = (2 * znow_x * znow_y) + y;
			znow_x = znext_x;
			znow_y = znext_y;
			niter++;
			if ((Math.pow(znow_x, 2) + Math.pow(znow_y, 2)) > 4) 
			{
				return 0;
			}
		}
		return 1;
	}
	public synchronized void iterateArray(int xi,int yi,int xj,int yj)
	{
		for(int i=xi;i<yi;i++)
		{
			for(int j=xj;j<yj;j++)
			{	
				complexValue(i,j);
				pixelArray[i][j][0]=boundCheck(x,y,iterations);
				pixelArray[i][j][1]=niter;
			}
		}	
	}
	
	public void fillArray()
	{
		Thread k1 = new Thread(new Runnable() 
		{
			public void run() 
			{
				iterateArray(0,400,0,400);
			}
		});
		k1.start();
		Thread k2 = new Thread(new Runnable() 
		{
			public void run() 
			{
				iterateArray(400,800,0,400);
			}
		});
		k2.start();
		Thread k3 = new Thread(new Runnable() 
		{
			public void run() 
			{
				iterateArray(0,400,400,800);
			}
		});
		k3.start();
		Thread k4 = new Thread(new Runnable() 
		{
			public void run() 
			{
				iterateArray(400,800,400,800);
			}
		});
		k4.start();
		
		try{k1.join();}	catch (InterruptedException e1){}
		try{k2.join();}	catch (InterruptedException e2){}
		try{k3.join();}	catch (InterruptedException e3){}
		try{k4.join();}	catch (InterruptedException e4){}
	}

	// print points in the given coordinates with a given color
	private synchronized static void printPoint(Graphics2D frame, Color c, double x, double y) 
	{
		frame.setColor(c);
		frame.draw(new Line2D.Double(x, y, x, y));
	}

	// call paintComponent from parent class
	public void paintComponent(Graphics g) 
	{
		long st = System.currentTimeMillis();
		// call paintComponent from parent cl
		super.paintComponent(g);
		loopPrint(g,0,800,0,800);
		/*Thread t1 = new Thread(new Runnable() 
		{
			public void run() 
			{
				
			}
		});
		t1.start();
		*/
		//These threads are removed since it seems unnecessary
	/*	Thread t2 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g, 400,800,0,400);
			}
		});
		t2.start();
		Thread t3 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g, 0,400,400,800);
			}
		});
		t3.start();
		Thread t4 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g,400,800,400,800);
			}
		});
		t4.start();
		Thread t5 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g, 0,200,400,800);
			}
		});
		t5.start();
		Thread t6 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g, 200,400,400,800);
			}
		});
		t6.start();
		Thread t7 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g,400,600,400,800);
			}
		});
		t7.start();
		Thread t8 = new Thread(new Runnable() 
		{
			public void run() 
			{
				loopPrint(g,600,800,400,800);
			}
		});
		t8.start();
*/
		/*try{t1.join();}	catch (InterruptedException e1){}
		try{t2.join();}	catch (InterruptedException e2){}
		try{t3.join();}	catch (InterruptedException e3){}
		try{t4.join();}	catch (InterruptedException e4){}
		try{t5.join();}	catch (InterruptedException e5){}
		try{t6.join();}	catch (InterruptedException e6){}
		try{t7.join();}	catch (InterruptedException e7){}
		try{t8.join();}	catch (InterruptedException e8){}*/
		long ed = System.currentTimeMillis();
		System.out.println("Elapsed time: "+(ed-st)+"ms");
	}

	public void loopPrint(Graphics g, int xi, int yi, int xj, int yj) 
	{
		for (int i = xi; i <= yi; i++) 
		{
			for (int j = xj; j <= yj; j++) 
			{
				if(pixelArray[i][j][0]==0)
				{
					Color color = Color.getHSBColor((float)(pixelArray[i][j][1])*20.0f/(float) iterations,1.0f,1.0f);
					printPoint((Graphics2D) g, color, i, j);
				}
				else if(pixelArray[i][j][0]==1)
				{
					printPoint((Graphics2D) g,Color.BLACK,i,j);
				}
			}
		}
	}
}
