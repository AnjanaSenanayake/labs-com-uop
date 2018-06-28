import java.net.*;

import java.io.*;

public class EchoServer {

    ServerSocket m_ServerSocket;
    public EchoServer() {
        try {
            m_ServerSocket = new ServerSocket(12111);
        } catch (IOException ioe) {
            System.out.println("could not create server socket at 12111 Quitting");
            System.exit(-1);
        }
        System.out.println("Listening for clients on 12111....");
        int id = 0;
        while (true) {
            try {
                Socket clientSocket = m_ServerSocket.accept();
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
                cliThread.start();
            } catch (IOException ioe) {
                System.out.println("Exception encountered on accept.Ignoring.StackTrace :");
                ioe.printStackTrace();
            }
        }
    }
    public static void main(String args[]) {
        new EchoServer();
    }
    class ClientServiceThread extends Thread {
        Socket m_clientSocket;
        int m_clientID = -1;
        boolean m_bRunThread = true;

        ClientServiceThread(Socket s, int clientID) {
            m_clientSocket = s;
            m_clientID = clientID;
        }
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            System.out.println("Accepted Client :ID - " + m_clientID + " :Address - " + m_clientSocket.getInetAddress().getHostName());
            try {
                in = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(m_clientSocket.getOutputStream()));
                while (m_bRunThread) {
                    String clientCommand = in.readLine();
                    System.out.println("Client says :" + clientCommand);
                    if (clientCommand.equalsIgnoreCase("quit")) {
                        m_bRunThread = false;
                        System.out.println("Stopping client thread for client :" + m_clientID);
                        System.exit(0);
                    } else {
                        out.println(clientCommand);
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    m_clientSocket.close();
                    System.out.println("...STOPPED");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}