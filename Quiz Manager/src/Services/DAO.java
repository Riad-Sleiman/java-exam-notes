package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utilities.Answer;
import Utilities.MCQChoice;
import Utilities.MCQQuestion;
import Utilities.Question;
import Utilities.Quiz;
import Utilities.Student;

public class DAO {

	private String connectionLink;
	private String username;
	private String password;

	public DAO(String connectionLink, String username, String password) {
		this.connectionLink = connectionLink;
		this.username = username;
		this.password = password;
	}

	// Search Functions for all tables

	public List<Answer> SearchAnswer(Answer answerSearchParams) throws SQLException {		
		Connection connection = DriverManager.getConnection(connectionLink, username, password);

		/*String query;
		PreparedStatement prepareStatement = null;
		
		if(answerSearchParams.getId() == null) {
			query = "SELECT * FROM answer WHERE text LIKE ?";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, answerSearchParams.getText());
		}
		else{
			query = "SELECT * FROM answer WHERE id =?";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, answerSearchParams.getId());
		}*/
		
		String query = "SELECT text, id FROM answer WHERE id = ? " + answerSearchParams.getId() + " OR text LIKE ?"
				+ answerSearchParams.getText() + "'";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		 prepareStatement.setInt(1, answerSearchParams.getId());
		 prepareStatement.setString(2, answerSearchParams.getText());
		ResultSet results = prepareStatement.executeQuery();
		
		List<Answer> answers = new ArrayList<Answer>();

		while (results.next()) {
			Answer a = new Answer(results.getString("text"), results.getInt("id"));
			answers.add(a);
		}
		connection.close();

		return answers;

	}

	public List<Student> SearchStudent(Student studentParam) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT studentid, id, name FROM \"student\" WHERE " + "studentid LIKE ' " + studentParam.getId()
				+ "'" + " OR name LIKE '" + studentParam.getName() + "'";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		// prepareStatement.setString(1,studentParam.getId());
		// prepareStatement.setString(2, studentParam.getName());
		// prepareStatement.setInt(3, studentParam.getDb_id());
		ResultSet results = prepareStatement.executeQuery();

		List<Student> students = new ArrayList<Student>();

		while (results.next()) {
			Student s = new Student(results.getString("name"), results.getString("studentid"), results.getInt("id"));
			students.add(s);
		}

		connection.close();

