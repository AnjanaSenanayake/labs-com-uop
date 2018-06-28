// UDPClient.java: A simple UDP client program.

import java.net.*;
import java.io.*;

public class TPClient {

    final static int PORT = 1234;
    final static int BUFSIZE = 1024;

    public static void main(String args[]) throws UnknownHostException {
        InetAddress aHost = InetAddress.getByName(args[0]);            
        byte[] dataArray = args[2].getBytes();
        DatagramPacket requestPacket = new DatagramPacket(
                dataArray, dataArray.length, aHost, Integer.parseInt(args[1]));
		
        try ( DatagramSocket aSocket = new DatagramSocket();) {
    		for (;;)
				aSocket.send(requestPacket);
        } catch (Exception e) {
            System.out.println("Socket: " + e.getMessage());
        } 
    }
}
