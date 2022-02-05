package main.control.prodotto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Categoria;
import main.bean.Prodotto;
import main.model.CategoriaDAO;
import main.model.ProdottoDAO;
import main.utils.Utility;


@WebServlet("/AggiungiCategoria")
public class AggiungiCategoriaControl extends HttpServlet {
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
		String nome = request.getParameter("nome");
		
		if (nome == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/gestoreCatalogo/aggiungiCategoria.jsp"));
			return;
		}
		
		nome = Utility.filter(nome);
		

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		CategoriaDAO model = new CategoriaDAO(ds);
		ProdottoDAO prodottoModel = new ProdottoDAO(ds);
		try {
			Collection<Prodotto> prodotti = prodottoModel.doRetrieveSvincolati();
			request.setAttribute("listaProdotti", prodotti);
			Categoria categoria = new Categoria();
			
			categoria.setNome(nome);
			model.doSave(categoria);
			for(Prodotto p : prodotti)
				p.setCategoria(categoria.getNome());
			
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/insertError.jsp"));
			return;
		}
	}
}


