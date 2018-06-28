import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea clientText;
	JTextField msg;
	JPanel panel;
	JScrollPane clientScroll;
	JButton send = new JButton("Send");
	 JButton closeButton = new JButton("Close");
	Container cont;
	
	public ClientGUI() 
	{
		cont = getContentPane();
	    setSize(250,500);
	    setTitle("Client");
	    panel = new JPanel();
	    msg = new JTextField(20);
	    panel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    clientText = new JTextArea(10,20);
	    clientScroll = new JScrollPane(clientText);
	    panel.add(clientText);
	    panel.add(msg);
	    panel.add(send);
	    panel.add(closeButton);
	    cont.add(panel);
	    
	    send.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	        Socket s = null;
	        try 
	        {
	        	s = new Socket("localhost",400);
	        } 
	        catch (UnknownHostException e1) 
	        {
	        	clientText.setText("UnknownHost: + localhost");
	            s = null;
	        } 
	        catch (IOException e2) 
	        {
	            clientText.setText("Cant connect to the server.Make sure it is running");
	            s = null;
	        }
	        if (s == null) 
	        {
	        	System.exit(-1);
	        }
	        BufferedReader in = null;
	        PrintWriter out = null;
	        try 
	        {
	        	in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
	            String line = null;
	            
	         //   msg.setText("");
	            line = in.readLine();
	            clientText.append("\nServer says:" +line);
	            //clientText.setText(line);
	          //  System.out.println(clientText.getText().toString());
	            out.println(msg.getText());
	            out.flush();
	         } 
	         catch (IOException e3) 
	         {
	        	 clientText.setText("Exception during communication.Server probably closed connection.");
	         } 
	         finally 
	         {
	        	 try
	        	 {
	        		 out.close();
	                 in.close();
	                 s.close();
	        	 } 
	        	 catch (Exception e4) 
	        	 {
	        		 e4.printStackTrace();
	             }
	         }
	        }
	    });
	    closeButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
	}
	 public static void main(String args[]) 
	 {
	        ClientGUI clientFrame = new ClientGUI();
	        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        clientFrame.setVisible(true);
	 }
}
