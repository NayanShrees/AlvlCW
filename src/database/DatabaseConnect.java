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
		}catch(SQLException resultexception){
			System.out.println("Database error statement " + resultexception.getMessage());
		}
		return qState;
	}



	public ResultSet runQuery(PreparedStatement qState)
	{               
		try {            
			return qState.executeQuery();
		}
		catch (SQLException queryexception) 
		{
			System.out.println("Database query error: " + queryexception.getMessage());
			return null;
		}
	}
	
	public static ObservableList<ObservableList<String>> newTableView(String query,int numOfCols){

		ObservableList<ObservableList<String>> userData = FXCollections.observableArrayList();

		try{
			ResultSet rs = connection.createStatement().executeQuery(query);

			while(rs.next()){
				//Iterate Row
				ObservableList<String> innerlist = FXCollections.observableArrayList();
				
					for(int i = 1;i<numOfCols+1;i++){
						innerlist.add(rs.getString(i));
					}
				
					userData.add(innerlist);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error on Building Data");             
		}
		return userData;
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