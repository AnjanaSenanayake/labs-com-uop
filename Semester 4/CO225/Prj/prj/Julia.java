package prj;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class Julia extends JPanel
{

	private static final long serialVersionUID = 1L;
	protected boolean bound;
	private double x_input;
	private double y_input;
	private double x;
	private double y;
	private double znow_x;
	private double znow_y;
	private int niter;
	private int iterations;
	private int[][][] pixelArray = new int[801][801][2];
	
	//Constructor for Julia
	public Julia(double x,double y,int iterations)
	{
		//setPreferredSize(new Dimension(800, 800));
		x_input=x;
		y_input=y;
		this.iterations=iterations;
		fillArray();
	}
	
	//Maps the x,y coordinates into complex number within the region of interest 
	public void complexValue(double i,double j)
	{
		znow_x=(((double)i*2)/800)-1;
		znow_y=(((double)j*2)/800)-1;
	}
	
	//Checks the complex coordinates are in the set of Julia
	public int boundCheck(int iterations)		
	{
		niter=0;
		x=x_input;
		y=y_input;
		while(iterations-->0)
		{
			double znext_x=(znow_x*znow_x)-(znow_y*znow_y)+x;
			double znext_y=(2*znow_x*znow_y)+y;
			znow_x=znext_x;
			znow_y=znext_y;
			niter++;
			if((Math.pow(znow_x,2)+Math.pow(znow_y,2))>4)
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
				pixelArray[i][j][0]=boundCheck(iterations);
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
		private static void printPoint(Graphics2D frame, Color c, double x, double y) 
		{
			frame.setColor(c);
			frame.draw(new Line2D.Double(x,y,x,y));
		}

		// call paintComponent from parent class
		public void paintComponent(Graphics g) 
		{
			long st = System.currentTimeMillis();
			// call paintComponent from parent class
			super.paintComponent(g);
			Thread t1 = new Thread(new Runnable() 
			{
				public void run() 
				{
					loopPrint(g,0,800,0,800);
				}
			});
			t1.start();
			//Unnecessary threads
			/*Thread t2 = new Thread(new Runnable() 
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
*/
			try{t1.join();}	catch (InterruptedException e1){}
			/*try{t2.join();}	catch (InterruptedException e2){}
			try{t3.join();}	catch (InterruptedException e3){}
			try{t4.join();}	catch (InterruptedException e4){}*/
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
