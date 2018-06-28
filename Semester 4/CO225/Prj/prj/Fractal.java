package prj;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Fractal
{ 	
	//Usage Details
	public static void printUsage()
	{
		System.out.println("Usage:");
    	System.out.println("java Fractal Mandelbrot x_start x_end y_start y_end iterations");
    	System.out.println("java Fractal Julia x y iterations");
    	System.exit(0);
	}
	
public static void main(String[]args)
{	 
	
	JFrame frame;
	if(args.length>=1)
	{	
		int iterations;
		double x_start;
		double x_end;
		double y_start;
		double y_end;
	    	if(args[0].equals("Mandelbrot"))
	    	{
	    		x_start=0;
	    		x_end=0;
	    		y_start=0;
	    		y_end=0;
	    		iterations=0;
	    		if(args.length==1)
	    		{
	    			x_start=-1;
	    			x_end=1;
	    			y_start=-1;
	    			y_end=1;
	    			iterations=1000;
	    		}
	    		else if(args.length==6)
	    		{
	    			x_start = Double.parseDouble(args[1]);
	    			x_end = Double.parseDouble(args[2]);
	    			y_start = Double.parseDouble(args[3]);
	    			y_end = Double.parseDouble(args[4]);
	    			iterations = Integer.parseInt(args[5]);
	    		}
	    		else
	    		{
	    			System.out.println("\nOops Error Encountered");
	    			printUsage();
	    		}
	    		Mandelbrot mandelbrot = new Mandelbrot(x_start,x_end,y_start,y_end,iterations);
    			frame = new JFrame("Mandelbrot Set");
    			frame.setContentPane(mandelbrot);
    			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			frame.setPreferredSize(new Dimension(800, 800));
    			frame.pack(); 
    			frame.setLocationRelativeTo(null); 
    			frame.setVisible(true); 
	    	}	
	    	else if(args[0].equals("Julia"))
	    	{
	    		
	    		x_start=0;
	    		y_start=0;
	    		iterations=0;
	    		if(args.length==1)
	    		{
	    			x_start=-0.4;
	    			y_start=0.6;
	    			iterations=1000;
	    		}
	    		else if(args.length==4)
	    		{	
	    			x_start = Double.parseDouble(args[1]);
	    			y_start = Double.parseDouble(args[2]);
	    			iterations = Integer.parseInt(args[3]);
	    		}
	    		else
	    		{
	    			System.out.println("\nOops Error Encountered");
	    			printUsage();
	    		}  		
	    		Julia julia = new Julia(x_start,y_start,iterations);
    			frame = new JFrame("Julia Set");
    			frame.setContentPane(julia);
    			frame.setPreferredSize(new Dimension(800,800));
    			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			frame.pack(); 
    			frame.setLocationRelativeTo(null); 
    			frame.setVisible(true); 
	    	}
	    	else
	    	{
	    		System.out.println("\nThe Passed Argument for the Fractal Type is not Identified");
		    	printUsage();
	    	}
	    }	
	    else
	    {
	    	System.out.println("\nNot enough arguments");
	    	printUsage();
	    }	
	}
}
