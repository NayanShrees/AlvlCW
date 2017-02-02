package database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.Main;

public class Paydb {
	public static BigDecimal pay;
	public static int payID;
	
	public static ResultSet loadPay(){
		PreparedStatement qState = Main.maindb.newQ("SELECT PayID, PayPerHour From PayStat ORDER by PayID");
		ResultSet run = Main.maindb.runQuery(qState);
		return run;
	}
}