package hello;

public class Greeting {
	private final long id;
	private final String content;
	
	public Greeting(long _id, String _content) {
		id = _id;
		content = _content;
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}