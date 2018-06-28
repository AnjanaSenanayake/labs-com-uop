import java.util.Random;

public class MonteCarloSimulation 
{
	static int successCount=0;
	
	public static void main(String[]args)
	{
		int runtimes = 100000;
		int threadNumber = 4;
		
		Thread[] threads = new Thread[threadNumber];
		
		long time0 = System.currentTimeMillis();
		
		for(int i=0;i<threadNumber;i++)
		{
			threads[i] = new Thread(new MonteCarlo(new Mazes(runtimes/threadNumber)));
			threads[i].start();
		}
		
		for (int i = 0; i < threadNumber; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		
		long time1 = System.currentTimeMillis();
		
//		generateMaze(maze);
//		showMaze(maze);
//		System.out.println("--------------------------------");
//		resetMaze();
//		showMaze(maze);
		
		float probability = (float)(successCount/(float)runtimes)*(100);
		System.out.printf("Probability: "+probability+" in "+(time1-time0)+"ms");	
	}
}
	
	class Mazes extends MonteCarloSimulation
	{
		int[][] maze = new int[20][20];
		int runtimes;
		
		public Mazes(int runtimes)
		{
			this.runtimes = runtimes;
		}
		
		private void generateMaze(int[][] maze)
		{
			Random rand = new Random();
			for(int i=0;i<20;i++)
			{
				for(int j=0;j<20;j++)
				{
					maze[i][j] = rand.nextInt(2);
				}
			}
			maze[0][0] = 0;
			maze[19][19] = 0;
		}
		
		private void showMaze(int[][] maze)
		{
			for(int i=0; i < maze.length; i++) 
			{
			    System.out.printf("{");
			    for(int j=0; j < maze[i].length; j++)
				System.out.printf("%d%s", maze[i][j],j != maze[i].length - 1 ? ", ": " }\n");
			}
		}
		
		public boolean findPath(int x, int y, int X, int Y) 
	    {
	    	if(x==X && y==Y) 
	    	{
	    		return true;
	    	}
	    		
	    	maze[x][y]=1;
	    	if(y-1>=0 && maze[x][y-1]==0)
	    	{
	    		
	    		if(findPath(x,y-1,X,Y))
	    			return true;
	    	}
	    	if(x-1>=0 && maze[x-1][y]==0)
	    	{
	    		
	    		if(findPath(x-1,y,X,Y))
	    			return true;    		
	    	}
	    	if(y+1<maze.length && maze[x][y+1]==0)
	    	{
	    		
	    		if(findPath(x,y+1,X,Y))	
	    			return true;
	    	}
	    	if(x+1<maze.length && maze[x+1][y]==0)
	    	{
	    		
	    		if(findPath(x+1,y,X,Y))
	    			return true;
	    	}
	    	return false;
	    }
		
		public void resetMaze()
		{
			for(int i=0;i<20;i++)
			{
				for(int j=0;j<20;j++)
				{
					maze[i][j] = 0;
				}
			}
		}
		
		public void simulation()
		{
			int count=0;
			for(int i=0;i<runtimes;i++)
			{
				generateMaze(maze);
				if(findPath(0, 0, 19, 19))
				{
					count++;
				}
			//	resetMaze();
			}
			updateCounter(count);
		}
		
		private synchronized static void updateCounter(int count)
		{
			successCount+=count;
		}
	}
	
	class MonteCarlo implements Runnable
	{
		private Mazes maze;
		
		public MonteCarlo(Mazes maze)
		{
			this.maze = maze;
		}
		
		@Override
		public void run() 
		{
			maze.simulation();
		}	
	}