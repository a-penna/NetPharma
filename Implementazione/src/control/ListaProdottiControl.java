package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Categoria;
import bean.Prodotto;
import utils.Utility;

@WebServlet("/ProdottiControl")
public class ListaProdottiControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoria = request.getParameter("categoria");
		Categoria c = new Categoria(categoria);
		
		if (categoria == null) {
		 	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ProdottiControl"));
		 	return;
		}
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);
		
		try {
			Collection<Prodotto> prodotti = model.doRetrieveAllByCategoria(c);

			request.setAttribute("prodotti", prodotti); 
			request.setAttribute("categoria", categoria);
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
			return;
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/listaProdotti.jsp"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
