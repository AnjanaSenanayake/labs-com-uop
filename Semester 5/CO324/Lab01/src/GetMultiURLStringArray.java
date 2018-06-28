/*
 * Content Storing in a String Array program
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class GetURL3 implements Runnable
{
	String urlString;
			
	public GetURL3(String url)
	{
		// System.setProperty("http.proxyHost", "cachex.pdn.ac.lk");
		// System.setProperty("http.proxyPort", "3128");
		urlString = url;
	}
	
	@Override
	public synchronized void run() 
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
					GetMultiURLStringArray.text = GetMultiURLStringArray.text + "\n\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\nServer HTTP version, Response code:"+value;
					GetMultiURLStringArray.text = GetMultiURLStringArray.text + "\n";
				} 
				else 
				{
					GetMultiURLStringArray.text = GetMultiURLStringArray.text + name + "=" + value;
					GetMultiURLStringArray.text = GetMultiURLStringArray.text + "\n";
				}
			}
			
			String line;
			try 
			{
				while ((line = reader.readLine()) != null) 
				{
					//System.out.println(line);
					GetMultiURLStringArray.text = GetMultiURLStringArray.text + line;
					GetMultiURLStringArray.urlContentArray[0] = GetMultiURLStringArray.text;
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

public class GetMultiURLStringArray
{
	public static String[] urlContentArray = new String[2];	
	public static String text = "";
	public static void main(String[] args) 
	{
		Thread[] threads = new Thread[args.length];
				
		for(int i=0;i<args.length;i++)
		{
			threads[i] = new Thread(new GetURL3(args[i]));
		}
		
		for(int i=0;i<args.length;i++)
		{
			threads[i].start();
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
		System.out.println(urlContentArray[0]);
	}
}
