import java.io.IOException;
class Main
{
    public static void main(String [] args) 
    {
	RAM ram = new RAM(1024); // 1GB ram

	String addr1 = "0";
	String addr2 = "122";
	int val = 100; 

	try 
	{
	    ram.lw(addr1);
	} 
	catch (IOException e) 
	{
	    System.out.println("Good should be exception " + e);	

	    try { 
		ram.sw(addr2, val);
		if(ram.lw(addr2) == val)
		{	
		    System.out.println("Good seems to work");
		    //System.out.println(ram.lw(addr2));
		}
	    } catch (IOException e1) {
		System.out.println("Bad " + e1);
		assert false; 
	    }
	    return;
	}
	assert false; 
    }
}

	    
