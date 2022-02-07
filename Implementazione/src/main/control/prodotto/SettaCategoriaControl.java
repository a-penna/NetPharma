package main.control.prodotto;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Categoria;
import main.model.CategoriaDAO;
import main.model.ProdottoDAO;
import main.utils.Utility;


@WebServlet("/SettaCategoria")
public class SettaCategoriaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean loggedIn = request.getSession(false) != null && request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null;
		if(!loggedIn) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		String nome= request.getParameter("nome");
		String idProdotti[] = request.getParameterValues("idProdotti");
		
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);
		CategoriaDAO categoriaModel = new CategoriaDAO(ds);
		
		try {
			Categoria categoria = new Categoria();
			
			categoria.setNome(nome);
			categoriaModel.doSave(categoria);
			
			for (int i = 0; i < idProdotti.length; i++) {
				System.out.println(idProdotti[i]);
				model.updateCategoria(Integer.parseInt(idProdotti[i]), nome);
			}
			
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/insertError.jsp"));
			return;
		}
	}
}


