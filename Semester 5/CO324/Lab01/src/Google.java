import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Google 
{
	public static void main(String[]args)
	{
		//System.setProperty("http.proxyHost", "cachex.pdn.ac.lk");
		//System.setProperty("http.proxyPort", "3128");
		
		String google = null;
		URL newURL;
		URLConnection newConnection = null;
		
		String[] query = args[0].split("");
		
		String encodedQuery="";

		try 
		{
	    	for (int i = 0; i < query.length; i++)
	    	{	             
	    		if(i == query.length-1)
	    		{
	    			encodedQuery += URLEncoder.encode(query[i], "UTF-8");
	    		}
	    		else
	    		{
	    			encodedQuery += (URLEncoder.encode(query[i], "UTF-8") + "+");
	    		}
	    	}
	    	google = "https://www.google.lk/search?q="+encodedQuery;
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	    System.out.println(encodedQuery);
		
		try 
		{
			System.setProperty("http.agent", "Chrome");
			newURL = new URL(google);
			newConnection = newURL.openConnection();
		}
		catch (MalformedURLException e1) 
		{
			e1.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		//newConnection.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		
		try(BufferedReader searchResult = new BufferedReader(new InputStreamReader(newConnection.getInputStream())))
		{
			String line;
			try 
			{
				while ((line = searchResult.readLine()) != null) 
				{
					System.out.println(line);
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Target file is missing or corrupted");
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
