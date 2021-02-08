package Utilities;

public class Question {
	
	private String question;
	private String[] topic;
	private Integer difficulty;
	private Answer answer;
	
	private Integer id;
	
	//Getters and Setters.
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String[] getTopic() {
		return topic;
	}
	public String getTopicForDB() {
		String topics = "";
		for (int i = 0 ; i < topic.length; i++) {
			topics += " "+topic[i];
		}
		return topics;
	}
	public void setTopic(String[] topic) {
		this.topic = topic;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int dificulty) {	//CHECK IF VALUE IS INTEGER!!!
		this.difficulty = dificulty;
	}
	public Answer getAnswers() {
		return answer;
	}
	public void setAnswers(Answer answer) {
		this.answer = answer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//Getters and Setters.
	
	//Constructors
	public Question() {
		this.question = null;
		this.topic = null;
		this.difficulty = -1;
		this.answer = null;
		this.id = null;
	}
	public Question(String question, String topic, int difficulty, Answer answer) {
		this.question = question;
		this.topic = topic.split(" ");
		this.difficulty = difficulty;
		this.answer = answer;
		this.id = null;
	}
	public Question(String question, String topic, int difficulty, Answer answer, Integer id) {
		this.question = question;
		this.topic = topic.split(" ");
		this.difficulty = difficulty;
		this.answer = answer;
		this.id = id;
	}
	public Question(Integer id) {
		this.id = id;
		this.topic = null;
		this.difficulty = -1;
		this.answer = null;
	}
	//Constructors

}
