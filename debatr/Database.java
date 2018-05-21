package debatr;

import java.util.ArrayList;

public class Database {
	private ArrayList<Topic> topics;
	private ArrayList<User> users;
	private ArrayList<ChatRoom> chatrooms;
	
	private static Database instance = new Database();
	
	private Database() {
		topics = new ArrayList<Topic>();
		users = new ArrayList<User>();
		chatrooms = new ArrayList<ChatRoom>();
	}
	
	public static Database getInstance() {
		return instance;
	}
	
	public ArrayList<Topic> getTopics() {
		return topics;
	}
	
	public void addTopic(Topic topic) {
		topics.add(topic);
	}
	
	public void removeTopic(Topic topic) {
		int index = topics.indexOf(topic);
		if (index > -1) {
			topics.remove(index);
		}
	}
	
	public void addUser(String name)
	{
		users.add( new User(name) );
	}
	
	public boolean checkAvailableUser(String name)
	{
		
		for(int x = 0; x<users.size();x++){
			User temp = users.get(x);
			String tempS = temp.getUsername();
			
			if(tempS.equals(name)){
				 return false; 
			}
		}
			return true;				
	}
	
	
	public void addChat(String name){
		chatrooms.add(new ChatRoom(name));
	}
	
	public boolean chatCreated (String name){
		
		for(int x = 0; x<chatrooms.size();x++){
			ChatRoom temp = chatrooms.get(x);
			String tempS = temp.getName();
			
			if(tempS.equals(name)){
				 return true; 
			}
		}
			return false;
	}
	
	public ChatRoom getChat (String name){
			
			for(int x = 0; x<chatrooms.size();x++){
				ChatRoom temp = chatrooms.get(x);
				String tempS = temp.getName();
				
				if(tempS.equals(name)){
					 return temp; 
				}
			}
				return null;
		}
	
}
