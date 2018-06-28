import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetURL 
{
	public static void main(String[] args) 
	{
		//System.setProperty("http.proxyHost", "cachex.pdn.ac.lk");
		//System.setProperty("http.proxyPort", "3128");
		
		String urlString = args[0];
		URL url = null;
		
		try 
		{
			url = new URL(urlString);
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) 
		{

			URLConnection connection = url.openConnection();
		    
		      for (int i=0; ; i++) 
		      {
		            String name = connection.getHeaderFieldKey(i);
		            String value = connection.getHeaderField(i);
		    
		            if (name == null && value == null)
		            {
		              break;         
		            }

		            if (name == null)
		            {
		              System.out.println("Server HTTP version, Response code:");
		              System.out.println(value);
		              System.out.print("\n");
		            }
		            else
		            {
		              System.out.println(name + "=" + value);
		            }
		      }
		      
		    System.out.println();  
			String line;
			try 
			{
				while ((line = reader.readLine()) != null) 
				{
					System.out.println(line);
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Target file is missing or corrupted");
			}
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
}
