package sceneController;
//importing necessary utilities
import application.Main;
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
	//creating a private stage
	private static Stage stage;
	//FXML contollers
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
	//If enter is pressed on any text field than it runs this method.
	@FXML public void onEnter(ActionEvent ae) throws Exception{
		//runs submitClick method;
		submitClick();
	}
	//handles the submit button
	public void submitClick() throws Exception{
		//Creates an alert popup
		Alert alert = new Alert(AlertType.WARNING);
		//sets the title of the popup
		alert.setTitle("Error");
		//sets the header of the popup
		alert.setHeaderText("Something has gone wrong!");
		//sets the main body text
		alert.setContentText("Please enter in the correct details!");
		System.out.println("Submit Clicked");
		//if the text field username and password field password is empty it sends an error
		if(txt_Name.getText().isEmpty() || pass_log.getText().isEmpty()){
			System.out.println("Error");
			//popup is shown
			alert.showAndWait();
			//if the text field isn't empty than it runst his chunk of code
		}else{
			//gets the text of all the textfield to two string variables
			String userName = txt_Name.getText();
			String passWord = pass_log.getText();
			//submits these two string variables to the verigy method in LOgindb
			Logindb.verify(userName, passWord);
			//if the public variable verified isn't true
			if(Logindb.verified == true){
				Parent root;
				try{
					//if the user is a manager than it loads the manager scene
					if(Logindb.managerdb == true){
						root = FXMLLoader.load(getClass().getResource("/scene/Manager.fxml"));
					}else{
						//if the user is an employee it loads the employee scene
						root = FXMLLoader.load(getClass().getResource("/scene/Employee.fxml"));
					}
					//creates a new scene with the username as the title
					Stage secStage = new Stage();
					secStage.setTitle(Logindb.usernamedb);
					//sets the scene for the stage
					secStage.setScene(new Scene(root));
					//shows the stage
					secStage.show();
					//closes the current stage
					stage.close();
					//if the user is a manager than the it runs the prepare method in the ManagerController class
					if(Logindb.managerdb == true){
						ManagerController controller = new ManagerController();
						controller.prepareStageEvents(secStage);
					}else{
						//if the user is an employee than it instead runs the prepare method in the EmployeeController
						EmployeeController controller = new EmployeeController();
						controller.prepareStageEvents(secStage);
					}
				}catch(Exception ex){
					//if anything goes wrong than the error is printed in the console and the program shuts down
					ex.printStackTrace();
					Main.terminate();
				}
				//if the login isn't verified than it runs this section of the code
			}else{
				//sets the header text of the dialog 
				alert.setHeaderText("Wrong Username and Password!");
				//sets the body text of the dialog
				alert.setContentText("Please enter in the correct details!");
				//shows the alert
				alert.showAndWait();
			}
		}
	}
}