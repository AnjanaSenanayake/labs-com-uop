/*
 * Content Storing in a StringBuffer program
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class GetURL2 implements Runnable
{
	String urlString;
			
	public GetURL2(String url)
	{
		// System.setProperty("http.proxyHost", "cachex.pdn.ac.lk");
		// System.setProperty("http.proxyPort", "3128");
		urlString = url;
	}
	
	@Override
	public void run() 
	{
		URL url = null;
		try
		{
			System.setProperty("http.agent", "Chrome");
			url = new URL(urlString);
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())))
		{
			URLConnection connection = url.openConnection();

			for (int i = 0;; i++) 
			{
				String name = connection.getHeaderFieldKey(i);
				String value = connection.getHeaderField(i);

				if(name == null && value == null) 
				{
					break;
				}
				if(name == null) 
				{
					GetMultiURLStringBuffer.urlContentBuffer.append("\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\nServer HTTP version, Response code:"+value);
					GetMultiURLStringBuffer.urlContentBuffer.append("\n");
				} 
				else 
				{
					GetMultiURLStringBuffer.urlContentBuffer.append(name + "=" + value);
					GetMultiURLStringBuffer.urlContentBuffer.append("\n");
				}
			}
			
			String line;
			try 
			{
				while ((line = reader.readLine()) != null) 
				{
					GetMultiURLStringBuffer.urlContentBuffer.append(line);
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Target file is missing or corrupted");
			}
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
}

public class GetMultiURLStringBuffer 
{
	public static StringBuffer urlContentBuffer = new StringBuffer("");	
	public static void main(String[] args) 
	{
		Thread[] threads = new Thread[args.length];
				
		for(int i=0;i<args.length;i++)
		{
			threads[i] = new Thread(new GetURL2(args[i]));
		}
		
		for(int i=0;i<args.length;i++)
		{
			threads[i].start();
			try 
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		for(int i=0;i<args.length;i++)
		{
			try 
			{
				threads[i].join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}				
		System.out.println(urlContentBuffer.toString());
	}
}
