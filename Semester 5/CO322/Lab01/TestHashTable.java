import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestHashTable extends HashTableImp
{	
	public TestHashTable(int initialSize) 
	{
		super(initialSize);		
	} 

	public static void main(String[]args)
	{				
		String line="";
		int hashTableSize=0;		
		TestHashTable hashMap = null;
		
		//Extracting number of buckets from command line
		if(args[0].equals("-b"))
		{
			hashTableSize = Integer.valueOf(args[1]);
			hashMap = new TestHashTable(hashTableSize);
			
			if(args[2].equals("-i"))
			{
				//Reading from Text Files
				String fileName = args[3];
				try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
				{
					while((line = br.readLine())!=null)
					{
						//Converting all non-alphanumberic characters to whitespace						
						String text = line.replaceAll("\\W"," ");
						//split lines between whitespaces
						String tokens[] = text.split("\\s+");
						for(String s:tokens)
						{
							//insert strings to hash table
							hashMap.insert(s);								
						}
					}			
				}		
				catch (FileNotFoundException e)
				{
					System.out.println("File does not exists");
					System.exit(0);
				}
				catch (IOException e) 
				{
					System.out.println("File reading failed");
					System.exit(0);
				}		
				//Print overall information of contents in the hash table and it's distribution
				hashMap.getBucketSize(hashTableSize);
				System.out.println();
				
				try(Scanner ip = new Scanner(System.in))
				{				
					String key;
					do 
					{
						//Prompting user to enter a key to search from the hash table
						System.out.println("Enter a key to search: ");
						key = ip.nextLine();
						System.out.println("Value: "+hashMap.search(key));
					}
					while(!key.equals("q"));
				}				
			}
			else
			{
				printUsage();
			}
		}		
		else
		{
			printUsage();
		}
	}
	
	//How to use the program instructions for the user
	private static void printUsage()
	{
		System.out.println("Usage:for compiling via terminal");
		System.out.println("Insert-> Java TestHashTable -b [#buckets] -i [filename]");
		System.out.println("Ex-> Java TestHashTable -b 30 -i sample-text1.txt");	
		System.out.println("To quit from loop-> q");
	}
}
