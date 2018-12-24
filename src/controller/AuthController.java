package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.SingletonConnection;

public class AuthController {
	
	public static boolean login(String email, String password) throws Exception {
		String stmt = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(1, email);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return true;
		} 
		return false;
	}
}
