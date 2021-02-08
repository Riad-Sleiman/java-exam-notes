package application;
	
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;
import com.sun.prism.paint.Color;

import Services.Administration;
import Services.DAO;
import Utilities.Answer;
import Utilities.MCQChoice;
import Utilities.MCQQuestion;
import Utilities.Question;
import Utilities.Quiz;
import Utilities.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;


public class Main extends Application {
	
	Administration admin = new Administration();
	
	List<Question> allQuestionsPage = admin.GetAllQuestions();
	List<CheckBox> questionPageBoxes = new ArrayList<CheckBox>();
	
	List<MCQQuestion> allMCQuestionsPage = admin.GetAllMCQ();
	List<CheckBox> mcquestionPageBoxes = new ArrayList<CheckBox>();
	
	VBox questionVbox = new VBox();
	List<CheckBox> questionBoxes = new ArrayList<CheckBox>();		
	List<Question> allQuestions = admin.GetAllQuestions();
	
	VBox mcqquestionVbox = new VBox();
	List<CheckBox> mcqquestionBoxes = new ArrayList<CheckBox>();		
	List<MCQQuestion> allMCQQuestions = admin.GetAllMCQ();
	
	List<Quiz> availableQuizes = new ArrayList<Quiz>();
	final ToggleGroup quizGroup = new ToggleGroup();
	List<RadioButton> quizList = new ArrayList<RadioButton>();
	Integer quizID;
	Quiz currentQuiz;
	VBox quizVbox = new VBox();
	List<TextField> allAnswers = new ArrayList<TextField>();
	List<CheckBox> allMCQchoices = new ArrayList<CheckBox>();
	
