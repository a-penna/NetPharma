package main.control.ordini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


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


@WebServlet("/ListaOrdini")
public class ListaOrdiniControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListaOrdiniControl() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDAO model = new OrdineDAO(ds);
		
		try {
			Collection<Ordine> ordini = model.doRetrieveAll("");
			
			
			
			request.setAttribute("ordini", ordini); 
			
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
			return;
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreOrdini/ListaOrdini.jsp"));
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}