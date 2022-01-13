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
import model.ProdottoDAO;
import utils.Utility;

@WebServlet("/AggiungiProdottoCarrello")
public class AggiungiProdottoCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prodottoID = request.getParameter("prodotto");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		if (request.getSession().getAttribute("clienteRoles")!="true") { 
			ProdottoDAO prodottoModel = new ProdottoDAO(ds);
			Prodotto p = null;
			try {
				p = prodottoModel.doRetrieveByKey(prodottoID);
			} catch (SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
				return;
			}

			Carrello cart = (Carrello) request.getSession(false).getAttribute("carrello");
			if (cart == null) {
				cart = new Carrello();
				request.getSession(false).setAttribute("carrello", cart);
			}
			cart.setItem(p, quantity);
		} else {
			String username = (String)request.getSession(false).getAttribute("user");

			CarrelloDAO model = new CarrelloDAO(ds);
			try {
				boolean added = model.insertProdotto(username, Integer.parseInt(prodottoID), quantity);
				if (!added) {
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
					return;
				}
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
