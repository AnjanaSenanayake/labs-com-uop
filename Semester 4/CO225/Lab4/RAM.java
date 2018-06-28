import java.io.IOException;
import java.util.HashMap;

public class RAM implements MemInterface
{
	int size;
	int value;
	
	HashMap<String,Integer> mem = new HashMap<String,Integer>(size);
	
	public RAM(int size)
	{
		this.size = size;
	}
	
	@Override
	public int lw(String address) throws IOException
	{
		try
		{
			this.value = mem.get(address);
		}
		catch(NullPointerException e)
		{
			throw new IOException();
		}
		return value;
	}

	@Override
	public void sw(String address, int val) throws IOException 
	{
		mem.put(address,val);
		
	}

	@Override
	public int cacheHits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int cacheMisses() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
