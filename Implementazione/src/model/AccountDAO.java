package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import bean.Account;

public class AccountDAO {

	private DataSource ds = null;
	
	public AccountDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Account authenticate(String username, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Account bean = null;
		
		String selectSQL = "SELECT * FROM Account "
						 + "WHERE username = ? AND password = MD5(?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				bean = new Account(rs.getInt("id"),rs.getString("username"), rs.getString("password"));
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
		return bean;
	}

	public boolean checkUsername(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM Account "
						 + "WHERE username = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			
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
