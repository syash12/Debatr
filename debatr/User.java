package debatr;

public class User {
	private String id;
	private String username;
	
	public User(String user) {
		this.username = user;
	}
	
	public User(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
