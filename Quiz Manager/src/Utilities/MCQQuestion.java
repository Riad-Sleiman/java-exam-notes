package Utilities;

import java.util.ArrayList;
import java.util.List;

public class MCQQuestion extends Question {
	private List<MCQChoice> choicesList;
	
	private Integer id;
	
	//Getters and Setters. 
	public List<MCQChoice> getChoicesList() {
		return choicesList;
	}
	public void setChoicesList(List<MCQChoice> choicesList) {
		this.choicesList = choicesList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//Getters and Setters.
	
	//Constructors
	public MCQQuestion() {
		super();
		this.choicesList = new ArrayList<MCQChoice>();
	}
	public MCQQuestion(String question, String topic, int difficulty, List<MCQChoice> choicesList) {
		super(question, topic, difficulty,null);
		this.choicesList = choicesList;
		this.id = null;
	}
	public MCQQuestion(String question, String topic, int difficulty, List<MCQChoice> choicesList, Integer id) {
		super(question, topic, difficulty,null);
		this.choicesList = choicesList;
		this.id = id;
	}
	public MCQQuestion(Integer id) {
		this.choicesList = null;
		this.id = id;
	}
	//Constructors

	
}
