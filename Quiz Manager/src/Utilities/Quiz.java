package Utilities;

import java.util.List;

public class Quiz {
	private String title;
	private List<Question> questions;
	private List<MCQQuestion> mcqQuestions;
	private Student student;

	private Integer id;
	
	//Getters and Setters.
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public List<MCQQuestion> getMcqQuestions() {
		return mcqQuestions;
	}
	public void setMcqQuestions(List<MCQQuestion> mcqQuestions) {
		this.mcqQuestions = mcqQuestions;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	//Getters and Setters.
	
	//Constructors
	public Quiz() {
		this.title = null;
		this.questions = null;
		this.mcqQuestions = null;
		this.student = null;
		this.id = null;
	}
	public Quiz(String title, List<Question> questions, List<MCQQuestion> mcqQuestions, Student student) {
		this.title = title;
		this.questions = questions;
		this.mcqQuestions = mcqQuestions ;
		this.student = student;
		this.id = null;
	}
	public Quiz(String title, List<Question> questions, List<MCQQuestion> mcqQuestions, Student student, Integer id) {
		this.title = title;
		this.questions = questions;
		this.mcqQuestions = mcqQuestions ;
		this.student = student;
		this.id = id;
	}

}
