import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class CommServer extends Thread {  
    
    private Socket connectionSocket; 
    private Auth authInterface; 

    private int state; 
    private static int s0 = 0; 
    private static int s1 = 1;

    private String name; 

    public static String authOK     = "clear to send"; 
    public static String authFailed = "not registred"; 
    public static String posted     = "posted"; 


    public CommServer(Socket sock, Auth auth) { 
	this.connectionSocket = sock; 
	this.authInterface = auth;
	this.state = s0; 
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
		if(state == s0) { 
		    if(authInterface.isRegistered(line)) {
			state = s1; 
			name = authInterface.getName(line); 
			line = authOK; 
		    }
		    else line = authFailed; 
		}
		if(state == s1) {
		    line = name +": " + line; 				
		    System.out.println(line); 
		    line = posted;
		}
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
	

}