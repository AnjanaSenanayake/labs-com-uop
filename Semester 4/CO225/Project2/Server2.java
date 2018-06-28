import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server2 implements Runnable { 
    
    public static final int BASE_PORT = 1250;     
    
    private static ServerSocket serverSocket; 
    @SuppressWarnings("unused")
	private int socketNumber; 

    private Socket connectionSocket; 
        
    public Server2(int socket) throws IOException { 
	serverSocket = new ServerSocket(socket); 
	socketNumber = socket; 
    }

    public Server2(Socket socket) { 
	this.connectionSocket = socket; 
    }

    public void server_loop() throws IOException { 
	while(true) { 
	    Socket socket = serverSocket.accept(); 	    
	    Thread worker = new Thread(new Server2(socket)); 
	    worker.start(); 	    
	}
    }

    public void run() { 
	try { 
	    BufferedReader in = new 
		BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
	    PrintWriter out = new 
		PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream()));	 
	    String line; 
	    for(line = in.readLine(); 
		line != null && !line.equals("quit"); 
		line = in.readLine()) { 
		System.out.println(line); 
		out.print(line + "\n"); 
		out.flush();	    
	    } // for loop
	}// try 
	catch (IOException e) { 
	    System.out.println(e); 
	} 
	try { 	    
	    this.connectionSocket.close(); 
	} catch(IOException e) {}
	
    }

    public static void main(String [] args) throws IOException { 
	Server2 server = new Server2(BASE_PORT);
	server.server_loop(); 
    }
}
	    
	