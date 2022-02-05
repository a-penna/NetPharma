package main.control.prodotto;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Categoria;
import main.model.CategoriaDAO;
import main.utils.Utility;

@WebServlet("/RimuoviCategoria")
public class RimuoviCategoriaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean loggedIn = request.getSession(false) != null && request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null;
		if(!loggedIn) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
			return;
		}
		
		if (request.getParameter("id") == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/gestoreCatalogo/rimuoviCategoria.jsp"));
			return;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CategoriaDAO model = new CategoriaDAO(ds);	
		//modificare con id
		//lista di categoria da passare in select in jsp
		try {
			Collection<Categoria> categorie = model.doRetrieveAll("nome");
			request.setAttribute("listaCategorie", categorie);
			Categoria bean = new Categoria();
			bean.setId(id);
			model.doDelete(bean);
	    	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
	    	return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
			return;
		}
	}
}


