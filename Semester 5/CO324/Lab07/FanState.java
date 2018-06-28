import java.util.Scanner;

public class FanState 
{
    final static int PATTERN = 0xa;
    enum State {OFF,MED,HI};
    static State currentState = State.OFF;
    
    static void sendCountsUp(int count) 
    {
        System.err.println("Sending " + count +" incrementing counts.");
    }
    
    static void sendCountsDown(int count) 
    {
        System.err.println("Sending " + count +" decrementing counts.");
    }
    
    public static void main(String[]args) throws Exception 
    {	
		Scanner ip = new Scanner(System.in);
    	
    	while(true)
    	{
    		System.out.println("Input a state[OFF,MED,HI]: ");
    		String input = ip.nextLine();
            switch (input) 
            {
                case "OFF":
                	if(currentState == State.MED)
                	{
                		if(currentState.ordinal()<State.OFF.ordinal())
                		{
                			sendCountsUp(1);
                		}                		
                		if(currentState.ordinal()>State.OFF.ordinal())
                		{
                			sendCountsDown(1);
                		}
                	}
                	else if(currentState == State.HI)
                	{            
                		if(currentState.ordinal()<State.OFF.ordinal())
                		{
                			sendCountsUp(2);
                		}
                		if(currentState.ordinal()>State.OFF.ordinal())
                		{
                			sendCountsDown(2);
                		}
                	}
                	currentState = State.OFF;
                	break;
                
                case "MED":
                	if(currentState == State.OFF)
                	{
                		if(currentState.ordinal()<State.MED.ordinal())
                		{
                			sendCountsUp(1);
                		}
                		if(currentState.ordinal()>State.MED.ordinal())
                		{
                			sendCountsDown(1);
                		}
                	}
                	else if(currentState == State.HI)
                	{
                		if(currentState.ordinal()<State.MED.ordinal())
                		{
                			sendCountsUp(1);
                		}
                		if(currentState.ordinal()>State.MED.ordinal())
                		{
                			sendCountsDown(1);
                		}
                	}
                	currentState = State.MED;                    
                	break;
                	
                case "HI":
                	if(currentState == State.OFF)
                	{
                		if(currentState.ordinal()<State.HI.ordinal())
                		{
                			sendCountsUp(2);
                		}
                		if(currentState.ordinal()>State.HI.ordinal())
                		{
                			sendCountsDown(2);
                		}
                	}
                	else if(currentState == State.MED)
                	{
                		if(currentState.ordinal()<State.HI.ordinal())
                		{
                			sendCountsUp(1);
                		}
                		if(currentState.ordinal()>State.HI.ordinal())
                		{
                			sendCountsDown(1);
                		}
                	}
                	currentState = State.HI;
                	break;  
                default:
                	System.out.println("State is undefined");
            }
    	}
    }
}