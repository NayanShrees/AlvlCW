package sceneController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import application.Main;

public class Pay {
	public int PayID;
	public String PayPerHour;
	
	public Pay(int PayID, String PayPerHour) {
		this.PayID = PayID;
		this.PayPerHour = PayPerHour;
	}
	
	@Override
	public String toString(){
		return PayPerHour;
	}
	
	public static void readAll(List<Pay> list){
		list.clear();
		
		PreparedStatement qState = Main.maindb.newQ("SELECT PayID, PayPerHour From PayStat ORDER by PayID");
		ResultSet rs = Main.maindb.runQuery(qState);
		
		try{
			while(rs.next()){
				list.add(new Pay(rs.getInt("PayID"), rs.getString("PayPerHour")));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
