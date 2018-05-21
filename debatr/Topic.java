package debatr;
import java.util.ArrayList;

public class Topic {
	private String name;
	private ArrayList<Subtopic> subtopics;
	
	public Topic(String name) {
		this.name = name;
		this.subtopics = new ArrayList<Subtopic>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Subtopic> getSubtopics() {
		return subtopics;
	}
	
	public void addSubtopic(Subtopic subtopic) {
		subtopics.add(subtopic);
	}
	
	public void removeSubtopic(Subtopic subtopic) {
		int index = subtopics.indexOf(subtopic);
		if (index > -1) {
			subtopics.remove(index);
		}
	}
}
