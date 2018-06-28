// UDPServer.java: A simple UDP server program.

import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer {
    final static int PORT = 1234;
    final static int BUFSIZE = 1024;
    
    
    public static void main(String args[]) {

        while (!Thread.interrupted())
        try (DatagramSocket aSocket = new DatagramSocket(PORT);) {
        	InetAddress aHost = InetAddress.getByName(args[0]);
            byte[] bufferRecieve = new byte[BUFSIZE];          
            DatagramPacket recievePacket = new DatagramPacket(bufferRecieve, BUFSIZE);
			
			aSocket.receive(recievePacket);
			DatagramPacket replyPacket = new DatagramPacket(
                    recievePacket.getData(), recievePacket.getLength(), 
                    recievePacket.getAddress(), recievePacket.getPort());

            aSocket.send(replyPacket);           
        } catch (Exception e) {            
            System.out.println("Socket: " + e.getMessage());            
        } 
    }
}
