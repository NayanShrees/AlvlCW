package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;

public class DatabaseConnect {
	//variable with the connection data type to connect to the database
	public static Connection connection;

	//constructor that starts by trying to connect to the database drivers and then tries to connect to the database.
	public DatabaseConnect(String dbConnect){
		try{
			//used to specify the database driver and location of the database and name.
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbConnect);
			System.out.println("Database connected.");
			//if the class is not found than this is what it does.
		}catch(ClassNotFoundException cnfex){
			System.out.println("Class not found exception: " + cnfex.getMessage());
			//if there is any other problem than this runs
		}catch(SQLException exception){
			System.out.println("Database connection failed: " + exception.getMessage());
		}
	}

	//SQL statement method
	public PreparedStatement newQ(String Query){
		//cleare's the statement
		PreparedStatement qState = null;
		//tries to run the query
		try{
			qState = connection.prepareStatement(Query);
			//catches any sql error
		}catch(SQLException e){
			//prints any error
			System.out.println("Database error statement " + e.getMessage());
		}
		//returns the prepared Statement
		return qState;
	}
	//Method to get the result set from the data
	public ResultSet runQuery(PreparedStatement qState)
	{               
		//tries to return the prepared statement as a result set after the query is executed
		try {            
			return qState.executeQuery();
			//catches any SQL Errors
		}catch (SQLException e) {
			//prints the errors
			System.out.println("Database query error: " + e.getMessage());
			//returns nothing
			return null;
		}
	}
	
	public static ObservableList<ObservableList<String>> newTableView(String query,int numOfCols){
		//observable list to hold data from database
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
		//tries to run the code
		try{
			//Result Set to run the query that is parsed in
			ResultSet rs = connection.createStatement().executeQuery(query);
			//while there is a next value in the result set
			while(rs.next()){
				//Iterate Row
				ObservableList<String> innerlist = FXCollections.observableArrayList();
					//used to have data added to its respective columns
					for(int i = 1;i<numOfCols+1;i++){
						//adds the data from the respective columns into the observable list
						innerlist.add(rs.getString(i));
					}
					//adds each colums data into the observable lise
					data.add(innerlist);
			}
			//if there is any error it is caught here
		}catch(Exception e){
			//prints the error
			e.printStackTrace();
			System.err.println("Error on Building Data");             
		}
		//returns the observable list so that it can be added to the table
		return data;
	}
	
	public void disconnect()
	{
		System.out.println("Disconnecting from database.");
		try {
			if (connection != null) connection.close();                        
		} 
		catch (SQLException finalexception) 
		{
			System.out.println("Database disconnection error: " + finalexception.getMessage());
		}        
	}
}