package debatr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DatabaseClient {
	
	private static final int portnum = 9001; 					 		//need to figure out what portnum to use
	private static final String hostname = "10.145.121.245";			 //need to figure out what the hostname will be
	
	
	BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Computer Client");
    JTextField textField = new JTextField(55);
    JTextArea messageArea = new JTextArea(8, 40);
    boolean flag = true;
    
    public DatabaseClient() {

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
        	//when you press enter, sends text to server then clears text area
            public void actionPerformed(ActionEvent e) {
                out.println("MESSAGE " + textField.getText());
                textField.setText("");
            }
        });
        
        
    }
    
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    private String getChat() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a chatroom:",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    private void run() throws IOException {

        // Make connection and initialize streams
    	Socket mysocket = new Socket(hostname, portnum);
        in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
        out = new PrintWriter(mysocket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
        	String line = in.readLine();
        	
            if (line.startsWith("NAME")) {
                out.println(getName()); 
                out.println("CHAT " + getChat());
                flag = false;
            } 
            
            
            else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }
    
	public static void main(String[] args) throws Exception {
	    	
	        DatabaseClient client = new DatabaseClient();
	        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        client.frame.setVisible(true);
	        client.run();	        
	        
	        InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			System.out.println("Ip: " + ip);
		}
	
}
