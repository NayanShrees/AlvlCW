package application;

import database.DatabaseConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sceneController.LoginController;

public class Main extends Application{
	//Connect to database using the DatabaseConnect class
	public static DatabaseConnect maindb;
	//launches the program
	public static void main(String[] args) {
		launch(args); 
	}
	//starts here, creates a primary stage 
	public void start(Stage primaryStage) throws Exception {
		//runs what is inside, if there is a problem the program wont stop and it will do something else listed in the catch
		try{
			//setting database location
			maindb = new DatabaseConnect("MainDatabase.db");
			//Loading the scene to root
			Parent root = FXMLLoader.load(getClass().getResource("/scene/Login.fxml"));
			primaryStage.setTitle("Login");
			//loads the scene to the stage
			primaryStage.setScene(new Scene(root));
			//shows the stage with the scene
			primaryStage.show();
			System.out.println("Login Scene Showing");
			//making an object out of the LoginController which will be used to run methods
			LoginController controller = new LoginController();
			//runs the prepareStageEvents method while parse in the stage
			controller.prepareStageEvents(primaryStage);
			//catching any exit exceptions
		}catch(Exception ex){
			//prints out exit statement
			ex.printStackTrace();
			//terminates the program
			terminate();
		}
	}
	
	//this method is to shut down the program and closes the database connection.
	public static void terminate(){
		System.out.println("Closing down");
		//if statement to say that if the database isn't already closed down than it will close down
		if(maindb != null){
			//disconnecting from the database
			maindb.disconnect();
		}
		//exiting the application.
		System.exit(0);
	}
}