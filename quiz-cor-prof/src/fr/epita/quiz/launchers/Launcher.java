package fr.epita.quiz.launchers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.data.QuestionDAO;

public class Launcher {



	public static void main(String[] args) {
		
		QuestionDAO qdao = new QuestionDAO();
		
		System.out.println("Hello, please login");
		System.out.print("Username : ");
		Scanner scanner = new Scanner(System.in);
		String login = scanner.nextLine();
		System.out.print("Password : ");
		String pwd = scanner.nextLine();
		// authenticate
		if ( !AuthenticationService.authenticate(login, pwd)) {
			System.out.println("unauthorized");
			scanner.close();
			return;
		}
		System.out.println("congratulations,  you are authenticated!");
		System.out.println("please choose among the following options:");
		System.out.println("Create(c), Update(u), Delete(d), Quit(q)");
		System.out.print("what is your choice? (c|u|d|q) ");
		String choice = scanner.nextLine();
		switch (choice) {
		case "c":
			System.out.println("Creation");
			System.out.print("Enter Question:");
			String question = scanner.nextLine();
			System.out.print("Enter Difficulty:");
			int difficulty = scanner.nextInt();
			qdao.create(question, difficulty);
			break;
		case "d":
			System.out.println("deletion");
			System.out.print("Enter Question ID:");
			int idToDelete = scanner.nextInt();
			qdao.delete(idToDelete);
			break;
		case "u":
			System.out.println("update");
			
			try {
				List<Question> l = qdao.search();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "q":
			System.out.println("good bye!");
			System.exit(0);
			break;
		default:
			System.out.println("unrecognized option");
		}
		

		scanner.close();

	}
	


}
