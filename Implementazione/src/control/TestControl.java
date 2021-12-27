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

import bean.UtenteRegistrato;
import model.UtenteRegistratoDAO;
import utils.Utility;


@WebServlet("/TestControl")
public class TestControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		UtenteRegistratoDAO model = new UtenteRegistratoDAO(ds);

		try {
			UtenteRegistrato user = model.doRetrieveByKey("MRossi");
			
			request.setAttribute("user", user); 
		} catch (SQLException e) {
			Utility.printSQLException(e);
			return;
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/test.jsp"));
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
