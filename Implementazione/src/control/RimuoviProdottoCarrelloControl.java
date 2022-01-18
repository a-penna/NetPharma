package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Carrello;
import bean.Prodotto;
import model.CarrelloDAO;
import utils.Utility;

@WebServlet("/RimuoviProdottoCarrello")
public class RimuoviProdottoCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int prodottoID;
		try {
			prodottoID = Integer.parseInt(request.getParameter("prodotto"));
		} catch (NumberFormatException e) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
			return;
		}
			
		if (request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!="true") { 
			Carrello cart = (Carrello) request.getSession(false).getAttribute("carrello");
			Prodotto p = new Prodotto();
			p.setId(prodottoID);
			cart.removeItem(p);
		} else if (request.getSession(false) != null) {
			String username = (String)request.getSession(false).getAttribute("user");

			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			CarrelloDAO model = new CarrelloDAO(ds);
			try {
				model.removeProdotto(username, prodottoID);
			} catch (SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
				return;
			}
		}
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/VisualizzaCarrello"));
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
