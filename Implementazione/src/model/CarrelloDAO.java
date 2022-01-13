package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import bean.Carrello;
import bean.Prodotto;

public class CarrelloDAO {

	private DataSource ds = null;
	
	public CarrelloDAO(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Carrello doRetrieveByUsername(String username) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Carrello carrello = new Carrello();

		String selectSQL = "SELECT * FROM Prodotto, Carrello WHERE id=prodotto AND cliente = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto bean = new Prodotto();

				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("marchio"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setFoto(rs.getBytes("foto"));  

				carrello.setItem(bean, rs.getInt("quantita"));
			}
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

		return carrello;

	}
	
	public boolean updateQuantity(String username, int prodottoId, int quantity) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE Carrello SET quantita=? "
						 + "WHERE prodotto=? AND cliente=?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, prodottoId);
			preparedStatement.setString(3, username);

			int result = preparedStatement.executeUpdate();
			
			if (result != 1) 
				return false;
			
			connection.commit();
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

		return true;
	}
	
	public boolean removeProdotto(String username, int prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM Carrello "
						+ "WHERE prodotto=? AND cliente=?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto);
			preparedStatement.setString(2, username);
			
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				return false;
			}
			
			connection.commit();
		} 
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return true;
	}
	
	public boolean insertProdotto(String username, int prodotto, int quantity) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Carrello(cliente, prodotto, quantita) VALUES(?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, prodotto);
			preparedStatement.setInt(3, quantity);
			
			if (preparedStatement.executeUpdate() != 1) {
				return false;
			}
			
			connection.commit();
		} 
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return true;
	}
}
