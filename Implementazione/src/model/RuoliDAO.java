package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


import bean.Ruoli;


public class RuoliDAO {

	private DataSource ds = null;
	
	public RuoliDAO(DataSource ds) {
		this.ds = ds;
	}
	
	private Ruoli.Ruolo enumRole(String role) {
		if (role == "CL") 
			return Ruoli.Ruolo.CL;
		else if (role == "GO") 
			return Ruoli.Ruolo.GO;
		if (role == "GC") 
			return Ruoli.Ruolo.GC;
		return null;
	}
	
	public Ruoli doRetrieveByAccount(int account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Ruoli bean = new Ruoli();
		bean.setAccount(account);
		
		String selectSQL = "SELECT * FROM Ruoli "
						 + "WHERE account = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, account);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				bean.addRuolo(enumRole(rs.getString("ruolo")));
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
}
