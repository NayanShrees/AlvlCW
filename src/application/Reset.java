package application;

import database.Employeedb;
import database.Logindb;

public class Reset {
	public static void resetVar() {
		//reset all variables in Employeedb class
		Employeedb.employeeIDdb = null;
		Employeedb.firstdb = null;
		Employeedb.lastdb = null;
		Employeedb.agedb = null;
		Employeedb.addLine1db = null;
		Employeedb.towndb = null;
		Employeedb.postcodedb = null;
		Employeedb.numberdb = null;
		//resets all variables in Logindb class
		Logindb.managerdb = false;
		Logindb.usernamedb = null;
		Logindb.passworddb = null;
		Logindb.verified = false;
		//Resets all variables in the Hashing class
		Hashing.vHash = false;
	}
}