package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import bean.Carrello;
import bean.Prodotto;
import model.CarrelloDAO;
import utils.Utility;

@WebServlet("/ModificaQuantityCarrello")
public class ModificaQuantityCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		int prodottoID = Integer.parseInt(request.getParameter("prodotto"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		BigDecimal prezzo = new BigDecimal(request.getParameter("prezzo"));

		JSONObject json = new JSONObject();
		Carrello c = null;
		boolean updated = false;
		
		if (request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!="true") { 
			c = (Carrello) request.getSession(false).getAttribute("carrello");
			Prodotto p = new Prodotto();
			p.setId(prodottoID);
			c.setItem(p, quantity);
			updated = true;
		} else {
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			CarrelloDAO model = new CarrelloDAO(ds);
			
			String username = (String)request.getSession(false).getAttribute("user");  
			try {
				if(model.updateQuantity(username, prodottoID, quantity)) {
					c = model.doRetrieveByUsername(username);
					updated = true;
				}
			} catch (SQLException e1) {
				Utility.printSQLException(e1);
				return;
			}
		}

		try {
			if (updated) {
				json.append ("update", "true");
				json.append("price", prezzo.multiply(new BigDecimal(quantity)).toString());
				json.append("prezzoTotale", c.getTotale());
				json.append("nProdotti", c.getNProdotti());
			} else {  
				json.append("update", "false");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
		response.getWriter().print(json); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
