import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuctionServer extends Thread 
{
	private Socket connectionSocket;
	private String symbol;
	private String bidderName;
	private String price;
	static Map<String, ArrayList<String>> stockUpdate;
	
	public AuctionServer(Socket sock) 
	{
		this.connectionSocket = sock;
	}
	
	//Checks whether a string has a numerical value
	public boolean isNumeric(String str)
	{
		try 
		{
			Float.parseFloat(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	public void run() 
	{
		try 
		{
			//Data exchange between server and client
			//Server will ask specific details from every connecting client
			//if client replied with 'quit' command then server will terminate the connection with that client
			//if a specified stock has previous bidders then server will show the bidding history
			//and the current stock price will be shown
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream()));
			stockUpdate = new HashMap<String, ArrayList<String>>();
			out.print("Client Name: ");
			out.flush();
			bidderName = in.readLine();
			try
			{
				if (bidderName.equalsIgnoreCase("quit"))
					in.close();;
				System.out.println(bidderName);
			}
			catch(Exception e)
			{System.out.println("Please use quit command to exit in future");}
			do {
				
				int repeat;
				do {
					out.print("Firm Symbol: ");
					out.flush();
					symbol = in.readLine();
					try{if (symbol.equalsIgnoreCase("quit"))in.close();;}
					catch(Exception e) 
					{
						System.out.println("Please use quit command to exit in future");
					}
					System.out.println(symbol);
					if (Auction.stockDB.get(symbol) != null) 
					{
						repeat = 1;
						ArrayList<String> details = stockUpdate.get(symbol);
						if(stockUpdate.get(symbol)!=null)
						{	
						out.println("Previous Bid Offers for "+symbol+":");
						out.println("---------------------------");
						out.println();
						}
						try {
						for(String detail:details)
						{
							out.println(detail);
							out.flush();
						}
						out.println();
						out.flush();
						}
						catch(NullPointerException e) {};
						String[] tokens = Auction.stockDB.get(symbol).toString().split(",");
						out.println("Current Stock Price of " + symbol + ": $"+ tokens[1].substring(0, tokens[1].length() - 1));
					} 
					else 
					{
						out.println("-1");
						repeat = -1;
					}
				} while (repeat != 1);
				out.flush();
				do
				{
					out.print("Submit your bid: ");
					out.flush();
					price = in.readLine();
					if(price.equalsIgnoreCase("quit"))
					{
						in.close();
					}
					else if(!isNumeric(price))
					{
						out.println("Invalid bid, Please Check Again!");
					}
				}	
				while(!isNumeric(price));
				try{if (price.equalsIgnoreCase("quit"))break;}
				catch(Exception e) 
				{
					System.out.println("Please use quit command to exit in future");
				}
				Auction.stockDB.get(symbol).set(1, price);
				System.out.println(price);
				out.print("Your bid is submitted\n");
				out.flush();
				
				String detail=bidderName+" offered "+price;
				
				if(stockUpdate.containsKey(symbol))
					stockUpdate.get(symbol).add(detail);
				else
				{
					ArrayList<String> info = new ArrayList<String>();
					info.add(detail);
					stockUpdate.put(symbol,info);
				}
			} while ((line = in.readLine()) != null && !line.equals("quit"));
		}
		catch (IOException e) 
		{
			System.out.println("Server Couldn't be started");
		}
		try 
		{
			this.connectionSocket.close();
		}
		catch (IOException e){}
	}
}
