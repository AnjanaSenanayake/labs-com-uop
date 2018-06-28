import java.util.EmptyStackException;
import java.util.Stack;

public class TowerOfHanoi 
{
	static int stepNumber = 0; 
	
	public static int legalMove(Stack<Integer> A, Stack<Integer> B) 
	{
		int a, b;
		try 
		{
			a = Integer.parseInt(A.peek().toString());
		} 
		catch (EmptyStackException e) 
		{
			a = 0;
		}
		try 
		{
			b = Integer.parseInt(B.peek().toString());
		}
		catch (EmptyStackException e) 
		{
			b = 0;
		}
		if (a == b)
			return 0;
		if (a == 0) // If peg A is empty, then pop from B and push the disk onto A
		{
			A.push(B.pop());
			return 2; // Return 2 as move occurred from B to A
		}
		else if (b == 0) // If peg B is empty, then pop from A and push the disk onto B
		{
			B.push(A.pop());
			return 1; // Return 1 since move occurred from A to B
		}

		if (a < b) 
		{
			B.push(A.pop());
			return 1; // Return 1 since move occurred from A to B
		} 
		else if (a > b) // value of top disk of peg A is greater than the value of topmost disk of peg B
		{
			A.push(B.pop());
			return 2; // Return 2 since move occurred from B to A
		}
		return -1;
	}
	
	private static void printInstruction(Stack<Integer> A,Stack<Integer> B,String a,String b)
	{
		System.out.println((++stepNumber) + ". Move disk "+B.peek().toString()+" from "+a+" --> "+b);
	}
	
	private static void printUsage()
	{
		System.out.println("Usage:-------------------------------------------------------------");
		System.out.println("Java TowerOfHanoi [numberofdisks] [source] [auxilary] [destination]");
		System.out.println("Example:Java TowerOfHanoi 3 A B C");
	}

	public static void main(String[] args) 
	{		 
        
        int status = 0; 
        try 
        {
        	if(args.length!=4)
        	{
        		printUsage();
        		System.exit(1);
        	}
        	Stack<Integer> Source = new Stack<Integer>();        	
        	Stack<Integer> Auxilary = new Stack<Integer>(); 
        	Stack<Integer> Destination = new Stack<Integer>(); 
          
        	int n=0;
        	String src="";
        	String aux="";
        	String dest="";
        	try 
        	{
        		n = Integer.parseInt(args[0]);
        		src = args[1];
        		aux = args[2];
        		dest = args[3];
        	}
        	catch(NumberFormatException e)
        	{
        		System.out.println("Input value error!");
        		printUsage();
        		System.exit(1);
        	}
        
        	if(n<=0) 
        	{ 
        		System.out.println("Number of disks can not be smaller than 0!"); 
        		System.exit(1); 
        	} 
        	for(int i=n; i>0; i--) 
        		Source.push(i);       
        	int m = n%2; 
        	do 
        	{        	
        		if(m==1) 
        		{ 
        			if((status = legalMove(Source,Destination)) == 1)
        				printInstruction(Source, Destination, src, dest);        				 
        			else if (status == 2)
        				printInstruction(Destination, Source, dest, src);        				        		
                 
        			if((status = legalMove(Source,Auxilary)) == 1)
        				printInstruction(Source, Auxilary, src, aux);        			
        			else if(status == 2)
        				printInstruction(Auxilary, Source, aux, src);        				        				 
        			else  
        				break; 
        		}              
        		else 
        		{ 
        			if((status = legalMove(Source,Auxilary)) == 1)   
        				printInstruction(Source, Auxilary, src, aux);        				        				
        			else if (status == 2)
        				printInstruction(Auxilary, Source, aux, src);        				        				 
                 
        			if((status = legalMove(Source,Destination)) == 1)
        				printInstruction(Source, Destination, src, dest);        				        		
        			else if(status == 2)
        				printInstruction(Destination, Source, dest, src);        				        				                 
        		} 
             
        		if((status = legalMove(Auxilary,Destination)) == 1)  
        			printInstruction(Auxilary, Destination, aux, dest);        			        			 
        		else if(status == 2)
        			printInstruction(Destination, Auxilary, dest, aux);        			
        	}
        	while(Destination.size()!=n);        	
        }          
        catch (Exception e)
        {
        	e.printStackTrace();
        }        
   } 
}
