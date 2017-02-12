package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.Hashing;
import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Logindb {
	public static boolean managerdb;
	public static String usernamedb;
	public static String passworddb;
	public static byte[] saltdb;
	public static boolean verified = false;
	public static String sSalt;

	public static boolean verify(String userName, String passWord) throws Exception{
		Hashing.vHash = true;
		PreparedStatement qState = Main.maindb.newQ("SELECT EmployeeID, Manager, UserName, PassWord, Salt FROM Login ORDER by EmployeeID");
		ResultSet run = Main.maindb.runQuery(qState);
		try{
			while(run.next()){
				Employeedb.employeeIDdb = run.getInt("EmployeeID");
				managerdb = run.getBoolean("Manager");
				usernamedb = run.getString("UserName");
				passworddb = run.getString("PassWord");
				saltdb = run.getBytes("Salt");
				if(usernamedb.equals(userName) && passworddb.equals(Hashing.generateHash(passWord, saltdb))){
					System.out.println("Login Successful!");
					verified = true;
					break;
				}
			}
		}catch(SQLException resultsexception){
			resultsexception.printStackTrace();
		}
		return verified;
	}

	public static boolean newLog(){
		
		try {
			PreparedStatement addState = DatabaseConnect.connection.prepareStatement("INSERT INTO Login(EmployeeID, Manager, UserName, PassWord, Salt) " +
					"VALUES(?, ?, ?, ?, ?)");
			addState.setInt(1, Employeedb.employeeIDdb);
			addState.setBoolean(2, managerdb);
			addState.setString(3, usernamedb);
			addState.setString(4, passworddb);
			addState.setString(5, sSalt);
			addState.executeUpdate();
		} catch (SQLException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Username clash!");
			alert.setContentText("Please enter a new username!");
			alert.showAndWait();
			return false;
		}
		return true;
	}
}