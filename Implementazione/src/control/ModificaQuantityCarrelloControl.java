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
import model.ProdottoDAO;
import utils.Utility;

@WebServlet("/ModificaQuantityCarrello")
public class ModificaQuantityCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		int quantity;
		int prodottoID;
		
		try {
			prodottoID = Integer.parseInt(request.getParameter("prodotto"));
			quantity = Integer.parseInt(request.getParameter("quantity"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}
		
		if (quantity <= 0) return;
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO prodottoModel = new ProdottoDAO(ds);
		Prodotto p = null;
		try {
			p = prodottoModel.doRetrieveByKey(prodottoID);
		} catch (SQLException e) {
			Utility.printSQLException(e);
			return;
		}
		
		if (p == null) return;
		
		JSONObject json = new JSONObject();
		Carrello c = null;
		boolean updated = false;
		
		if (request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!="true") { 
			c = (Carrello) request.getSession(false).getAttribute("carrello");
			c.setItem(p, quantity);
			updated = true;
		} else if (request.getSession(false) != null){
			CarrelloDAO model = new CarrelloDAO(ds);
			
			String username = (String)request.getSession(false).getAttribute("user");  
			try {
				if(model.updateQuantity(username, prodottoID, quantity)) {
					c = model.doRetrieveByUsername(username);
					updated = true;
				}
			} catch (SQLException e) {
				Utility.printSQLException(e);
				return;
			}
		}

		try {
			if (updated) {
				json.append ("update", "true");
				json.append("price", p.getPrezzo().multiply(new BigDecimal(quantity)).toString());
				json.append("prezzoTotale", c.getTotale());
				json.append("nProdotti", c.getNProdotti());
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
