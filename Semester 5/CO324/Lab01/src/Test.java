

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Test 
{
	public static void main(String[] args) throws IOException 
	{
		String urlName = "https://www.google.lk/search?q="+URLEncoder.encode("dog","UTF-8");
		URLConnection newConnection = null;
		
		URL url = null;
		try 
		{
			url = new URL(urlName);
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		newConnection = url.openConnection();
		newConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		
		while ((line = in.readLine()) != null)
		{  
		   System.out.println(line);
		}
	}
}
