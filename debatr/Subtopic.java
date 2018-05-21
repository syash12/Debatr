package debatr;
import java.util.ArrayList;

public class Subtopic {
	private String name;
	private ArrayList<ChatRoom> chatrooms;
	
	public Subtopic(String name) {
		this.name = name;
		this.chatrooms = new ArrayList<ChatRoom>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<ChatRoom> getChatrooms() {
		return chatrooms;
	}
	
	public void addChatRoom(ChatRoom chatroom) {
		chatrooms.add(chatroom);
	}
	
	public void removeChatRoom(ChatRoom chatroom) {
		int index  = chatrooms.indexOf(chatroom);
		if (index > -1) {
			chatrooms.remove(index);
		}
	}
}
