package debatr;
import java.util.Date;

public class Message {
	private String text;
	private String owner;
	private Date time;
	
	public Message(String message, String owner) {
		this.text = message;
		this.owner  = owner;
		this.time = new Date();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOwner() {
		return owner;
	}

	public Date getTime() {
		return time;
	}
	
	// No setters for owner and time since they should never change
}
