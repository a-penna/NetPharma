package main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import main.bean.Prodotto;
import main.utils.Utility;

public class ProdottoDAO {
    
	private DataSource ds = null;

	public ProdottoDAO(DataSource ds) {
		this.ds = ds;
	}

	public Prodotto doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Prodotto bean = new Prodotto();

		String selectSQL = "SELECT * FROM prodotto "
						 + "LEFT JOIN categoria ON prodotto.categoria = categoria.id "
						 + "WHERE prodotto.id=?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(rs.getString(12));
				bean.setFoto(rs.getBytes("foto"));
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

		return bean;
	}

	private boolean checkOrder(String order) {
		return order != null 
			   && !order.equals("") 
			   && (order.equals("nome") 
			   || order.equals("marchio") 
			   || order.equals("produttore") 
			   || order.equals("formato") 
			   || order.equals("descrizione") 
			   || order.equals("disponibilita DESC") 
			   || order.equals("prezzo DESC")
			   || order.equals("categoria") 
			   || order.equals("foto"));
	}
	
	public Collection<Prodotto> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Prodotto> prodotti = new LinkedList<Prodotto>();

		String selectSQL = "SELECT * FROM prodotto";

		if (checkOrder(order)) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto bean = new Prodotto();

				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setFoto(rs.getBytes("foto"));  //gestiamo foto come array di byte

				prodotti.add(bean);
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

		return prodotti;

	}

	public void doSave(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Prodotto (id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, prodotto.getId());
			preparedStatement.setString(2, prodotto.getNome());
			preparedStatement.setString(3, prodotto.getMarchio());
			preparedStatement.setString(4, prodotto.getProduttore());
			preparedStatement.setString(5, prodotto.getFormato());
			preparedStatement.setString(6, prodotto.getDescrizione());
			preparedStatement.setInt(7, prodotto.getDisponibilita());
			preparedStatement.setBigDecimal(8, prodotto.getPrezzo());
            preparedStatement.setBytes(9, prodotto.getFoto());

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Utility.printSQLException(e);
				}
			}

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
	}


	public void doUpdate(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE prodotto SET nome = ?, marchio = ?, produttore = ?, formato = ?, descrizione = ?, disponibilita = ?, prezzo = ?, foto = ? WHERE id = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, prodotto.getNome());
			preparedStatement.setString(2, prodotto.getMarchio());
			preparedStatement.setString(3, prodotto.getProduttore());
			preparedStatement.setString(4, prodotto.getFormato());
			preparedStatement.setString(5, prodotto.getDescrizione());
			preparedStatement.setInt(6, prodotto.getDisponibilita());
			preparedStatement.setBigDecimal(7, prodotto.getPrezzo());
            preparedStatement.setBytes(8, prodotto.getFoto());
            preparedStatement.setInt(9, prodotto.getId());
            
			preparedStatement.executeUpdate();

			connection.commit();
		}catch(SQLException e){ System.out.println(e);
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
	}
	
	public void updateCategoria(int prodottoID, String categoria) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE prodotto SET categoria = (SELECT id FROM Categoria WHERE nome = ?) WHERE id = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, categoria);
            preparedStatement.setInt(2, prodottoID);
            
			preparedStatement.executeUpdate();

			connection.commit();
		}catch(SQLException e){ 
			System.out.println(e);
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
	}

	public void doDelete(Prodotto bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		String deleteSQL = "DELETE FROM Prodotto "
						+ "WHERE id=?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			int prodotto = bean.getId();
	
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto);
			
			int result = preparedStatement.executeUpdate();
			if (result != 1) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Utility.printSQLException(e);
				}
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
	}

	
	public Collection<Prodotto> doRetrieveAllByCategoria(int categoria) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Prodotto> prodotti = new LinkedList<Prodotto>();
	
		String selectSQL = "SELECT * FROM prodotto WHERE categoria=?";
		
		try {
			connection = ds.getConnection();
		
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, categoria);
	
			ResultSet rs = preparedStatement.executeQuery();
	
			while (rs.next()) {
				Prodotto bean = new Prodotto();
				
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setFoto(rs.getBytes("foto"));  //gestiamo foto come array di byte
				
				prodotti.add(bean);
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
		return prodotti;
	}	
	

	public Collection<Prodotto> doRicerca(String nome, String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Prodotto> prodotti = new LinkedList<Prodotto>();
		nome = "%"+nome+"%";

		String selectSQL = "SELECT * FROM prodotto "
						 + "LEFT JOIN categoria ON prodotto.categoria = categoria.id "
						 + "WHERE prodotto.nome LIKE ?"; 
		
		if (checkOrder(order)) {
			selectSQL += " ORDER BY prodotto." + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome);

			ResultSet rs = preparedStatement.executeQuery();
 
			while (rs.next()) {
				Prodotto bean = new Prodotto();
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(rs.getString(12));
				bean.setFoto(rs.getBytes("foto"));
				
				prodotti.add(bean);
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
		
		return prodotti;
	}
	

	public Collection<Prodotto> doRetrieveSvincolati() throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<Prodotto> prodotti = new LinkedList<Prodotto>();
	
		String selectSQL = "SELECT * FROM prodotto WHERE categoria IS NULL";
	
		try {
			connection = ds.getConnection();
		
			preparedStatement = connection.prepareStatement(selectSQL);
	
			ResultSet rs = preparedStatement.executeQuery();
	
			while (rs.next()) {
				Prodotto bean = new Prodotto();
				
				bean.setId(rs.getInt("id"));
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(rs.getString("categoria"));
				bean.setFoto(rs.getBytes("foto"));  //gestiamo foto come array di byte
				
				prodotti.add(bean);
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
		return prodotti;
	}	
	
	public boolean checkProdotto(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM Prodotto "
						 + "WHERE id = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, codice);
			
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