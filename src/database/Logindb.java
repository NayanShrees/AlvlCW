package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.Hashing;
import application.Main;

public class Logindb {
	public static boolean managerdb;
	public static String usernamedb;
	public static String passworddb;
	public static byte[] saltdb;
	public static boolean verified = false;
	public static String sSalt;

	public static boolean verify(String userName, String passWord) throws Exception{
		//sets the boolean hash as true as i want to verify the hash
		Hashing.vHash = true;
		//the SQL statement that i want the database to run
		PreparedStatement qState = Main.maindb.newQ("SELECT EmployeeID, Manager, UserName, PassWord, Salt FROM Login ORDER by EmployeeID");
		//gets the data from the database into a resultset
		ResultSet run = Main.maindb.runQuery(qState);
		//there can be many faults therefore the program tries this section and catches any database exception
		try{
			//while the resultset has another value it runs the section in the code
			while(run.next()){
				//assigns each value from the database to the variables
				Employeedb.employeeIDdb = run.getInt("EmployeeID");
				managerdb = run.getBoolean("Manager");
				usernamedb = run.getString("UserName");
				passworddb = run.getString("PassWord");
				saltdb = run.getBytes("Salt");
				//checks if the username from the database is equal to what the user entered
				if(usernamedb.equals(userName) && passworddb.equals(Hashing.generateHash(passWord, saltdb))){
					//if it is equal than the login is successful
					System.out.println("Login Successful!");
					//sets the verified variable to true so that it returns that it is true
					verified = true;
					//breaks the while loop so after the data is checked it doesn't need to loop unnecessarily
					break;
				}
			}
			//catches any database errors
		}catch(SQLException e){
			e.printStackTrace();
		}
		//returns the boolean variable
		return verified;
	}

	public static void newLog(){
		
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
			ex.printStackTrace();
		}
	}
}