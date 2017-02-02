package sceneController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.javafx.scene.layout.region.SliceSequenceConverter;

import application.Hashing;
import application.Main;
import database.Employeedb;
import database.Logindb;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {
	private static Stage stage;

	@FXML private AnchorPane p_StartP;
	@FXML private TextField txt_Name;
	@FXML private PasswordField pass_log;
	@FXML private Button btn_logSub;

	//tries to see if all the assets have loaded
	@FXML void initialize(){
		try{
			assert p_StartP != null : "Anchor pane was not loaded!";
			assert txt_Name != null : "Text field was not loaded!";
			assert pass_log != null : "Password field was not loaded!";
			assert btn_logSub != null : "Submit button was not loaded!";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
	}

	//tells the private stage that it is the primaryStage from the main method
	public void prepareStageEvents(Stage primaryStage){
		System.out.println("Preparing stage events");
		LoginController.stage = primaryStage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Main.terminate();
			}
		});
	}
	@FXML public void onEnter(ActionEvent ae) throws Exception{
		submitClick();
	}
	//handles the submit button
	public void submitClick() throws Exception{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText("Something has gone wrong!");
		alert.setContentText("Please enter in the correct details!");
		System.out.println("Submit Clicked");
		//if the text field username and password field password it sends an error
		if(txt_Name.getText().isEmpty() || pass_log.getText().isEmpty()){
			System.out.println("Error");
			alert.showAndWait();
		}else{
			String userName = txt_Name.getText();
			String passWord = pass_log.getText();
			Logindb.verify(userName, passWord);
			if(Logindb.verified == true){
			Parent root;
			try{
				if(Logindb.managerdb == true){
					root = FXMLLoader.load(getClass().getResource("/scene/Manager.fxml"));
				}else{
					root = FXMLLoader.load(getClass().getResource("/scene/Employee.fxml"));
				}
				Stage secStage = new Stage();
				secStage.setTitle(Logindb.usernamedb);
				secStage.setScene(new Scene(root));
				secStage.show();
				stage.close();
				if(Logindb.managerdb == true){
					ManagerController controller = new ManagerController();
					controller.prepareStageEvents(secStage);
				}else{
					EmployeeController controller = new EmployeeController();
					controller.prepareStageEvents(secStage);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				Main.terminate();
			}
			}else{
				System.out.println("Error");
				alert.showAndWait();
			}
		}
	}
}