	@Override
	public void start(Stage primaryStage) throws SQLException {
		try {
			
			
			 
			
			//Pages
			StackPane titlePage = new StackPane();
			StackPane teacherPage = new StackPane();
			StackPane createQuizPage = new StackPane();
			StackPane createQuestionPage = new StackPane();
			StackPane createMCQPage = new StackPane();
			StackPane studentPage = new StackPane();
			//Pages
			
			Scene sceneTitle = new Scene(titlePage, 600, 650);
			Scene sceneTeacher = new Scene(teacherPage,600,650);
			Scene sceneCreateQuiz = new Scene(createQuizPage,1000, 650);
			Scene sceneCreateQuestion = new Scene(createQuestionPage,600,650);
			Scene sceneCreateMCQ = new Scene(createMCQPage,600,650);
			Scene sceneStudent = new Scene(studentPage,600,650);
			
			//TITLE PAGE
			Label title = new Label("QUIZ MANAGER");
			title.setFont(new Font("Calibri",50));
			title.setTranslateY(-300);
			
			Label description = new Label("Welcome, select your function");
			description.setFont(new Font("Calibri",30));
			description.setTranslateY(-90);
			
			Button btn_Teacher = new Button("Teacher");
			btn_Teacher.setFont(new Font("Calibri",24));
			btn_Teacher.setTranslateY(-30);
			btn_Teacher.setOnAction( e -> {
				primaryStage.setScene(sceneTeacher);
				primaryStage.show();
			});
			
			Button btn_Student = new Button("Student");
			btn_Student.setFont(new Font("Calibri",24));
			btn_Student.setTranslateY(30);
			btn_Student.setOnAction( e -> {
				primaryStage.setScene(sceneStudent);
				primaryStage.show();
			});
			
			titlePage.getChildren().addAll(title,description,btn_Teacher,btn_Student);
			//TITLE PAGE
			
			//TEACHER PAGE
			Label teacher_title = new Label("TEACHER");
			teacher_title.setFont(new Font("Calibri",50));
			teacher_title.setTranslateY(-300);
			
			Button btn_CreateQuiz = new Button("Create A New Quiz");
			btn_CreateQuiz.setFont(new Font("Calibri",24));
			btn_CreateQuiz.setTranslateY(-90);
			btn_CreateQuiz.setOnAction( e -> {
				primaryStage.setScene(sceneCreateQuiz);
				//Done here to be dynamically updated
				allQuestions.clear();
				allQuestions = admin.GetAllQuestions();
				questionVbox.getChildren().clear();
				for(int i = 0 ; i < allQuestions.size(); i++) {
					CheckBox checkbox = new CheckBox(allQuestions.get(i).getQuestion());
					questionBoxes.add(checkbox);
					questionVbox.getChildren().add(questionBoxes.get(i));
				}
				allMCQQuestions.clear();
				allMCQQuestions = admin.GetAllMCQ();
				mcqquestionVbox.getChildren().clear();
				for(int i = 0 ; i < allMCQQuestions.size(); i++) {
					CheckBox checkbox = new CheckBox(allMCQQuestions.get(i).getQuestion());
					mcqquestionBoxes.add(checkbox);
					mcqquestionVbox.getChildren().add(mcqquestionBoxes.get(i));
				}
				primaryStage.show();
			});
			
			Button btn_CreateQuestion = new Button("Edit Questions");
			btn_CreateQuestion.setFont(new Font("Calibri",24));
			btn_CreateQuestion.setTranslateY(0);
			btn_CreateQuestion.setOnAction( e -> {
				primaryStage.setScene(sceneCreateQuestion);
				primaryStage.show();
			});
			
			Button btn_CreateMCQuestion = new Button("Edit MCQs");
			btn_CreateMCQuestion.setFont(new Font("Calibri",24));
			btn_CreateMCQuestion.setTranslateY(90);
			btn_CreateMCQuestion.setOnAction( e -> {
				primaryStage.setScene(sceneCreateMCQ);
				primaryStage.show();
			});
			
			Button btn_back_to_title = new Button("Back");
			btn_back_to_title.setFont(new Font("Calibri",16));
			btn_back_to_title.setTranslateY(-300);
			btn_back_to_title.setTranslateX(-250);
			btn_back_to_title.setOnAction( e -> {
				primaryStage.setScene(sceneTitle);
				primaryStage.show();
			});
			
			teacherPage.getChildren().addAll(teacher_title,btn_CreateQuiz,btn_back_to_title,btn_CreateQuestion,btn_CreateMCQuestion);
			//TEACHER PAGE
			
			//CREATE PAGE QUIZ
			
			
			
			Label createQuiz_title = new Label("CREATE QUIZ");
			createQuiz_title.setFont(new Font("Calibri",50));
			createQuiz_title.setTranslateY(-300);
			
			
			
			Label quizTitle = new Label("Title:");
			quizTitle.setFont(new Font("Calibri",30));
			quizTitle.setTranslateY(-200);
			quizTitle.setTranslateX(-400);
			
			TextField txt_quizTitle = new TextField();
			txt_quizTitle.setFont(new Font("Calibri",14));
			txt_quizTitle.setMaxWidth(200);
			txt_quizTitle.setTranslateY(-150);
			txt_quizTitle.setTranslateX(-325);
			
			Label studentLable = new Label("Student Information:");
			studentLable.setFont(new Font("Calibri",30));
			studentLable.setTranslateY(-100);
			studentLable.setTranslateX(-305);
			
			TextField txt_studentID = new TextField();
			txt_studentID.setFont(new Font("Calibri",14));
			txt_studentID.setMaxWidth(200);
			txt_studentID.setPromptText("ID");
			txt_studentID.setTranslateY(-50);
			txt_studentID.setTranslateX(-325);
			
			TextField txt_studentName = new TextField();
			txt_studentName.setFont(new Font("Calibri",14));
			txt_studentName.setMaxWidth(200);
			txt_studentName.setPromptText("Full Name");
			txt_studentName.setTranslateY(0);
			txt_studentName.setTranslateX(-325);
			
			Label questionLable = new Label("Questions List");
			questionLable.setFont(new Font("Calibri",30));
			questionLable.setTranslateY(-250);
			questionLable.setTranslateX(325);

			ScrollPane questionScroll = new ScrollPane();
			questionScroll.setContent(questionVbox); 
			questionScroll.setMaxWidth(300);
			questionScroll.setMaxHeight(450);
			questionScroll.setTranslateX(325);
			questionScroll.setTranslateY(0);

			
			/*for(int i = 0 ; i < allQuestions.size(); i++) {
				CheckBox checkbox = new CheckBox(allQuestions.get(i).getQuestion());
				questionBoxes.add(checkbox);
				questionVbox.getChildren().add(questionBoxes.get(i));
			}*/
			
			Label mcqquestionLable = new Label("MCQ List");
			mcqquestionLable.setFont(new Font("Calibri",30));
			mcqquestionLable.setTranslateY(-250);
			mcqquestionLable.setTranslateX(0);
			
			ScrollPane mcqquestionScroll = new ScrollPane();

			mcqquestionScroll.setContent(mcqquestionVbox); 
			mcqquestionScroll.setMaxWidth(300);
			mcqquestionScroll.setMaxHeight(450);
			mcqquestionScroll.setTranslateX(0);
			mcqquestionScroll.setTranslateY(0);

			
			/*for(int i = 0 ; i < allMCQQuestions.size(); i++) {
				CheckBox checkbox = new CheckBox(allMCQQuestions.get(i).getQuestion());
				mcqquestionBoxes.add(checkbox);
				mcqquestionVbox.getChildren().add(mcqquestionBoxes.get(i));
			}*/

			Text errorMessage = new Text("Please fill in all information (One question at least)");
			errorMessage.setVisible(false);
			errorMessage.setTranslateX(-325);
			errorMessage.setTranslateY(160);
			
			Button btn_SaveQuiz = new Button("Save Quiz");
			btn_SaveQuiz.setFont(new Font("Calibri",24));
			btn_SaveQuiz.setTranslateY(200);
			btn_SaveQuiz.setTranslateX(-325);
			btn_SaveQuiz.setOnAction( e -> {				
				String qIds = "";
				for(int i = 0 ; i < questionBoxes.size(); i++) {

					if(questionBoxes.get(i).isSelected())
						qIds += allQuestions.get(i).getId() + " ";
				}
				String mcqIds = "";
				for(int i = 0 ; i < mcqquestionBoxes.size(); i++) {
					if(mcqquestionBoxes.get(i).isSelected())
						mcqIds += allMCQQuestions.get(i).getId() + " ";
				}
				
				
				if(txt_quizTitle.getText().equals("") || txt_studentID.getText().equals("") || txt_studentName.getText().equals("") || (qIds.equals("") && mcqIds.equals("")))
					errorMessage.setVisible(true);
				else {
					errorMessage.setVisible(false);
					try {
						admin.addNewQuiz(txt_quizTitle.getText(), txt_studentID.getText(), txt_studentName.getText(), qIds, mcqIds);
						primaryStage.setScene(sceneTeacher);
						primaryStage.show();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			
				
			});
			
			Button btn_back_to_teacher = new Button("Back");
			btn_back_to_teacher.setFont(new Font("Calibri",16));
			btn_back_to_teacher.setTranslateY(-300);
			btn_back_to_teacher.setTranslateX(-450);
			btn_back_to_teacher.setOnAction( e -> {
				txt_studentID.setText("");
				txt_studentName.setText("");
				txt_quizTitle.setText("");
				primaryStage.setScene(sceneTeacher);
				primaryStage.show();
			});
			
			createQuizPage.getChildren().addAll(createQuiz_title, 
					btn_back_to_teacher,
					quizTitle,
					txt_quizTitle,
					studentLable,
					txt_studentID,
					txt_studentName,
					btn_SaveQuiz,
					questionScroll,
					questionLable,
					mcqquestionScroll,
					mcqquestionLable,
					errorMessage);
			
			//CREATE PAGE QUIZ
			
			//Create Question Page
			
			StackPane addQuestionPane = new StackPane();
			addQuestionPane.setVisible(false);
			
			StackPane editPane = new StackPane();
			editPane.setVisible(true);
			
			Label createQuestion_title = new Label("EDIT QUESTIONS");
			createQuestion_title.setFont(new Font("Calibri",50));
			createQuestion_title.setTranslateY(-300);
			
			Button btn_back_to_teacher_question = new Button("Back");
			btn_back_to_teacher_question.setFont(new Font("Calibri",16));
			btn_back_to_teacher_question.setTranslateY(-300);
			btn_back_to_teacher_question.setTranslateX(-250);
			btn_back_to_teacher_question.setOnAction( e -> {

				primaryStage.setScene(sceneTeacher);
				primaryStage.show();
			});
			

			
			ScrollPane questionPageScroll = new ScrollPane();
			GridPane entry = new GridPane();
			entry.setMaxHeight(50);
			entry.setMaxWidth(500);
			questionPageScroll.setContent(entry); 
			questionPageScroll.setMaxWidth(500);
			questionPageScroll.setMaxHeight(450);
			questionPageScroll.setTranslateX(0);
			questionPageScroll.setTranslateY(50);
		

			
			for(int i = 0 ; i < allQuestionsPage.size(); i++) {
				
				CheckBox checkbox = new CheckBox(allQuestionsPage.get(i).getQuestion()+"     ");
				questionPageBoxes.add(checkbox);
				entry.add(questionPageBoxes.get(i),0,i);
				entry.add(new Text(allQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
				entry.add(new Text(allQuestionsPage.get(i).getDifficulty()+""), 2, i);
				
			}
			
			Button btn_deleteQuestion = new Button("Delete Selected");
			btn_deleteQuestion.setFont(new Font("Calibri",16));
			btn_deleteQuestion.setTranslateY(-200);
			btn_deleteQuestion.setTranslateX(180);
			btn_deleteQuestion.setOnAction( e -> {
				for(int i = 0 ; i < questionPageBoxes.size(); i++) {
					if(questionPageBoxes.get(i).isSelected()) {
						admin.DeleteSelectedQuestion(allQuestionsPage.get(i).getId());
					}

				}
				
				entry.getChildren().clear();
				questionPageBoxes.clear();
				allQuestionsPage = admin.GetAllQuestions();
				for(int i = 0 ; i < allQuestionsPage.size(); i++) {
					
					CheckBox checkbox = new CheckBox(allQuestionsPage.get(i).getQuestion()+"     ");
					questionPageBoxes.add(checkbox);
					entry.add(questionPageBoxes.get(i),0,i);
					entry.add(new Text(allQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
					entry.add(new Text(allQuestionsPage.get(i).getDifficulty()+""), 2, i);
					
				}
			});
			
			Button btn_addQuestion = new Button("Create");
			btn_addQuestion.setFont(new Font("Calibri",16));
			btn_addQuestion.setTranslateY(-200);
			btn_addQuestion.setTranslateX(50);
			btn_addQuestion.setOnAction( e -> {
				addQuestionPane.setVisible(true);
				editPane.setVisible(false);
			});
			
			//Add Question Tab starts here
			
			Label question_title_label = new Label("Question:");
			question_title_label.setFont(new Font("Calibri",20));
			question_title_label.setTranslateY(-200);
			question_title_label.setTranslateX(-100);
			
			TextField txt_newQuesiton = new TextField();
			txt_newQuesiton.setFont(new Font("Calibri",20));
			txt_newQuesiton.setMaxWidth(310);
			txt_newQuesiton.setTranslateY(-150);
			txt_newQuesiton.setTranslateX(-50);
			
			Label question_topic_label = new Label("Topic[s]:");
			question_topic_label.setFont(new Font("Calibri",20));
			question_topic_label.setTranslateY(-100);
			question_topic_label.setTranslateX(-100);
			
			TextField txt_newTopic = new TextField();
			txt_newTopic.setFont(new Font("Calibri",20));
			txt_newTopic.setMaxWidth(310);
			txt_newTopic.setPromptText("Seperate with coma(No spaces)");
			txt_newTopic.setTranslateY(-50);
			txt_newTopic.setTranslateX(-50);
			
			Label question_diff_label = new Label("Difficulty:");
			question_diff_label.setFont(new Font("Calibri",20));
			question_diff_label.setTranslateY(0);
			question_diff_label.setTranslateX(-100);
			
			TextField txt_newDiff = new TextField();
			txt_newDiff.setFont(new Font("Calibri",20));
			txt_newDiff.setMaxWidth(310);
			txt_newDiff.setPromptText("Enter a number(letters count as 0)");
			txt_newDiff.setTranslateY(50);
			txt_newDiff.setTranslateX(-50);
			txt_newDiff.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	txt_newDiff.setText(newValue.replaceAll("[^\\d]", "0"));
			        }
			    }
			});
			
			Button btn_insertQuestion = new Button("Add New Question");
			btn_insertQuestion.setFont(new Font("Calibri",16));
			btn_insertQuestion.setTranslateY(100);
			btn_insertQuestion.setTranslateX(-100);
			btn_insertQuestion.setOnAction( e -> {
				if(txt_newQuesiton.getText().equals("") || txt_newTopic.getText().equals("") || txt_newDiff.getText().equals(""))
					btn_insertQuestion.setFont(Font.font("Calibri",FontWeight.BOLD,16));
				else {
					admin.AddNewQuesiton(txt_newQuesiton.getText(),txt_newTopic.getText() ,txt_newDiff.getText() );
					txt_newQuesiton.setText("");
					txt_newTopic.setText("");
					txt_newDiff.setText("");
					addQuestionPane.setVisible(false);
					editPane.setVisible(true);
					entry.getChildren().clear();
					questionPageBoxes.clear();
					allQuestionsPage = admin.GetAllQuestions();
					for(int i = 0 ; i < allQuestionsPage.size(); i++) {
						
						CheckBox checkbox = new CheckBox(allQuestionsPage.get(i).getQuestion()+"     ");
						questionPageBoxes.add(checkbox);
						entry.add(questionPageBoxes.get(i),0,i);
						entry.add(new Text(allQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
						entry.add(new Text(allQuestionsPage.get(i).getDifficulty()+""), 2, i);
					}
				}
			});
			
			Button btn_cancelQuestion = new Button("Cancel");
			btn_cancelQuestion.setFont(new Font("Calibri",16));
			btn_cancelQuestion.setTranslateY(100);
			btn_cancelQuestion.setTranslateX(50);
			btn_cancelQuestion.setOnAction( e -> {
					txt_newQuesiton.setText("");
					txt_newTopic.setText("");
					txt_newDiff.setText("");
					addQuestionPane.setVisible(false);
					editPane.setVisible(true);					
				}

			);
			
			
			addQuestionPane.getChildren().addAll(question_title_label,
					txt_newQuesiton,
					question_topic_label,
					txt_newTopic,
					question_diff_label,
					txt_newDiff,
					btn_insertQuestion,
					btn_cancelQuestion);
			
			editPane.getChildren().addAll(createQuestion_title,
					btn_back_to_teacher_question,
					questionPageScroll,
					btn_deleteQuestion,
					btn_addQuestion);
			
			createQuestionPage.getChildren().addAll(editPane,addQuestionPane);
			//Create Question Page
			
			//Create MCQ Page
			StackPane addMCQPane = new StackPane();
			addMCQPane.setVisible(false);
			
			StackPane editMCQPane = new StackPane();
			editMCQPane.setVisible(true);
			
			Label createMCQuestion_title = new Label("EDIT QUESTIONS");
			createMCQuestion_title.setFont(new Font("Calibri",50));
			createMCQuestion_title.setTranslateY(-300);
			
			Button btn_back_to_teacher_mcquestion = new Button("Back");
			btn_back_to_teacher_mcquestion.setFont(new Font("Calibri",16));
			btn_back_to_teacher_mcquestion.setTranslateY(-300);
			btn_back_to_teacher_mcquestion.setTranslateX(-250);
			btn_back_to_teacher_mcquestion.setOnAction( e -> {

				primaryStage.setScene(sceneTeacher);
				primaryStage.show();
			});
			

			
			ScrollPane mcquestionPageScroll = new ScrollPane();
			GridPane MCQentry = new GridPane();
			MCQentry.setMaxHeight(50);
			MCQentry.setMaxWidth(500);
			mcquestionPageScroll.setContent(MCQentry); 
			mcquestionPageScroll.setMaxWidth(500);
			mcquestionPageScroll.setMaxHeight(450);
			mcquestionPageScroll.setTranslateX(0);
			mcquestionPageScroll.setTranslateY(50);
		

			
			for(int i = 0 ; i < allMCQuestionsPage.size(); i++) {
				
				CheckBox checkbox = new CheckBox(allMCQuestionsPage.get(i).getQuestion()+"     ");
				mcquestionPageBoxes.add(checkbox);
				MCQentry.add(mcquestionPageBoxes.get(i),0,i);
				MCQentry.add(new Text(allMCQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
				MCQentry.add(new Text(allMCQuestionsPage.get(i).getDifficulty()+""), 2, i);
				
			}
			
			Button btn_deleteMCQuestion = new Button("Delete Selected");
			btn_deleteMCQuestion.setFont(new Font("Calibri",16));
			btn_deleteMCQuestion.setTranslateY(-200);
			btn_deleteMCQuestion.setTranslateX(180);
			btn_deleteMCQuestion.setOnAction( e -> {
				for(int i = 0 ; i < mcquestionPageBoxes.size(); i++) {
					if(mcquestionPageBoxes.get(i).isSelected()) {
						admin.DeleteSelectedMCQuestion(allMCQuestionsPage.get(i).getId());
					}

				}
				
				MCQentry.getChildren().clear();
				mcquestionPageBoxes.clear();
				allMCQuestionsPage = admin.GetAllMCQ();
				for(int i = 0 ; i < allMCQuestionsPage.size(); i++) {
					
					CheckBox checkbox = new CheckBox(allMCQuestionsPage.get(i).getQuestion()+"     ");
					mcquestionPageBoxes.add(checkbox);
					MCQentry.add(mcquestionPageBoxes.get(i),0,i);
					MCQentry.add(new Text(allMCQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
					MCQentry.add(new Text(allMCQuestionsPage.get(i).getDifficulty()+""), 2, i);
					
				}
			});
			
			Button btn_addMCQuestion = new Button("Create");
			btn_addMCQuestion.setFont(new Font("Calibri",16));
			btn_addMCQuestion.setTranslateY(-200);
			btn_addMCQuestion.setTranslateX(50);
			btn_addMCQuestion.setOnAction( e -> {
				addMCQPane.setVisible(true);
				editMCQPane.setVisible(false);
			});
			
			//Add MCQuestion Tab starts here
			
			Label mcquestion_title_label = new Label("Question:");
			mcquestion_title_label.setFont(new Font("Calibri",20));
			mcquestion_title_label.setTranslateY(-200);
			mcquestion_title_label.setTranslateX(-100);
			
			TextField txt_newMCQuesiton = new TextField();
			txt_newMCQuesiton.setFont(new Font("Calibri",20));
			txt_newMCQuesiton.setMaxWidth(310);
			txt_newMCQuesiton.setTranslateY(-150);
			txt_newMCQuesiton.setTranslateX(-50);
			
			Label mcquestion_topic_label = new Label("Topic[s]:");
			mcquestion_topic_label.setFont(new Font("Calibri",20));
			mcquestion_topic_label.setTranslateY(-100);
			mcquestion_topic_label.setTranslateX(-100);
			
			TextField txt_newMCQTopic = new TextField();
			txt_newMCQTopic.setFont(new Font("Calibri",20));
			txt_newMCQTopic.setMaxWidth(310);
			txt_newMCQTopic.setPromptText("Seperate with coma(No spaces)");
			txt_newMCQTopic.setTranslateY(-50);
			txt_newMCQTopic.setTranslateX(-50);
			
			Label mcquestion_diff_label = new Label("Difficulty:");
			mcquestion_diff_label.setFont(new Font("Calibri",20));
			mcquestion_diff_label.setTranslateY(0);
			mcquestion_diff_label.setTranslateX(-100);
			
			TextField txt_newMCQDiff = new TextField();
			txt_newMCQDiff.setFont(new Font("Calibri",20));
			txt_newMCQDiff.setMaxWidth(310);
			txt_newMCQDiff.setPromptText("Enter a number(letters count as 0)");
			txt_newMCQDiff.setTranslateY(50);
			txt_newMCQDiff.setTranslateX(-50);
			txt_newMCQDiff.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	txt_newDiff.setText(newValue.replaceAll("[^\\d]", "0"));
			        }
			    }
			});
			
			Label mcquestion_choices_label = new Label("Enter Choices starting with the correct one:");
			mcquestion_choices_label.setFont(new Font("Calibri",20));
			mcquestion_choices_label.setTranslateY(100);
			mcquestion_choices_label.setTranslateX(-50);
			
			TextField txt_newMCQChoices = new TextField();
			txt_newMCQChoices.setFont(new Font("Calibri",20));
			txt_newMCQChoices.setMaxWidth(500);
			txt_newMCQChoices.setPromptText("Seperate with coma(No spaces)");
			txt_newMCQChoices.setTranslateY(150);
			txt_newMCQChoices.setTranslateX(0);
			
			Button btn_insertMCQuestion = new Button("Add New MCQ");
			btn_insertMCQuestion.setFont(new Font("Calibri",16));
			btn_insertMCQuestion.setTranslateY(200);
			btn_insertMCQuestion.setTranslateX(-100);
			btn_insertMCQuestion.setOnAction( e -> {
				if(txt_newMCQuesiton.getText().equals("") || txt_newMCQTopic.getText().equals("") || txt_newMCQDiff.getText().equals("") || txt_newMCQChoices.getText().equals(""))
					btn_insertMCQuestion.setFont(Font.font("Calibri",FontWeight.BOLD,16));
				else {
					admin.AddNewMCQuestion(txt_newMCQuesiton.getText(),txt_newMCQTopic.getText(),txt_newMCQDiff.getText(),txt_newMCQChoices.getText());
					txt_newMCQuesiton.setText("");
					txt_newMCQTopic.setText("");
					txt_newMCQDiff.setText("");
					txt_newMCQChoices.setText("");
					addMCQPane.setVisible(false);
					editMCQPane.setVisible(true);
					MCQentry.getChildren().clear();
					mcquestionPageBoxes.clear();
					allMCQuestionsPage = admin.GetAllMCQ();
					for(int i = 0 ; i < allMCQuestionsPage.size(); i++) {
						
						CheckBox checkbox = new CheckBox(allMCQuestionsPage.get(i).getQuestion()+"     ");
						mcquestionPageBoxes.add(checkbox);
						MCQentry.add(mcquestionPageBoxes.get(i),0,i);
						MCQentry.add(new Text(allMCQuestionsPage.get(i).getTopicForDB()+"      "), 1, i);
						MCQentry.add(new Text(allMCQuestionsPage.get(i).getDifficulty()+""), 2, i);
					}
				}
			});
			
			Button btn_cancelMCQuestion = new Button("Cancel");
			btn_cancelMCQuestion.setFont(new Font("Calibri",16));
			btn_cancelMCQuestion.setTranslateY(200);
			btn_cancelMCQuestion.setTranslateX(50);
			btn_cancelMCQuestion.setOnAction( e -> {
					txt_newMCQuesiton.setText("");
					txt_newMCQTopic.setText("");
					txt_newMCQDiff.setText("");
					addMCQPane.setVisible(false);
					editMCQPane.setVisible(true);					
				}

			);
			
			
			addMCQPane.getChildren().addAll(mcquestion_title_label,
					txt_newMCQuesiton,
					mcquestion_topic_label,
					txt_newMCQTopic,
					mcquestion_diff_label,
					txt_newMCQDiff,
					mcquestion_choices_label,
					txt_newMCQChoices,
					btn_insertMCQuestion,
					btn_cancelMCQuestion);
			
			editMCQPane.getChildren().addAll(createMCQuestion_title,
					btn_back_to_teacher_mcquestion,
					mcquestionPageScroll,
					btn_deleteMCQuestion,
					btn_addMCQuestion);
			
			createMCQPage.getChildren().addAll(addMCQPane,editMCQPane);
			//Create MCQ Page
			
			//STUDENT PAGE
			
			StackPane studentLoginPane = new StackPane();
			StackPane selectQuizPane = new StackPane();
			StackPane takeQuizPane = new StackPane();
			
			
			Label student_title = new Label("STUDENT");
			student_title.setFont(new Font("Calibri",50));
			student_title.setTranslateY(-300);
			
			Button btn_back_to_title2 = new Button("Back");
			btn_back_to_title2.setFont(new Font("Calibri",16));
			btn_back_to_title2.setTranslateY(-300);
			btn_back_to_title2.setTranslateX(-250);
			btn_back_to_title2.setOnAction( e -> {
				primaryStage.setScene(sceneTitle);
				primaryStage.show();
			});
			

			TextField txt_studentId = new TextField();
			txt_studentId.setFont(new Font("Calibri",20));
			txt_studentId.setMaxWidth(310);
			txt_studentId.setPromptText("Enter Student ID");
			txt_studentId.setTranslateY(-50);
			txt_studentId.setTranslateX(0);
			
			TextField txt_studentNameInput= new TextField();
			txt_studentNameInput.setFont(new Font("Calibri",20));
			txt_studentNameInput.setMaxWidth(310);
			txt_studentNameInput.setPromptText("Enter Name");
			txt_studentNameInput.setTranslateY(0);
			txt_studentNameInput.setTranslateX(0);
			
			Label student_message = new Label("");
			student_message.setFont(new Font("Calibri",20));
			student_message.setTranslateY(100);
			
			Button btn_checkForQuiz = new Button("Check For Available Quiz");
			btn_checkForQuiz.setFont(new Font("Calibri",16));
			btn_checkForQuiz.setTranslateY(50);
			btn_checkForQuiz.setTranslateX(0);
			btn_checkForQuiz.setOnAction( e -> {
				if(txt_studentNameInput.getText().equals("") || txt_studentId.getText().equals(""))
					student_message.setText("Please enter both name and ID");
				else {
					availableQuizes = admin.GetAvailableQuizes(txt_studentId.getText(), txt_studentNameInput.getText());
					if(availableQuizes.size() <= 0)
						student_message.setText("No quizes available for this student");
					else {
						student_message.setText("Working...");
						studentPage.getChildren().remove(studentLoginPane);
						studentPage.getChildren().add(selectQuizPane);
						double y = 0;
						quizVbox.getChildren().clear();
						for(Quiz q : availableQuizes) {
							RadioButton rb = new RadioButton(q.getTitle());
							if(y == 0) {
								rb.setSelected(true);
								quizID = q.getId();
							}
							rb.setFont(new Font("Calibri", 16));
							rb.setToggleGroup(quizGroup);
							rb.setUserData(q.getId());
							y += 1;
							quizVbox.getChildren().add(rb);
							quizList.add(rb);
							
						}
					}
				}
			});
			
			//Quiz select
			Label student_select_title = new Label("QUIZ SELECT");
			student_select_title.setFont(new Font("Calibri",50));
			student_select_title.setTranslateY(-300);
			
			quizGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
			            if (quizGroup.getSelectedToggle() != null) {
			            	Integer selectedID= (Integer) quizGroup.getSelectedToggle().getUserData();
			            	if(selectedID != null)
			            		quizID = selectedID;
			            }                
			        }
			});
			
			ScrollPane quizSelectScroll = new ScrollPane();
			quizSelectScroll.setMaxHeight(500);
			quizSelectScroll.setMaxWidth(400);
			quizSelectScroll.setContent(quizVbox);
			
			Button btn_startQuiz = new Button("Start Quiz");
			btn_startQuiz.setFont(new Font("Calibri",16));
			btn_startQuiz.setTranslateY(295);
			btn_startQuiz.setTranslateX(0);
			btn_startQuiz.setOnAction( e -> {
				quizVbox.getChildren().clear();
				currentQuiz = admin.SearchQuizList(availableQuizes, quizID);
				
				Label Qtitle = new Label(currentQuiz.getTitle());
				Qtitle.setFont(new Font("Calibri",50));
				quizVbox.getChildren().add(Qtitle);
				
				for(int i = 0; i < currentQuiz.getQuestions().size(); i++){
					Label question = new Label(currentQuiz.getQuestions().get(i).getQuestion());
					question.setFont(new Font("Calibri",14));
					quizVbox.getChildren().add(question);
					
					TextField answer = new TextField();
					answer.setPromptText("Answer here...");
					allAnswers.add(answer);
					quizVbox.getChildren().add(answer);
				}
				
				for(int i = 0; i < currentQuiz.getMcqQuestions().size(); i++) {
					Label question = new Label(currentQuiz.getMcqQuestions().get(i).getQuestion());
					question.setFont(new Font("Calibri",14));
					quizVbox.getChildren().add(question);
					
					List<MCQChoice> choicesList = currentQuiz.getMcqQuestions().get(i).getChoicesList();
					Collections.shuffle(choicesList);
					for(int j = 0; j < choicesList.size(); j++) {
						CheckBox choiceBox = new CheckBox(choicesList.get(j).getChoice());
						choiceBox.setTranslateX(20);
						allMCQchoices.add(choiceBox);
						
						quizVbox.getChildren().add(choiceBox);
					}
				}
				
				studentPage.getChildren().remove(selectQuizPane);
				studentPage.getChildren().add(takeQuizPane);
			});
			
			ScrollPane quizTakeScroll = new ScrollPane();
			quizTakeScroll.setMaxHeight(500);
			quizTakeScroll.setMaxWidth(500);
			quizTakeScroll.setContent(quizVbox);
			
			
			
			//Take quiz
			
			Label submition_status = new Label();
			submition_status.setFont(new Font("Calibri",16));
			submition_status.setTranslateY(295);
			
			Button btn_submit = new Button("Submit Quiz");
			btn_submit.setFont(new Font("Calibri",16));
			btn_submit.setTranslateY(295);
			btn_submit.setOnAction( e -> {
				submition_status.setText("Quiz Submited");
				btn_submit.setVisible(false);
				for(CheckBox cb : allMCQchoices) {
					if(cb.isSelected()) {
						for(MCQQuestion mcq: currentQuiz.getMcqQuestions()) {
							for(MCQChoice mcqc: mcq.getChoicesList()) {
								if(cb.getText().equals(mcqc.getChoice())){
									if(mcqc.isValid())
										cb.setTextFill(javafx.scene.paint.Color.GREEN);
									else
										cb.setTextFill(javafx.scene.paint.Color.RED);
								}
							}
						}
					}
				}
				for(int i = 0 ; i < allAnswers.size() ; i++) {
					if(!allAnswers.get(i).getText().equals(""))
						admin.UpdateAnswers(allAnswers.get(i).getText(),currentQuiz.getQuestions().get(i));
				}
			});
					
			studentLoginPane.getChildren().addAll(student_title,btn_back_to_title2,txt_studentId,txt_studentNameInput,btn_checkForQuiz,student_message);
			selectQuizPane.getChildren().addAll(student_select_title,quizSelectScroll,btn_startQuiz);
			takeQuizPane.getChildren().addAll(quizTakeScroll,btn_submit,submition_status);
			
			studentPage.getChildren().addAll(studentLoginPane);
			//STUDENT PAGE
			primaryStage.setTitle("Quiz Manager by Riad Sleiman");
			primaryStage.setScene(sceneTitle);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
