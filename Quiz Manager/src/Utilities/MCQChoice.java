package Utilities;

public class MCQChoice {
	
	private String choice;
	private boolean valid;
	
	private Integer id;
	
	//Getters and Setters.
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//Getters and Setters.
	
	//Constructors
	public MCQChoice() {
		this.choice = null;
		this.valid = false;
	}
	public MCQChoice(String choice, boolean valid) {
		this.choice = choice;
		this.valid = valid;
		this.setId(null);
	}
	
	public MCQChoice(String choice, boolean valid, Integer id) {
		this.choice = choice;
		this.valid = valid;
		this.setId(id);
	}
	
	public MCQChoice(Integer id) {
		this.choice = null;
		this.valid = false;
		this.setId(id);
	}
	//Constructors
	
	
}
