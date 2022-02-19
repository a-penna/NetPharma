package main.control.prodotto;

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
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO prodottoModel = new ProdottoDAO(ds);
		try {
			Collection<Prodotto> prodotti = prodottoModel.doRetrieveSvincolati();
			request.setAttribute("listaProdotti", prodotti);
		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		String nome= request.getParameter("nome");
		String idProdotti[] = request.getParameterValues("idProdotti");
		
		if(nome == null) {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreCatalogo/aggiungiCategoria.jsp"));
			dispatcher.forward(request, response);
			return;
		}
		
		ProdottoDAO model = new ProdottoDAO(ds);
		CategoriaDAO categoriaModel = new CategoriaDAO(ds);
		
		try {
			Categoria categoria = new Categoria();
			if (categoriaModel.checkCategoria(nome)) {
				request.setAttribute("nomeEsistente", "true");
				request.setAttribute("nome", nome);
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreCatalogo/aggiungiCategoria.jsp"));
				dispatcher.forward(request, response);
				return;
			}
			categoria.setNome(nome);
			categoriaModel.doSave(categoria);
			if (idProdotti != null) {
				for (int i = 0; i < idProdotti.length; i++) {
					model.updateCategoria(Integer.parseInt(idProdotti[i]), nome);
				}
			}
			
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(com.mysql.cj.jdbc.exceptions.MysqlDataTruncation e) {
			Utility.printSQLException(e);
			request.setAttribute("message", "Probabilmente alcuni dati sono troppo lunghi per essere inseriti");
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error/genericError.jsp"));
			dispatcher.forward(request, response);
			return;
		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
			return;
		}
	}
}


