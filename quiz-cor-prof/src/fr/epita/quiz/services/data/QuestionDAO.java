package fr.epita.quiz.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Question;

public class QuestionDAO {
	
	private String connectionLink = "jdbc:postgresql://localhost:5432/2020s1-quiz-database";
	private String username = "postgres";
	private String password = "";
	
	private void InsertQuestionRow(String q, int d) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();
			
			//VERY VERY BAD !!! Vulnerable to sql injections
			String sql = "INSERT INTO questions(question, difficulty) VALUES('"+q+"', '"+d+"')";
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!= null)
					conn.close();
				if(stmt!= null)
					stmt.close();;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void DeleteQuestionRowByID(int id) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionLink, username, password);
			stmt = conn.createStatement();
			
			String sql = "DELETE FROM questions WHERE id = "+ id;
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!= null)
					conn.close();
				if(stmt!= null)
					stmt.close();;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private ResultSet QueryTable(String query) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionLink,username,password);
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet results = prepareStatement.executeQuery();
		connection.close();
		return results;
	}
	
	public void create(String question, int difficulty) {
		InsertQuestionRow(question, difficulty);
	}
	
	public void delete(int questionId) {
		try {
			DeleteQuestionRowByID(questionId);
			search();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Question> search() throws SQLException {
		
		ResultSet results = QueryTable("select question,difficulty,id from \"questions\"");
		
		List<Question> resultList = new ArrayList<Question>();
		
		while (results.next()) {
			String question = results.getString("question");
			int difficulty = results.getInt("difficulty");
			int id = results.getInt("id");
			resultList.add(new Question(0,question,difficulty));
			System.out.println("question : " + question + " with difficulty :" + difficulty + " ID: "+ id);
		}
		
		//connection.close();
		
		return resultList;//TODO complete;
		
	}

}
