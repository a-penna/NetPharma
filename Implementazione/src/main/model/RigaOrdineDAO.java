package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import main.bean.RigaOrdine;
import main.utils.Utility;

public class RigaOrdineDAO {
	
	private DataSource ds = null;
	
	public RigaOrdineDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Collection<RigaOrdine> doRetrieveAllByOrder(String orderID) throws SQLException {
		Collection<RigaOrdine> lista = new LinkedList<RigaOrdine>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM riga_ordine WHERE ordine = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, orderID);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				RigaOrdine rigaOrdine = new RigaOrdine();
				rigaOrdine.setProdotto(rs.getInt("prodotto"));
				rigaOrdine.setOrdine(rs.getString("ordine"));
				rigaOrdine.setQuantity(rs.getInt("quantity"));
				rigaOrdine.setPrezzoAlPezzo(rs.getBigDecimal("prezzo_al_pezzo"));
				lista.add(rigaOrdine);
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
		return lista;
	}

	
	public Boolean doSave(RigaOrdine bean) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO riga_ordine(prodotto, ordine, quantity, prezzo_al_pezzo) "
						+ " VALUES (?,?,?,?)";
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, bean.getProdotto());
			preparedStatement.setString(2, bean.getOrdine());
			preparedStatement.setInt(3, bean.getQuantity());
			preparedStatement.setBigDecimal(4, bean.getPrezzoAlPezzo());
	
			preparedStatement.executeUpdate();
			
			connection.commit();
		} 
		catch(SQLException e) {
			Utility.printSQLException(e);
			return false;
		}
		
		
		return true;
	}
}