		return students;
	}

	public List<MCQChoice> SearchMCQChoice(MCQChoice mcqChoiceSearchParam) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT choice, valid, id FROM \"mcqchoice\" WHERE " + "id = " + mcqChoiceSearchParam.getId()
				+ " OR choice LIKE '" + mcqChoiceSearchParam.getChoice() + "'" + "AND valid = "
				+ mcqChoiceSearchParam.isValid();

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		// prepareStatement.setInt(1, mcqChoiceSearchParam.getId());
		// prepareStatement.setString(2,mcqChoiceSearchParam.getChoice());
		ResultSet results = prepareStatement.executeQuery();

		List<MCQChoice> choices = new ArrayList<>();

		while (results.next()) {
			MCQChoice c = new MCQChoice(results.getString("choice"), results.getBoolean("valid"), results.getInt("id"));
			choices.add(c);
		}

		connection.close();

		return choices;
	}

	public List<Question> SearchQuestion(Question questionSearchParam) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT id, question, topic, difficulty, answer FROM \"question\" WHERE " + "id = "
				+ questionSearchParam.getId() + " OR question LIKE '" + questionSearchParam.getQuestion() + "' "
				+ " OR topic LIKE '" + questionSearchParam.getTopic() + "' " + " OR difficulty = "
				+ questionSearchParam.getDifficulty();

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		// prepareStatement.setInt(1,questionSearchParam.getId());
		// prepareStatement.setString(2, questionSearchParam.getQuestion());
		// prepareStatement.setString(3, questionSearchParam.getTopic());
		// prepareStatement.setInt(3,questionSearchParam.getDifficulty());
		ResultSet results = prepareStatement.executeQuery();

		List<Question> questions = new ArrayList<Question>();

		while (results.next()) {
			Question c = new Question(results.getString("question"), results.getString("topic"),
					results.getInt("difficulty"), SearchAnswer(new Answer(results.getInt("answer"))).get(0),
					results.getInt("id"));
			questions.add(c);
		}

		connection.close();

		return questions;
	}

	public List<Question> GetAllQuestions() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT id, question, topic, difficulty, answer FROM \"question\" ";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet results = prepareStatement.executeQuery();

		List<Question> questions = new ArrayList<Question>();

		while (results.next()) {
			Question c = new Question(results.getString("question"), results.getString("topic"),
					results.getInt("difficulty"), new Answer("TEST"),
					// SearchAnswer(new Answer(results.getInt("answer"))).get(0),
					results.getInt("id"));
			questions.add(c);
		}

		connection.close();

		return questions;
	}

	public List<MCQQuestion> SearchMCQQuestion(MCQQuestion mcqQuestionSearchParam) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT id, question, topic, difficulty, choiceslist FROM \"mcqquestion\" WHERE " + "id = ? "
				+ " OR question LIKE '?'"
				// +" OR topic LIKE '?'"
				+ " OR difficulty = ?";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(1, mcqQuestionSearchParam.getId());
		prepareStatement.setString(2, mcqQuestionSearchParam.getQuestion());
		// prepareStatement.setString(3, mcqQuestionSearchParam.getTopic());
		prepareStatement.setInt(3, mcqQuestionSearchParam.getDifficulty());
		ResultSet results = prepareStatement.executeQuery();

		List<MCQQuestion> mcqQuesitons = new ArrayList<MCQQuestion>();

		while (results.next()) {
			List<MCQChoice> mcqcl = new ArrayList<MCQChoice>();
			String[] indexes = results.getString("choiceslist").split(" ");
			for (String index : indexes) {
				MCQChoice mcq = SearchMCQChoice(new MCQChoice(Integer.parseInt(index))).get(0);
				mcqcl.add(mcq);
			}
			MCQQuestion mcq = new MCQQuestion(results.getString("question"), results.getString("topic"),
					results.getInt("difficulty"), mcqcl, results.getInt("id"));
			mcqQuesitons.add(mcq);
		}

		connection.close();

		return mcqQuesitons;
	}

	public List<MCQQuestion> GetAllMCQ() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT id, question, topic, difficulty, choiceslist FROM \"mcqquestion\" ";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet results = prepareStatement.executeQuery();

		List<MCQQuestion> questions = new ArrayList<MCQQuestion>();

		while (results.next()) {
			// String question, String topic, int difficulty, List<MCQChoice> choicesList

			String[] choicesIds = results.getString("choiceslist").split(" ");
			List<MCQChoice> choices = new ArrayList<MCQChoice>();
			for (String cid : choicesIds) {
				List<MCQChoice> c = SearchMCQChoice(new MCQChoice(Integer.parseInt(cid)));
				if (c.size() > 0) {
					choices.add(c.get(0));
				}
			}

			MCQQuestion c = new MCQQuestion(results.getString("question"), results.getString("topic"),
					results.getInt("difficulty"), choices,
					// results.getString("choiceslist"),
					results.getInt("id"));
			questions.add(c);
		}

		connection.close();

		return questions;
	}

	public List<Quiz> SearchQuiz(Quiz quizParam) throws SQLException {

		Connection connection = DriverManager.getConnection(connectionLink, username, password);
		

		String query = "SELECT id, title, student, questions, mcqquestions FROM \"quiz\" WHERE " + "id = "
				+ quizParam.getId() + " OR title LIKE '" + quizParam.getTitle() + "'" + " OR student ="
				+ quizParam.getStudent().getDb_id();

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		// prepareStatement.setInt(1,quizParam.getId());
		// prepareStatement.setString(2, quizParam.getTitle());
		// prepareStatement.setString(3, quizParam.getStudent().getName());
		ResultSet results = prepareStatement.executeQuery();

		List<Quiz> quizes = new ArrayList<Quiz>();

		while (results.next()) {
			List<Question> allQuestions = GetAllQuestions();
			List<Question> questions = new ArrayList<Question>();
			String[] qIds = results.getString("questions").split(" ");
			for (String index : qIds) {
				for (Question q : allQuestions) {
					if(!index.equals("")) {
						if (q.getId() == Integer.parseInt(index))
							questions.add(q);
					}
				}
			}

			List<MCQQuestion> allMcqq = GetAllMCQ();
			List<MCQQuestion> mcqq = new ArrayList<MCQQuestion>();
			String[] mcqIds = results.getString("mcqquestions").split(" ");
			for (String index : mcqIds) {
				for (MCQQuestion q : allMcqq) {
					if(!index.equals("")) {
						if (q.getId() == Integer.parseInt(index))
							mcqq.add(q);
					}
				}
			}

			Student student = quizParam.getStudent();

			Quiz q = new Quiz(results.getString("title"), questions, mcqq, student, results.getInt("id"));
			quizes.add(q);
		}

		return quizes;
	}

	// Add entries to all tables

	public void AddAnswer(Answer answer) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String sql = "INSERT INTO answer(text) VALUES('" + answer.getText() + "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void AddStudent(Student student) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String sql = "INSERT INTO student(studentid,name) VALUES('" + student.getId() + "','" + student.getName()
					+ "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void AddMCQChoice(MCQChoice mcqChoice) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO mcqchoice(valid,choice) VALUES(" + mcqChoice.isValid() + ",'"
					+ mcqChoice.getChoice() + "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void AddQuestion(Question question) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			Integer answerID = question.getAnswers().getId();
			
			String sql = "INSERT INTO question(question,topic,difficulty,answer) VALUES('" + question.getQuestion()
					+ "','" + question.getTopicForDB() + "'," + question.getDifficulty() + "," + answerID + ")";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void AddMCQQuestion(MCQQuestion mcqQuestion) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String choiceIds = "";
			for (MCQChoice choice : mcqQuestion.getChoicesList()) {
				choiceIds += choice.getId() + " ";
			}
			String sql = "INSERT INTO mcqquestion(question,topic,difficulty,choiceslist) VALUES('"
					+ mcqQuestion.getQuestion() + "','" + mcqQuestion.getTopicForDB() + "',"
					+ mcqQuestion.getDifficulty() + ",'" + choiceIds + "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void AddQuiz(Quiz quiz) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String questionsIds = "";
			for (Question question : quiz.getQuestions()) {
				questionsIds += question.getId() + " ";
			}

			String mcqIds = "";
			for (MCQQuestion mcq : quiz.getMcqQuestions()) {
				mcqIds += mcq.getId() + " ";
			}
			;
			String sql = "INSERT INTO quiz(questions,mcqquestions,student,title) VALUES('" + questionsIds + "','"
					+ mcqIds + "'," + quiz.getStudent().getDb_id() + ",'" + quiz.getTitle() + "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	// Delete entries

	public void DeleteQuestion(Question questionParams) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String sql = "DELETE FROM question WHERE id = " + questionParams.getId();

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void DeleteMCQ(MCQQuestion mcqParams) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();

			String sql = "DELETE FROM mcqquestion WHERE id = " + mcqParams.getId();

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	// Update entries
	public void UpdateQuestion(Question question) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();
			;
			String sql = "UPDATE question SET answer = " + question.getAnswers().getId() + " WHERE id = "
					+ question.getId();

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
}
