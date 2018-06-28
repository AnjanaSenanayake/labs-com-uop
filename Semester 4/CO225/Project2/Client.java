import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Client extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea clientText;
    JTextField msg;
    JPanel clip;
    JScrollPane clientScroll;
    JButton closeButton = new JButton("Close");
    JButton send = new JButton("Send");
    JLabel label1 = new JLabel("Echo Client - Multithreaded");
    Container cont;
    Client() {
        cont = getContentPane();
        setSize(250, 500);
        setTitle("GUI Client");
        clip = new JPanel();
        msg = new JTextField(20);
        clip.setLayout(new FlowLayout(FlowLayout.CENTER));
        clientText = new JTextArea(20, 20);
        clientScroll = new JScrollPane(clientText);
        clip.add(label1);
        clip.add(clientText);
        clip.add(msg);
        clip.add(send);
        clip.add(closeButton);
        cont.add(clip);
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Socket s = null;
                try {
                    s = new Socket("localhost", 12111);
                } catch (UnknownHostException uhe) {
                    clientText.setText("UnknownHost: + localhost");
                    s = null;
                } catch (IOException ioe) {
                    clientText.setText("Cant connect to server at 12111.Make sure it is running");
                    s = null;
                }
                if (s == null) {
                    System.exit(-1);
                }
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                    out.println(msg.getText());
                    out.flush();
                    String temp = clientText.getText();
                    if (temp.equalsIgnoreCase("quit")) {
                        System.exit(0);
                    }
                    msg.setText("");
                    clientText.append("\nServer says:" + in.readLine());
                } catch (IOException ioe) {
                    clientText.setText("Exception during communication.Server probably closed connection.");
                } finally {
                    try {
                        out.close();
                        in.close();
                        s.close();
                    } catch (Exception es) {
                        es.printStackTrace();
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
    public static void main(String args[]) {
        Client clientFrame = new Client();
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(true);
    }
}