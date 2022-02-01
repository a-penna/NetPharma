package control;

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

import bean.Ordine;

import model.OrdineDAO;

import utils.Utility;


@WebServlet("/OrdiniDaSpedire")
public class OrdiniDaSpedireControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OrdiniDaSpedireControl() {
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

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreOrdini/SpedisciOrdini.jsp"));
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}
