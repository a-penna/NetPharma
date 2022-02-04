package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bean.Prodotto;
import model.ProdottoDAO;
import utils.Utility;

@WebServlet("/ProdottoAJAX")
public class ProdottoAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);

		JSONObject json = new JSONObject();
		int id = Integer.parseInt(request.getParameter("id"));
		
		try {
			JSONArray jsArray = new JSONArray();
			Prodotto prodotto = model.doRetrieveByKey(id);
			jsArray.put(prodotto.getNome());
			jsArray.put(prodotto.getMarchio());
			jsArray.put(prodotto.getProduttore());
			jsArray.put(prodotto.getFormato());
			jsArray.put(prodotto.getDescrizione());
			jsArray.put(prodotto.getDisponibilita());
			jsArray.put(prodotto.getPrezzo());
			
			json.put("prodotto", jsArray);

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
