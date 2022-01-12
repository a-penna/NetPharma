package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import bean.Categoria;
import bean.Prodotto;
import utils.Utility;

public class ProdottoDAO implements Model<Prodotto>{
    
	private DataSource ds = null;

	public ProdottoDAO(DataSource ds) {
		this.ds = ds;
	}

	public Prodotto doRetrieveByKey(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Prodotto bean = new Prodotto();

		String selectSQL = "SELECT * FROM prodotto WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(new Categoria(rs.getString("categoria")));
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

				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(new Categoria(rs.getString("categoria")));
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
		
		String insertSQL = "INSERT INTO Prodotto (nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, prodotto.getNome());
			preparedStatement.setString(2, prodotto.getMarchio());
			preparedStatement.setString(3, prodotto.getProduttore());
			preparedStatement.setString(4, prodotto.getFormato());
			preparedStatement.setString(5, prodotto.getDescrizione());
			preparedStatement.setInt(6, prodotto.getDisponibilita());
			preparedStatement.setBigDecimal(7, prodotto.getPrezzo());
			preparedStatement.setString(8, prodotto.getCategoria().getNome());
            preparedStatement.setBytes(9, prodotto.getFoto());

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Utility.printSQLException(e);
				}
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
	}


	public void doUpdate(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE prodotto SET marchio = ?, produttore = ?, formato = ?, descrizione = ?, disponibilita = ?, prezzo = ?, categoria = ?, foto = ? WHERE nome = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, prodotto.getMarchio());
			preparedStatement.setString(2, prodotto.getProduttore());
			preparedStatement.setString(3, prodotto.getFormato());
			preparedStatement.setString(4, prodotto.getDescrizione());
			preparedStatement.setInt(5, prodotto.getDisponibilita());
			preparedStatement.setBigDecimal(6, prodotto.getPrezzo());
			preparedStatement.setString(8, prodotto.getCategoria().getNome());
            preparedStatement.setBytes(8, prodotto.getFoto());
            preparedStatement.setString(9, prodotto.getNome());
            
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

	public void doDelete(Prodotto bean) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		String deleteSQL = "DELETE FROM Prodotto "
						+ "WHERE nome=?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			String prodotto = bean.getNome();
	
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, prodotto);
			
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

	
	public Collection<Prodotto> doRetrieveAllByCategoria(Categoria categoria) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		Collection<Prodotto> prodotti = new LinkedList<Prodotto>();
	
		String selectSQL = "SELECT * FROM prodotto WHERE categoria=?))";
	
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, categoria.getNome());
	
			ResultSet rs = preparedStatement.executeQuery();
	
			while (rs.next()) {
				Prodotto bean = new Prodotto();
				
				bean.setNome(rs.getString("nome"));
				bean.setMarchio(rs.getString("marchio"));
				bean.setProduttore(rs.getString("produttore"));
				bean.setFormato(rs.getString("formato"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setDisponibilita(rs.getInt("disponibilita"));
				bean.setPrezzo(rs.getBigDecimal("prezzo"));
				bean.setCategoria(new Categoria(rs.getString("categoria")));
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
}