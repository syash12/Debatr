import java.util.ArrayList;

public class Database {
	private ArrayList<Topic> topics;
	private ArrayList<User> users;
	
	private static Database instance = new Database();
	
	private Database() {
		topics = new ArrayList<Topic>();
	}
	
	public static Database getInstance() {
		return instance;
	}
	
	public ArrayList<Topic> getTopics() {
		return topics;
	}
	
	public ArrayList<User> getUsers() {
		return users;
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
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		int index = users.indexOf(user);
		if (index > -1) {
			users.remove(index);
		}
	}
}
