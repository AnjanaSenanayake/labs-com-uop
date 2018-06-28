import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class WebServer
{
    static final int PORT = 8080;
    Socket socket;
    
    public static void main(String[] args) throws IOException 
    {   
    	ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Server started at PORT : " + PORT);
        
       while(true)
       {
    	   Socket socket = ss.accept();
    	   
    	   // Set up the read and write of the communication socket
           BufferedReader socketInput = new BufferedReader (new InputStreamReader(socket.getInputStream()));
           DataOutputStream socketOutput = new DataOutputStream (socket.getOutputStream());

    	   // Get the initial request from the client
           String requestMessage = socketInput.readLine();
           System.out.println ("Request: " + requestMessage);
           StringTokenizer tokens = null;
           try 
           {
        	   tokens = new StringTokenizer(requestMessage);   
        	   
        	   if (tokens.nextToken().equals("GET"))
               {
                   String fileName = tokens.nextToken();

                   if (fileName.startsWith("/") == true)
                   {
                       fileName = fileName.substring(1);
                   }

                   // Read the file and convert into byte array
                   File file = new File(fileName);
                   int lengthInBytes = (int) file.length();
                   FileInputStream inFile = new FileInputStream(fileName);
                   byte[] fileInBytes = new byte[lengthInBytes];
                   inFile.read(fileInBytes);

                   String contentType = Files.probeContentType(file.toPath());

                   // Sending a reply
                   socketOutput.writeBytes("HTTP/1.0 200 Document Follows\r\n");            
                   socketOutput.writeBytes("Content-Type: " +contentType+ "\r\n");
                   socketOutput.writeBytes("Content-Length: " +lengthInBytes+ "\r\n");
                   socketOutput.writeBytes("\r\n");
                   socketOutput.write(fileInBytes, 0, lengthInBytes);
       		    
	       		   while ((requestMessage = socketInput.readLine()) != null)
	       		   {
	       		    	requestMessage = socketInput.readLine();
	       		        System.out.println ("Request: " + requestMessage);
	       		        break;
	       		   }		 
       		    
               	}
               	else
               	{
                   System.out.println ("Bad Request Message");
               	}
           }
           catch(NullPointerException e)
           {
        	   System.out.println("Request body is null");
           }
       }
    }
}