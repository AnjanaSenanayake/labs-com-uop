import java.util.Stack;

public class Maze 
{
    private int [][] maze;
    Stack<Cell> route = new Stack<>();

    public Maze() {
	int [][] tmp = {{0,0,1,1,1},
			{1,0,1,1,0},
			{0,0,0,1,1},
			{0,0,0,0,1},
			{1,1,1,0,0}};
	maze = tmp;
    }

    public void show() 
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
    	System.out.println(x+" "+y);
    	if(x==X && y==Y) 
    	{
    		return true;
    	}
    		
    	maze[x][y]=1;
    	if(y-1>=0 && maze[x][y-1]==0)
    	{
    		
    		if(findPath(x,y-1,X,Y))
    		{
    			route.push(new Cell(x,y-1));
    			return true;
    		}
    	}
    	if(x-1>=0 && maze[x-1][y]==0)
    	{
    		
    		if(findPath(x-1,y,X,Y)) 
    		{
    			route.push(new Cell(x-1,y));
    			return true;
    		}
    	}
    	if(y+1<maze.length && maze[x][y+1]==0)
    	{
    		
    		if(findPath(x,y+1,X,Y))
    		{
    			route.push(new Cell(x,y+1));
    			return true;
    		}
    	}
    	if(x+1<maze.length && maze[x+1][y]==0)
    	{
    		
    		if(findPath(x+1,y,X,Y))
    		{
    			route.push(new Cell(x+1,y));
    			return true;
    		}
    	}
    	return false;
    }

    public void showPath() 
    {
    	while(!route.empty())
    	{
    		Cell c = route.pop();
    		System.out.println(c.x+" "+c.y);
    	}
    }
    
    
    public static void main(String [] args) 
    {
		Maze m = new Maze();
		m.show();
		System.out.println();
		System.out.print("All Visited Cells:\n");
		if(m.findPath(0,0, 3, 0)) 
		{
		    System.out.println("\nThere is a path");
		    System.out.println("Actual Path:");
		    m.showPath();
		}
		else
		    System.out.println("There is no path");
	  
	}
}

class Cell
{
	int x;
	int y;
	public Cell(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
}
