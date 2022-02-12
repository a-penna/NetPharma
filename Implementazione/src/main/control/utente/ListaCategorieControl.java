
package main.control.utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.bean.Categoria;
import main.model.CategoriaDAO;
import main.utils.Utility;

@WebServlet("/ListaCategorie")
public class ListaCategorieControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CategoriaDAO model = new CategoriaDAO(ds);
		JSONObject json = new JSONObject();
		
		try {
			Collection<Categoria> categorie = model.doRetrieveAll("nome");

			JSONArray jsArray = new JSONArray();
			Iterator<Categoria> it = categorie.iterator();
			while(it.hasNext()) {
				JSONArray categoriaArray = new JSONArray();
				Categoria categoria = it.next();
				categoriaArray.put(categoria.getId());
				categoriaArray.put(categoria.getNome());
				jsArray.put(categoriaArray);
			}
			json.put("listaCategorie", jsArray);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		catch (SQLException e1) {
			Utility.printSQLException(e1);
		}

		response.getWriter().print(json); 		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}


