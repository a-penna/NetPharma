package main.control.utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Prodotto;
import main.model.ProdottoDAO;
import main.utils.Utility;

@WebServlet("/RicercaProdotto")
public class RicercaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("cerca-per-prodotto");
		
		if (nome == null) {
		 	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
		 	return;
		}
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);
		

		try {
			Collection<Prodotto> prodotti = model.doRicerca(nome, "nome");
			request.setAttribute("prodotti", prodotti); 
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
			return;
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ricercaView.jsp"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
