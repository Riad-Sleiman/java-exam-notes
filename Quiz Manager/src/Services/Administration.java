package Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utilities.Answer;
import Utilities.MCQChoice;
import Utilities.MCQQuestion;
import Utilities.Question;
import Utilities.Quiz;
import Utilities.Student;

public class Administration {
	DAO dao = new DAO("jdbc:postgresql://localhost:5432/postgres","postgres","123");
	
	public List<Question> GetAllQuestions() {
		List<Question> q = new ArrayList<Question>();
		try {
			q = dao.GetAllQuestions();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return q;
	}
	
	public void DeleteSelectedQuestion(Integer id) {
		Question q = new Question(id);
		dao.DeleteQuestion(q);
	}
	
	public void AddNewQuesiton(String question, String topic, String difficulty) {
		Answer placeholder = new Answer("TBD",-1);
		
		String fixedTopics = topic.replace(" ", "-");
		fixedTopics = fixedTopics.replace(","," ");
		
		Question newQuestion = new Question(question,fixedTopics,Integer.parseInt(difficulty),placeholder);
		dao.AddQuestion(newQuestion);
		
	}
	
	public List<MCQQuestion> GetAllMCQ(){
		List<MCQQuestion> q = new ArrayList<MCQQuestion>();
		try {
			q = dao.GetAllMCQ();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return q;
	}
	
	public void AddNewMCQuestion(String question, String topic, String difficulty, String choices) {
		//String question, String topic, int difficulty, List<MCQChoice> choicesList
		List<MCQChoice> mcqList = new ArrayList<MCQChoice>();
		String[] choicesArr = choices.split(",");
		for(String c : choicesArr) {
			if(c == choicesArr[0]) {
				MCQChoice mcqC = new MCQChoice(c,true);
				dao.AddMCQChoice(mcqC);
				try {
					mcqList.add(dao.SearchMCQChoice(mcqC).get(0));
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
			}
				
			else {
				MCQChoice mcqC = new MCQChoice(c,false);
				dao.AddMCQChoice(mcqC);
				try {
					mcqList.add(dao.SearchMCQChoice(mcqC).get(0));
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
			}
		}
		
		String fixedTopics = topic.replace(" ", "-");
		fixedTopics = fixedTopics.replace(","," ");
		
		MCQQuestion mcq = new MCQQuestion(question, fixedTopics,Integer.parseInt(difficulty),mcqList);
		dao.AddMCQQuestion(mcq);
		
	}
	
	public void DeleteSelectedMCQuestion(Integer id) {
		MCQQuestion q = new MCQQuestion(id);
		dao.DeleteMCQ(q);
	}
	
	public void addNewQuiz(String title, String sId, String sName, String qIds, String mcqIds) throws SQLException {
		
		List<Student> DBstudent = dao.SearchStudent(new Student(sName, sId));
		Student student;
		
		if(DBstudent.size() != 0)	//In this case student exists in the database
			student = DBstudent.get(0);
		else {	//In this case student does not exist and will be added
			dao.AddStudent(new Student(sName,sId));
			DBstudent = dao.SearchStudent(new Student(sName, sId));
			if(!DBstudent.isEmpty())	//In this case he was added
				student = DBstudent.get(0);
			else {	//In this case we failed to add a student
				return;
			}

		}
		
		String[] questionIds = qIds.split(" ");
		List<Question> questions = new ArrayList<Question>();
			for(String i : questionIds) {
				if(!i.equals(""))
					questions.add(new Question(Integer.parseInt(i)));			
			}
		
			
		
		
		String[] mcquestionIds = mcqIds.split(" ");
		List<MCQQuestion> mcquestions = new ArrayList<MCQQuestion>();

			for(String i : mcquestionIds) {
				if(!i.equals(""))
					mcquestions.add(new MCQQuestion(Integer.parseInt(i)));
			}
			
			
		Quiz newQuiz = new Quiz(title,questions,mcquestions,student);
		
		dao.AddQuiz(newQuiz);
	}
	
	public List<Quiz> GetAvailableQuizes(String id, String name){
		List<Quiz> quizes = new ArrayList<>();
		List<Question> q = new ArrayList<>();
		List<MCQQuestion> mcq = new ArrayList<>();
		List<Student> s = new ArrayList<>();
		try {
			s = dao.SearchStudent(new Student(name,id));
			if(s.size() >0) {
				quizes = dao.SearchQuiz(new Quiz("none",q,mcq,s.get(0)));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return quizes;
	}

	public Quiz SearchQuizList(List<Quiz> quizes, Integer id) {
		for(Quiz q : quizes) {
			if(q.getId() == id) {
				return q;
			}
		}
		return new Quiz();
	}

	public void UpdateAnswers(String answer, Question question) {
		dao.AddAnswer(new Answer(answer));
			try {
				Answer a = dao.SearchAnswer(new Answer(answer)).get(0);
				question.setAnswers(a);
				dao.UpdateQuestion(question);
			} catch (Exception e) {
	
				e.printStackTrace();
			}
		
	}

}
