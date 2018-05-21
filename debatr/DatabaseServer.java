package debatr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DatabaseServer {
	
	private static final int portnum = 9001; 					 		
   
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();		//list of all current listeners
    static Database db = Database.getInstance();									//database data object, contains users, chatrooms and messages

    
	public static void main(String args[]) throws IOException {
		
		 System.out.println("The chat server is running.");
		 InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress();
		System.out.println("Ip: " + ip);
			
	     ServerSocket listener = new ServerSocket(portnum);
	     
	     try {
	    	 while (true) {
	    		 new Handler(listener.accept()).start();
	         }
	    	 
	     } finally { listener.close(); }
	}
	
	
	//handler class, called from the listener loop in the main method. (start calls run)
	 private static class Handler extends Thread{
		
		private Socket mysocket;
		private BufferedReader in;
		private PrintWriter out;
		private String name = new String();
		private String chatroom = new String();
		private ChatRoom CR = new ChatRoom();
		private boolean newChat = false;
		private String input = null;
		
		protected Handler(Socket soc){
			this.mysocket = soc;
		}
		
		public void run(){	 
			
			try{					
					in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
					out = new PrintWriter(mysocket.getOutputStream(), true);

					//select a name that hasnt been selected yet
					while (true) {
	                   out.println("NAME ");
	                   name = in.readLine();	//waits for message from client
	                    if (name == null) {
	                        return;
	                    }
	                    synchronized (db) {
//	                        if (db.checkAvailableUser(name))
	                        {
	                            db.addUser(name);
	                            break;
	                        }
	                    }
	                }
					
					writers.add(out);
					
					//get chatroom name, check is exists: A) if it does, display current messages; B) if it doesnt add chatroom to database
					while(true){
		                
						//checks if user is coming from a different chat or just opened the application
						if(newChat == false){
							 input = in.readLine(); }
						else{
							newChat = false; }
						
						if (input == null) {
	                        return;
	                    }
		                
						//part A
		                if(input.startsWith("CHAT")){	                    	
	                    	chatroom = input.substring(5);
	                    	synchronized(db){
		                    	if(db.chatCreated(chatroom))
		                    	{
		                    		CR = db.getChat(chatroom);
		                    		CR.addUser(out);
		                    		ArrayList<Message> messages = new ArrayList<Message>(CR.getMessages());
		                    		
		                    		for(int x = 0; x<messages.size(); x++){			//writes previous messages to new chatters
		        	                    	Message output = messages.get(x);
		        	                        out.println("MESSAGE " + output.getOwner()+": "+output.getText());		        	                    
		                    		}
		                    	}	
		                    	//part B
		                    	else
		                    	{
		                    		db.addChat(chatroom);
		                    		CR = db.getChat(chatroom);//
		                    		CR.addUser(out);//
		                    	}
	                    	 }
	                      }
						
		                //if in existing chatroom and get new message, send to all current listeners in the chatroom/add to chatroom message list
		                while(true){
		                	
		                	input = in.readLine();	                	
		                	
		                	if (input == null) {
		                        return;
		                    }
		                	synchronized(db){
				                if(input.startsWith("MESSAGE")){
				                    String tempMessage = input.substring(8);
				                    
//				                    CR = db.getChat(chatroom);
				                    CR.newMessage(new Message(tempMessage, name));
//				                    CR.addUser(out);
					                for (PrintWriter writer : writers) {
					                	if(CR.checkUser(writer)){
					                		writer.println("MESSAGE " + name + ": "+ tempMessage); }
					                 }
				                 }
		                	}
			                //if the user goes into a newChat, set the flag and break from this while(true) loop
			                if(input.startsWith("CHAT ")){
			                	newChat = true;
			                	break;
			                }
			                    
			             }		
					
					}
			} 
			catch (IOException e) { e.printStackTrace(); }			
			finally {   											//closes socket and handles any clean up 
                try {                     
                    mysocket.close();            
                } 
                catch (IOException e) { e.printStackTrace(); }
			}
		}
		
	}//end of Handler class
	
}//end of DatabaseServer
