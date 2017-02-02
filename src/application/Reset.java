package application;

import database.Employeedb;
import database.Logindb;
import sceneController.InsertController;

public class Reset {
	public static void resetVar() {
		Employeedb.employeeIDdb = null;
		Employeedb.firstdb = null;
		Employeedb.lastdb = null;
		Employeedb.agedb = null;
		Employeedb.addLine1db = null;
		Employeedb.towndb = null;
		Employeedb.postcodedb = null;
		Employeedb.numberdb = null;

		Logindb.managerdb = false;
		Logindb.usernamedb = null;
		Logindb.passworddb = null;
		Logindb.verified = false;

		Hashing.vHash = false;
		
		InsertController.count = 0;
	}
}