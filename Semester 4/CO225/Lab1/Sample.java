import java.util.Random; // for random 
import java.io.*; // for file IO

class Sample { 
    /* contains some sample code which might be useful for the lab */

    public static void main(String [] args) { 

	/* Method 1: the Math class provides a static method called random 
	 * to generate a random number between 0 and 1. 
	 * You can multiply with a suitable MAX to get an 
	 * integer within a range 
	 */
	System.out.println("Random value between 0-1 = " + Math.random()); 

	int MAX = 10;
	int rand = (int)(Math.random() * 10); // cast to int
	System.out.printf("Random integer between 0-%d = %d\n", MAX, rand); 

	/* Method 2: create Random object. When creating an object you can 
	 * give a seed (of type long) or not. 
	 * see https://docs.oracle.com/javase/7/docs/api/java/util/Random.html 
	 * The object provides a method called nextInt(int n) which returns 
	 * a integer between 0 and n (inclusive). 
	 */
	Random randomGen = new Random(); 
	System.out.println("Using Random. Rand number = " + 
			   randomGen.nextInt(MAX)); 

	/**************************************************************/ 
	/* Reading a file. 
	 */
	BufferedReader descriptor = null;  
	String [] names = new String[62]; // 61 names 
	try { 
	    descriptor = new BufferedReader(
			    new FileReader("co225-classlist.txt"));

	    for(int i=0; i < names.length; i++) // assume file has 61 lines 
		names[i] = descriptor.readLine(); 

	    descriptor.close(); 

	} catch(IOException e) { 
	    // IOException is more generic (FileNotFound is a type of a 
	    // IOException, so catching only that is sufficient 
	    System.out.println("Bad things happen, what do you do!" + e);
	    return; 
	}

	// we will run only if no exceptions were thrown 
	for(int i=0; i < names.length; i++) 
	    System.out.println(names[i]); 

    }
}
