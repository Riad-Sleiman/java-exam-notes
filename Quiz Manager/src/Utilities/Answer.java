package Utilities;

public class Answer {
	
	private String text;
	
	private Integer id;
	
	//Constructors
	public Answer(String text) {
		this.text = text;
		this.id = null;
	}
	
	public Answer(String text, Integer id) {
		this.text = text;
		this.id = id;
	}
	
	public Answer(Integer id) {
		this.text = null;
		this.id = id;
	}

	//Getters and Setters.
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
