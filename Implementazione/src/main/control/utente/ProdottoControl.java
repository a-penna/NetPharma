package main.control.utente;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/Prodotto")
public class ProdottoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("id") == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
			return;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO prodottoDAO = new ProdottoDAO(ds);
		
		try {
			Prodotto prodotto = prodottoDAO.doRetrieveByKey(id);
			
			request.setAttribute("prodotto", prodotto); 
		} catch (SQLException e) {
			Utility.printSQLException(e);
			return;
		}
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/prodotto.jsp"));
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
