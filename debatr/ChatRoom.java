package debatr;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class ChatRoom {
	private String name;

	private ArrayList<Message> messages;
	private HashSet<PrintWriter> users;
	
	public ChatRoom() {
		this.messages = new ArrayList<Message>();
		users = new HashSet<PrintWriter>();
	}
	
	public ChatRoom(String name) {
		this.name = name;
		this.messages = new ArrayList<Message>();
		users = new HashSet<PrintWriter>();
	}



	public String getName() {
		return name;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public void newMessage(Message message) {
		messages.add(message);
	}
	
	public void editMessage(Message message, String newText) {
		int index = messages.indexOf(message);
		if (index > -1) {
			messages.remove(index);
		}
	}
	
	public void deleteMessage(Message message) {
		int index = messages.indexOf(message);
		if (index > -1) {
			messages.remove(index);
		}
	}
	
	public void addUser(PrintWriter name)
	{
		if(!(users.contains(name))){
			users.add(name); }
	}
	
	public void deleteUser(PrintWriter name){
		users.remove(name);
	}
	
	public HashSet<PrintWriter> getUsers()
	{
		return users; 
	}
	
	public boolean checkUser(PrintWriter name){
		if (users.contains(name)){
			return true; }
		
		return false;
	}
	
}
