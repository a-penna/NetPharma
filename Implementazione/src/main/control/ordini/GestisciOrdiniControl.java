package main.control.ordini;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Ordine;
import main.model.OrdineDAO;
import main.utils.Utility;


@WebServlet(name = "GestisciOrdini", urlPatterns = { "/GestisciOrdini" })
public class GestisciOrdiniControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDAO model = new OrdineDAO(ds);
		try {
			Ordine ordine = model.doRetrieveByKey(request.getParameter("scelta"));
			long giorni = Long.parseLong(request.getParameter("giorni")) * 86400000;
			if(model.doUpdateStatus(ordine,giorni)) {
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreOrdini/SpedisciOrdini.jsp"));
				dispatcher.forward(request, response);
			}
			else {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
				return;
			}
		} catch (SQLException e) {
			
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
			return;
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
