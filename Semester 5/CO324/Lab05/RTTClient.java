// UDPClient.java: A simple UDP client program.

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class RTTClient {

    static char charArray[] = new char[65536];
    //final static String data = "Hello there!";
    final static int PORT = 1234;
    final static int BUFSIZE = 1024;
    static long start;
    static long delta;
    static long rtt=0;

    public static void main(String args[]) {

        try ( DatagramSocket aSocket = new DatagramSocket();) {
            InetAddress aHost = InetAddress.getByName(args[0]);
            
            Arrays.fill(charArray,'a');
            String data = new String(charArray);
            byte[] dataArray = data.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(dataArray, dataArray.length, aHost, PORT);
            for(int i=0;i<10000;i++)
            {
            	start=0;
            	start = System.currentTimeMillis();
                aSocket.send(requestPacket);

                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket recievePacket = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(recievePacket);
                delta = System.currentTimeMillis() - start;
                rtt+=delta;
            }
            System.out.println("RTT: "+(double)rtt/10000d+"ms");
        } catch (Exception e) {
            System.out.println("Socket: " + e.getMessage());
        } 
    }
}
