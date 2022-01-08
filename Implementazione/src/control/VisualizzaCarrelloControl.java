package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Carrello;
import model.CarrelloDAO;
import utils.Utility;


@WebServlet("/VisualizzaCarrello")
public class VisualizzaCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!="true") { 
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
			return;
		}

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

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/carrello.jsp"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
