package main.control.prodotto;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Prodotto;
import main.model.ProdottoDAO;
import main.utils.Utility;


@WebServlet("/FotoControl")
public class FotoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		
		if (idStr == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
		 	return;
		}
		
		int id = Integer.parseInt(idStr);
		
		byte[] bt = null;
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);
		try {
			Prodotto prodotto = model.doRetrieveByKey(id);
			if (prodotto == null) {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
			 	return;
			}
			
			bt = prodotto.getFoto();
				
			} catch (SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
				return;
		}
		
		ServletOutputStream out = response.getOutputStream();
		
		if(bt != null) { 
			out.write(bt);
			response.setContentType("image/png");			
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
