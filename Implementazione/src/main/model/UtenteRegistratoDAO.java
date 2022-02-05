package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UtenteRegistratoDAO{

	private DataSource ds = null;
	
	public UtenteRegistratoDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public boolean checkEmail(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM Utente_registrato "
						 + "WHERE email = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
			rs.close();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return false;
	}	
}
