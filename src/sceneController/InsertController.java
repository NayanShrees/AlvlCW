package sceneController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.Hashing;
import application.Main;
import application.Reset;
import database.DatabaseConnect;
import database.Employeedb;
import database.Logindb;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class InsertController {
	private static Stage stage;
	//FXML containers
	@FXML AnchorPane p_insert;
	@FXML Label lbl_title;
	@FXML Label lbl_first;
	@FXML Label lbl_second;
	@FXML Label lbl_age;
	@FXML Label lbl_addLine1;
	@FXML Label lbl_town;
	@FXML Label lbl_postcode;
	@FXML Label lbl_phone;
	@FXML TextField txt_first;
	@FXML TextField txt_second;
	@FXML TextField txt_age;
	@FXML TextField txt_addLine1;
	@FXML TextField txt_town;
	@FXML TextField	txt_postcode;
	@FXML TextField txt_phone;
	@FXML Label lbl_Username;
	@FXML Label lbl_Password;
	@FXML Label lbl_CPassword;
	@FXML TextField txt_UserName;
	@FXML PasswordField pass_Password;
	@FXML PasswordField pass_CPassword;
	@FXML Label lbl_Pay;
	@FXML ChoiceBox<String> cmb_pay;
	@FXML TextField txt_Pay;
	@FXML RadioButton rad_pay;
	@FXML CheckBox chk_Manager;
	@FXML Button btn_Submit;
	//initialising all controls
	@FXML void initialize(){
		try{
			assert p_insert != null : "AnchorPane was not loaded";
			assert lbl_title != null : "Title label was not loaded";
			assert lbl_first != null : "Title label was not loaded";
			assert lbl_second != null : "Username label was not loaded";
			assert lbl_age != null : "Password label was not loaded";
			assert lbl_addLine1 != null : "Confirm Password label was not loaded";
			assert lbl_town != null : "Username TextField was not loaded";
			assert lbl_postcode != null : "Password PasswordField was not loaded";
			assert lbl_phone != null : "Confirm PasswordField was not loaded";
			assert txt_first != null : "Manager check button was not loaded";
			assert txt_second != null : "Submit button was not loaded";
			assert txt_age != null : "Confirm Password label was not loaded";
			assert txt_addLine1 != null : "Username TextField was not loaded";
			assert txt_town != null : "Password PasswordField was not loaded";
			assert txt_postcode != null : "Confirm PasswordField was not loaded";
			assert txt_phone != null : "Manager check button was not loaded";
			assert lbl_Username != null : "Username label was not loaded";
			assert lbl_Password != null : "Password label was not loaded";
			assert lbl_CPassword != null : "Confirm Password label was not loaded";
			assert txt_UserName != null : "Username TextField was not loaded";
			assert pass_Password != null : "Password PasswordField was not loaded";
			assert pass_CPassword != null : "Confirm PasswordField was not loaded";
			assert lbl_Pay != null : "Pay label was not loaded";
			assert cmb_pay != null : "Pay ComboBox was not loaded";
			assert txt_Pay != null : "Pay TextField was not loaded";
			assert rad_pay != null : "Pay Button was not loaded";		
			assert chk_Manager != null : "Manager check button was not loaded";
			assert btn_Submit != null : "Submit button was not loaded";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
		//Statement to get the pay from the PayID database entity
		PreparedStatement qState = Main.maindb.newQ("SELECT PayID, PayPerHour From PayStat ORDER by PayID");
		//the result set rs has the data that is outputed from the database into it
		ResultSet rs = Main.maindb.runQuery(qState);
		//creates an observable list, which is used to show data in a choicebox
		ObservableList<String> pay = FXCollections.observableArrayList();
		//tries to run this code without breaking the program
		try {
			//while the result set rs has another piece of data it carries on
			while(rs.next()){
				//adds all values that has the PayPerHour column with £ added before the values
				pay.add("£" + rs.getString("PayPerHour"));
			}
			//if the program catches any errors than it prints the error
		} catch (Exception e) {
			e.printStackTrace();
		}
		//sets the items in the choice box from the observable list
		cmb_pay.setItems(pay);
		//automatically selects the first option
		cmb_pay.getSelectionModel().selectFirst();
		//creating tool tips to let the user know what each item does
		cmb_pay.setTooltip(new Tooltip("Select pay per week"));
		rad_pay.setTooltip(new Tooltip("Choose to add a new amount or select a preset"));
		txt_Pay.setTooltip(new Tooltip("Add a new pay"));
		//The listener checks if at anypoint in the program the rad_pay is selected, if it is it runs the method in the listemer
		rad_pay.selectedProperty().addListener(new ChangeListener<Boolean>(){
			//method to see if the radio button is checked
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				//if the radio button is selected than it disable the choice box and enables the text field to add pay
				if (isNowSelected) { 
					System.out.println("Selected");
					txt_Pay.setDisable(false);
					cmb_pay.setDisable(true);
				} else {
					//if the radio button isn't selected than it disables the text box so that they can only select from the choice box
					System.out.println("Unselected");
					txt_Pay.setDisable(true);
					cmb_pay.setDisable(false);
				}
			}
		});
	}
	//prepares the stage by getting the stage it is showing now to the private variable stage so that it can be referenced easily
	public void prepareStageEvents(Stage insertStage){
		System.out.println("Preparing stage events");
		//making the current stage this stage
		InsertController.stage = insertStage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			//if the program gets the event windowEvent which is to close the program than it runs the terminate method in main
			public void handle(WindowEvent we) {
				Main.terminate();
			}
		});
	}
	//method which is run if the user presses submit
	public void submitClick() throws Exception{
		//boolean variable to see if all data has been inputed correctly
		boolean vCheck = false;
		//variable to have the user's pay set
		String pay = null;
		//creating a new alert, so that if anything goes wrong than the program can output a dialog
		Alert alert = new Alert(AlertType.WARNING);
		//setting the title as warning
		alert.setTitle("Warning");
		//checks if any of the text fields are empty
		if(txt_first.getText().isEmpty() || txt_second.getText().isEmpty() || txt_age.getText().isEmpty()){
			//if the textfields are empty than it sends an alert saying that one of the text fields are empty
			alert.setHeaderText("Missing Information!");
			alert.setContentText("Please enter in at least First Name, Surname, and Age!");
			alert.showAndWait();
			//sets the check as false so that the program doesn't accept it later on
			vCheck = false;
			//returns so that no more of the code can be ran
			return;
			//it then checks to see if the age is a number
		}else{
			//tries to parse the textfields value as integer
			try{
				Integer.parseInt(txt_age.getText());
				//if it has got to this point it means that the user has entered correct info so far so the boolean is set to true
				vCheck = true;
				//while trying to parse the text as integer if it fails than this section is ran without breaking the program 
			}catch(Exception e){
				//it sets the check as false as if it is running this section of the code it means it has failed due to the text not being integer
				vCheck = false;
				//shows the alert with relevant information so that the user can fix their mistake
				alert.setHeaderText("Wrong data!");
				alert.setContentText("Please enter a numerical value for Age!");
				alert.showAndWait();
				//returns so that the program doesn't continue
				return;
			}
		}
		//it then checks if the username, password, and confirm password isn't empty
		if(txt_UserName.getText().isEmpty() || pass_Password.getText().isEmpty() || pass_CPassword.getText().isEmpty()){
			//if it is than it sets the check as false and shows the alert that the textfields are empty
			vCheck = false;
			alert.setHeaderText("Something has gone wrong!");
			alert.setContentText("Please enter in all the fields!");
			alert.showAndWait();
			//returns so that no more of the code is ran
			return;
			//it then checks if the text in password and confirm password is the same
		}else if(!((pass_Password.getText()).equals(pass_CPassword.getText()))){
			//it it isn't, it sets to false, and tells the user that the password and confirm password isn't the same
			vCheck = false;
			alert.setHeaderText("Password fields do not match!");
			alert.setContentText("Make sure the password fields match!");
			alert.showAndWait();
			//returns so that the program doesn't carry on
			return;
		}else{
			//if it goes through all of these verification than it sets the check as true
			vCheck = true;
		}
		//payID is to assign the new employee a pay
		int payID = 0;
		//statement that will be used to talk with the database
		PreparedStatement queryState;
		//checks if the radio button is selected
		if(rad_pay.isSelected() == false){
			//if the radio button isn't selected than the user is selecting a pre-made pay
			//therefore it needs to check which payID the user has selected, which therefore requires this SQL statement
			queryState = Main.maindb.newQ("SELECT PayID, PayPerHour FROM PayStat ORDER By PayID");
			//the result of the query is then stored in the ResultSet
			ResultSet rsPay = Main.maindb.runQuery(queryState);
			//the value that is retrieved from the choice box is then assigned to the string pay
			pay = cmb_pay.getValue();
			//this replaces the £ with nothing so it is just a string value
			pay = pay.replace("£", "");
			//tries to run the result set
			try{
				//while the result set has another value it runs the code
				while(rsPay.next()){
					//if the string pay equals the data from the database under the column PayPerHour it runs the code
					if(pay.equals(rsPay.getString("PayPerHour"))){
						//if it is the same the pay ID is set to pay ID
						payID = rsPay.getInt("PayID");
						//it then sets the check as true so that the program realises it has verified
						vCheck = true;
						//breaks the while loop so that the program doesn't need to unnecessarily compute
						break;
						//if for any reason the program doesn't find the combo box it sets the verify as false
					}else{
						vCheck = false;
					}
				}
				//catches any SQL errors
			}catch(SQLException e){
				//prints the SQL error
				e.printStackTrace();
			}
			//If radio button is selected than the program needs to add the pay
		}else{
			//firstly it checks if the text box is empty
			if(txt_Pay.getText().isEmpty()){
				//shows the dialog so that user can add the pay
				alert.setTitle("Pay Error!");
				alert.setContentText("Please enter a value for pay");
				alert.showAndWait();
				//returns so that no more of the code is carried on
				return;
				//if the text box isn't empty than it runs the next part of the code
			}else{
				//gets the text from the text box txt_pay
				pay = txt_Pay.getText();
				//replaces any £ with nothing so that it would just be a value
				pay = pay.replace("£", "");
				//New query so that it checks that the pay isn't already in the database
				queryState = Main.maindb.newQ("SELECT PayID, PayPerHour FROM PayStat ORDER By PayID");
				//gets the data from the database into the Result Set
				ResultSet rs = Main.maindb.runQuery(queryState);
				//while another value is next it runst the code
				while(rs.next()){
					//if the pay is euqal to the database pay than it sets the check as false and tells the user the pay is already in the database
					if(pay.equals(rs.getString("PayPerHour"))){
						vCheck = false;
						alert.setHeaderText("Pay Clash!");
						alert.setContentText("Pay per hour is already an option, please select the pay or enter new pay!");
						alert.showAndWait();
						//stops the program running so that no more of the code is ran
						return;
					}else{
						//the check is set to true as it has been verified that there is nothing wrong with the user input
						vCheck = true;
					}
				}
			}
			//It than checks if the check is true, if the program at any point has been verified than it doesnt run this section.
			if(vCheck == true){
				//it checks to see if their is a username clash using this SQl statement
				PreparedStatement qState = Main.maindb.newQ("SELECT UserName FROM Login");
				//gets the database data into a result set
				ResultSet run = Main.maindb.runQuery(qState);
				//tries to run this code
				try{
					//while theres another value for the resultset
					while(run.next()){
						//if the username is the same as another than it outputs a dialog saying that the user should change it
						if(txt_UserName.getText().equals(run.getString("UserName"))){
							alert.setHeaderText("Username Clash!");
							alert.setContentText("Please enter in a new Username!");
							alert.showAndWait();
							//returns so that the program doesn't carry on
							return;
						}
					}
					//catches any SQL exception
				}catch (SQLException e) {
					//prints the error
					e.printStackTrace();
				}
				//sends the data from the textfield to create an employee
				Employeedb.writeNew(txt_first.getText(), txt_second.getText(), Integer.parseInt(txt_age.getText()), txt_addLine1.getText(), txt_town.getText(), txt_postcode.getText(), txt_phone.getText());
				//sets the hash boolean to false so the program can create a hash instead of verifying
				Hashing.vHash = false;
				//sets the variable to true or false depending on if the check box is selected
				Logindb.managerdb = chk_Manager.isSelected();
				//sets the username and password to the textfield
				Logindb.usernamedb = txt_UserName.getText();
				//sets the password to the hashed version of the password hash
				Logindb.passworddb = Hashing.generateHash(pass_Password.getText(), null);
				//runs the newLog method so that it creates a new login user with the employeeID generated from the Employeedb.writeNew method
				Logindb.newLog();
				//if the radio button isn't selected than the user wants to select a pre-made pay
				if(rad_pay.isSelected() == false){
					//tries to assign the value of the selected payID to the new employee
					try{
						//statement to add the employeeID and payID into the Pay entity
						queryState = DatabaseConnect.connection.prepareStatement("INSERT INTO Pay(EmployeeID, PayID) " +
								"VALUES(?, ?)");
						//I'm adding the values this way so that SQL injection can be stopped
						queryState.setInt(1, Employeedb.employeeIDdb);
						queryState.setInt(2, payID);
						//updates the database
						queryState.executeUpdate();
						//catches any SQL exception
					}catch(SQLException e){
						//prints the error
						e.printStackTrace();
					}
					//if the user wanted to add a pay than it runs this section
				}else{
					//tries to insert a new PayPerHour while generating a new payID
					try{
						//statement to add the Pay and auto-generate payID
						PreparedStatement addState = DatabaseConnect.connection.prepareStatement("INSERT Into PayStat(PayPerHour) " +
								"Values(?)", Statement.RETURN_GENERATED_KEYS);
						//used to stop SQL injection
						addState.setString(1, pay);
						//updates the database
						addState.executeUpdate();
						//gets the auto-generated value from the database
						try(ResultSet GeneratedKeys = addState.getGeneratedKeys()){
							//gets the ID that is generated into payID
							if(GeneratedKeys.next()){
								payID = GeneratedKeys.getInt(1);
							}else{
								//if there is no generated keys than it throws the SQLException no id obtained
								throw new SQLException("No ID obtained");
							}
						}
						//tries to assign the employee to the payID
						try{
							//statement to insert values into pay
							qState = DatabaseConnect.connection.prepareStatement("INSERT INTO Pay(EmployeeID, PayID) " +
									"VALUES(?, ?)");
							//used to stop SQL injection
							qState.setInt(1, Employeedb.employeeIDdb);
							qState.setInt(2, payID);
							//updates the database
							qState.executeUpdate();
							//catches any SQL errors
						}catch(SQLException e){
							//prints the error
							e.printStackTrace();
						}
						//if anything went wrong in the whole of it, it instead runs this to stop the program crashing
					}catch(SQLException e){
						//prints the error
						e.printStackTrace();
					}
				}
				//alert to tell the user that it will now be going back to the login scene
				alert.setHeaderText("Returning");
				alert.setContentText("Try logging in now");
				alert.showAndWait();
				//resets all variables so that it is effectively a new instance of the program
				Reset.resetVar();
				//new stage to return to the login scene
				Stage back = new Stage();
				//tries to run this section of the code
				try {
					//loads the scene
					Parent root = FXMLLoader.load(getClass().getResource("/scene/login.fxml"));
					//sets the scene
					back.setScene(new Scene(root));
					//sets the title
					back.setTitle("Login");
					//shows the stage
					back.show();
					//new object to run the methods in a class
					LoginController controller = new LoginController();
					//runs the method while parsing the current stage
					controller.prepareStageEvents(back);
					//any exception is caught by the program
				} catch (IOException e) {
					//prints the error
					e.printStackTrace();
				}
				//closes the current stage
				stage.close();
			}

		}
	}
}