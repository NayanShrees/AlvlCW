package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

	public static void writeNew(String first, String second, Integer age, String addLine1, String town, String postcode, String phone){
		try {
			PreparedStatement addState = DatabaseConnect.connection.prepareStatement("INSERT INTO Employee(FirstName, Surname, Age, AddressLine1, TownOrCity, Postcode, Number) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			addState.setString(1, first);
			addState.setString(2, second);
			addState.setObject(3, age);
			addState.setString(4, addLine1);
			addState.setString(5, town);
			addState.setString(6, postcode);
			addState.setString(7, phone);
			addState.executeUpdate();

			try (ResultSet generatedKeys = addState.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					employeeIDdb = generatedKeys.getInt(1);
				}else {
					throw new SQLException("No ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ObservableList<ObservableList<String>> newTableView(String query,int numOfCols){

		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		try{
			ResultSet rs = DatabaseConnect.connection.createStatement().executeQuery(query);

			while(rs.next()){
				//Iterate Row
				ObservableList<String> innerlist = FXCollections.observableArrayList();
				if(employeeIDdb.equals(rs.getInt("EmployeeID"))){
					for(int i = 1;i<numOfCols+1;i++){
						innerlist.add(rs.getString(i));
					}
				}
				data.add(innerlist);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error on Building Data");             
		}
		return data;
	}
}