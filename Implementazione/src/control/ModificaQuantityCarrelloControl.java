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

import model.CarrelloDAO;
import utils.Utility;

@WebServlet("/ModificaQuantityCarrello")
public class ModificaQuantityCarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CarrelloDAO model = new CarrelloDAO(ds);

		JSONObject json = new JSONObject();
		int prodottoID = Integer.parseInt(request.getParameter("prodotto"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		BigDecimal prezzo = new BigDecimal(request.getParameter("prezzo"));
		
		String username = (String)request.getSession(false).getAttribute("user");  
		try {
			if(model.updateQuantity(username, prodottoID, quantity)) {
				json.append("update", "true");
				json.append("price", prezzo.multiply(new BigDecimal(quantity)).toString());
			}
			else  
				json.append("update", "false");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		catch (SQLException e1) {
			Utility.printSQLException(e1);
		}

		response.getWriter().print(json); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
