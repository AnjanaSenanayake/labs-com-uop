import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable
{
    protected static int serverPort = 8080;
    protected boolean isStopped = false;
    protected Thread runningThread = null;    
    protected Socket clientSocket = null;
    protected ServerSocket serverSocket = null;
    protected ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public ThreadPooledServer(int port)
    {
        serverPort = port;
    }

    public void run()
    {
        synchronized(this)
        {
            runningThread = Thread.currentThread();
        }
        serverSocketInitialize();
        while(!isStopped())
        {
            try 
            {
                clientSocket = serverSocket.accept();
            }
            catch (IOException e) 
            {
                if(isStopped()) 
                {
                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            threadPool.execute(new ThreadHandler(clientSocket));
        }
        threadPool.shutdown();
        System.out.println("Server Stopped.") ;
    }

    private synchronized boolean isStopped() 
    {
        return this.isStopped;
    }

    public synchronized void stop()
    {
        this.isStopped = true;
        try 
        {
            this.serverSocket.close();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void serverSocketInitialize() 
    {
        try 
        {
            this.serverSocket = new ServerSocket(serverPort);
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
    
    public static void main(String[]args)
    {
    	ThreadPooledServer server = new ThreadPooledServer(8080);
    	new Thread(server).start();
        System.out.println("Server started at PORT : "+serverPort);

    }
}

class ThreadHandler implements Runnable
{
    protected Socket clientSocket = null;

    public ThreadHandler(Socket clientSocket) 
    {
        this.clientSocket = clientSocket;
    }

    public void run() 
    {
        try 
        {
        	// Set up the read and write of the communication socket
            BufferedReader socketInput = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream socketOutput = new DataOutputStream (clientSocket.getOutputStream());
            
            // Get the initial request from the client
            String requestMessage = socketInput.readLine();
            System.out.println ("Request: " + requestMessage);
            StringTokenizer tokens = new StringTokenizer(requestMessage);

            // Check whether it is a GET request
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
                try 
                {
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
                    inFile.close();
                }
                catch(IOException e)
                {
                	socketOutput.writeBytes("Requested file couldn't be opened");
                }
                    		    
    		    while((requestMessage = socketInput.readLine()) != null)
    		    {
    		    	requestMessage = socketInput.readLine();
    		        System.out.println ("Request: " + requestMessage);
    		    }              
            }
            else
            {
                System.out.println ("Bad Request Message");
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}