package sceneController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Hashing;
import application.Main;
import application.Reset;
import database.Employeedb;
import database.Logindb;
import database.Paydb;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class InsertController {
	private static Stage stage;
	public static int count = 0;
	
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
	@FXML ComboBox<String> cmb_pay;
	@FXML TextField txt_Pay;
	@FXML Button btn_Pay;
	@FXML CheckBox chk_Manager;
	@FXML Button btn_Submit;
	
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
			assert btn_Pay != null : "Pay Button was not loaded";		
			assert chk_Manager != null : "Manager check button was not loaded";
			assert btn_Submit != null : "Submit button was not loaded";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
		
		
	}
	
	public void prepareStageEvents(Stage insertStage){
		System.out.println("Preparing stage events");
		InsertController.stage = insertStage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Main.terminate();
			}
		});
		loadPay();
	}
	
	public void loadPay(){
		ObservableList<String> pay = FXCollections.observableArrayList();
		ResultSet rs = Paydb.loadPay();
		try {
			while(rs.next()){
				pay.add(rs.getString("PayPerHour"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cmb_pay.setItems(pay);
	}
	
	public void submitClick() throws Exception{
		boolean vCheck = false;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		if(txt_first.getText().isEmpty() || txt_second.getText().isEmpty() || txt_age.getText().isEmpty()){
			alert.setHeaderText("Missing Information!");
			alert.setContentText("Please enter in at least First Name, Surname, and Age!");
			alert.showAndWait();
			vCheck = false;
			return;
		}else{
			try{
				Integer.parseInt(txt_age.getText());
				vCheck = true;
			}catch(Exception e){
				vCheck = false;
				alert.setHeaderText("Wrong data!");
				alert.setContentText("Please enter a numerical value for Age!");
				alert.showAndWait();
				return;
			}
		}
		if(txt_UserName.getText().isEmpty() || pass_Password.getText().isEmpty() || pass_CPassword.getText().isEmpty()){
			vCheck = false;
			alert.setHeaderText("Something has gone wrong!");
			alert.setContentText("Please enter in all the fields!");
			alert.showAndWait();
		}else if(!((pass_Password.getText()).equals(pass_CPassword.getText()))){
			vCheck = false;
			alert.setHeaderText("Password fields do not match!");
			alert.setContentText("Make sure the password fields match!");
			alert.showAndWait();
		}else{
			vCheck = true;
		}
		
		if(vCheck == true){
			count++;
			if(count == 1){
				Employeedb.writeNew(txt_first.getText(), txt_second.getText(), Integer.parseInt(txt_age.getText()), txt_addLine1.getText(), txt_town.getText(), txt_postcode.getText(), txt_phone.getText());
			}
			Hashing.vHash = false;
			Logindb.managerdb = chk_Manager.isSelected();
			Logindb.usernamedb = txt_UserName.getText();
			Logindb.passworddb = Hashing.generateHash(pass_Password.getText(), null);
			if(Logindb.newLog() == false){
				return;
			}
			alert.setHeaderText("Returning");
			alert.setContentText("Try logging in now");
			alert.showAndWait();
			Reset.resetVar();
			Stage back = new Stage();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/scene/login.fxml"));
				back.setScene(new Scene(root));
				back.setTitle("Login");
				back.show();
				LoginController controller = new LoginController();
				controller.prepareStageEvents(back);
			} catch (IOException e) {
				e.printStackTrace();
			}
			stage.close();
		}
		
	}
}