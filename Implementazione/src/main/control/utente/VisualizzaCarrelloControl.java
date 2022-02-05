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

import main.bean.Carrello;
import main.model.CarrelloDAO;
import main.utils.Utility;


@WebServlet("/VisualizzaCarrello")
public class VisualizzaCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("clienteRoles")!="true") { 
			Carrello cart = (Carrello) request.getSession(false).getAttribute("carrello");
			if (cart == null) {
				cart = new Carrello();
				request.getSession(false).setAttribute("carrello", cart);
			}
			request.setAttribute("carrello", cart);
		} else {	
			String username = (String)request.getSession(false).getAttribute("user");
			
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			CarrelloDAO model = new CarrelloDAO(ds);
			try {
				Carrello cart = model.doRetrieveByUsername(username);
				request.setAttribute("carrello", cart); 
			} catch (SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
				return;
			}
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/carrello.jsp"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
