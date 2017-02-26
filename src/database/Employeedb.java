package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.Main;

public class Employeedb {

	public static Integer employeeIDdb;
	public static String firstdb;
	public static String lastdb;
	public static Integer agedb;
	public static String addLine1db;
	public static String towndb;
	public static String postcodedb;
	public static String numberdb;

	public static ResultSet readAll(){
		PreparedStatement EmpReadAll = Main.maindb.newQ("SELECT * FROM Employee ORDER by EmployeeID");
		ResultSet readEmployee = Main.maindb.runQuery(EmpReadAll);
		return readEmployee;
	}
//method to add new employee to the employee entity in the database
	public static void writeNew(String first, String second, Integer age, String addLine1, String town, String postcode, String phone){
		//tries to run this section of the code
		try {
			//The statement is used to insert a new employee with the variables that are parsed in from the method, this method also generates the employeeID, which is returned using Statement.RETURN_GENERATED_KEYS
			PreparedStatement addState = DatabaseConnect.connection.prepareStatement("INSERT INTO Employee(FirstName, Surname, Age, AddressLine1, TownOrCity, Postcode, Number) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			//adds the values of each variable into the database
			//done using set... to stop SQL injection
			addState.setString(1, first);
			addState.setString(2, second);
			addState.setObject(3, age);
			addState.setString(4, addLine1);
			addState.setString(5, town);
			addState.setString(6, postcode);
			addState.setString(7, phone);
			//updates the database
			addState.executeUpdate();
			//gets the generated keys from the database
			try (ResultSet generatedKeys = addState.getGeneratedKeys()) {
				//if there is a value on the result set than the if statement is ran
				if (generatedKeys.next()) {
					//sets the emlployeeIDdb from the auto generated keys
					employeeIDdb = generatedKeys.getInt(1);
					//else it throws a SQLException error
				}else {
					throw new SQLException("No ID obtained.");
				}
			}
			//catches any SQLException
		} catch (SQLException e) {
			//prints the error
			e.printStackTrace();
		}

	}
}