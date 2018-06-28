import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Auction 
{
	public static final int BASE_PORT = 500;     
    private static ServerSocket serverSocket; 
    @SuppressWarnings("unused")
	private static int socketNumber; 
    static LinkedHashMap<String,ArrayList<String>> stockDB;
	public Auction(int socket) throws IOException 
	{ 
		serverSocket = new ServerSocket(socket); 
		socketNumber = socket; 
	}
	public static void main(String[]args)
	{
		//CSV file will be read and put the relevant data into a Linkedhashmap
		stockDB = new LinkedHashMap<String,ArrayList<String>>();
		File file = new File("stocks.csv");
		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line=null;
			while((line = reader.readLine())!=null)
			{
				String[] token = line.split(",");
				String symbol = token[0];
				String name = token[1];
				String price = token[6];
				ArrayList<String> details = new ArrayList<String>();
				details.add(name);
				details.add(price);
				stockDB.put(symbol,details);
			}
			new AuctionGUI();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("CSV file is not found!");
		}
		catch(IOException e)
		{
			System.out.println("File is unable to read");
		}
		
		Auction newServer=null;
		try 
		{
			//Initialization of a new server
			newServer = new Auction(BASE_PORT);
			newServer.server_loop();
		} 
		catch (IOException e) 
		{
			System.out.println("Server Initialization Failed");
		}
	}
	
	//Creates a new socket for every incoming client
	public void server_loop() throws IOException 
	{ 
		while(true) 
		{ 
		    Socket socket = serverSocket.accept(); 	    
		    AuctionServer worker = new AuctionServer(socket); 
		    worker.start(); 
		}
	}
	
	//Returns firm name and stock price when the symbol is specified 
	public static String getFirmName(String symbol) throws NullPointerException
	{
		ArrayList<String> list =  stockDB.get(symbol);
		return list.get(0);
	}
	
	public static String getStockPrice(String symbol)
	{
		ArrayList<String> list =  stockDB.get(symbol);
		return list.get(1);
	}
}
