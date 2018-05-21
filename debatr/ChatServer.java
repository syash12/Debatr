package debatr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class ChatServer {

	private static final int portnum = 9001; 					 //need to figure out what portnum to use
	private static final String hostname = "127.0.0.1";			 //need to figure out what the hostname will be
    
    private static HashSet<String> names = new HashSet<String>();    
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    static Database db = Database.getInstance();
    
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(portnum);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    } 
    
	protected static int getPortnum(){
		return portnum;
	}
	
	protected static String getHostname(){
		return hostname; 
	}
    
    private static class Handler extends Thread {
        private String tempS;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        
        public Handler(Socket socket) {
            this.socket = socket;
        }

        
        public void run() {
            try {

                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request an unused name
                while (true) {
                    out.println("SUBMITNAME");
                    tempS = in.readLine();
                    if (tempS == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(tempS)) {
                            names.add(tempS);
//                            db.addUser(tempS);                           
                            break;
                        }
                    }
                }

                out.println("NAMEACCEPTED");
                writers.add(out);

                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + tempS + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (tempS != null) {
                    names.remove(tempS);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}