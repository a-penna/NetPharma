package main.control.prodotto;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import main.bean.Prodotto;
import main.model.ProdottoDAO;
import main.utils.Utility;


@WebServlet("/ModificaProdotto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB after which the file will be temporarily stored on disk
				maxFileSize = 1024 * 1024 * 10) // 10MB maximum size allowed for uploaded files
public class ModificaProdottoControl extends HttpServlet {
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
		ProdottoDAO model = new ProdottoDAO(ds);

		try {
            Collection<Prodotto> prodotti = model.doRetrieveAll("nome");
			request.setAttribute("listaProdotti", prodotti);
		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/updateError.jsp"));
			return;
		}
		
		
		request.setCharacterEncoding("UTF-8");
		String nome = request.getParameter("nome");
		String marchio = request.getParameter("marchio");
		String produttore = request.getParameter("produttore");
		String formato = request.getParameter("formato");
		String descrizione = request.getParameter("descrizione");
		String disponibilitaStr= request.getParameter("disponibilita");
		String idStr = request.getParameter("prodotto");
		String prezzoStr = request.getParameter("prezzo");
		int disponibilita=0;

		if (nome == null || marchio == null || produttore == null || formato == null || descrizione == null || disponibilitaStr == null || prezzoStr == null || idStr == null) {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreCatalogo/modificaProdotto.jsp"));
			dispatcher.forward(request, response);
			return;
		}
	
		
		boolean error = false;
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/gestoreCatalogo/modificaProdotto.jsp"));
			return;
		}
		
		try {
			disponibilita = Integer.parseInt(disponibilitaStr);
			if (disponibilita <= 0) {
				request.setAttribute("erroreDisponibilita", "true");
				error = true;
			}		
		} catch (NumberFormatException e) {
			request.setAttribute("erroreDisponibilita", "true");
			error = true;
		}
		
		BigDecimal prezzo = BigDecimal.ZERO;
		try {
			prezzo = new BigDecimal(prezzoStr);
			if (prezzo.compareTo(BigDecimal.ZERO) <= 0) {
				request.setAttribute("errorePrezzo", "true");
				error = true;
			}		
		} catch (NumberFormatException e) {
			request.setAttribute("errorePrezzo", "true");
			error = true;
		}
		
		nome = Utility.filter(nome);
		marchio = Utility.filter(marchio);
		descrizione = Utility.filter(descrizione);
		produttore = Utility.filter(produttore);
		formato = Utility.filter(formato);

		
		if(nome.trim().equals("") || nome.length() > 100) {
			request.setAttribute("erroreNome", "true");
			error = true;
		}
		
		if(marchio.trim().equals("") || marchio.length() > 50) {
			request.setAttribute("erroreMarchio", "true");
			error = true;
		}
		
		if(descrizione.trim().equals("")) {
			request.setAttribute("erroreDescrizione", "true");
			error = true;
		}
		
		if (error) { 
			request.setAttribute("nome", nome);
			request.setAttribute("prezzo", prezzoStr);
			request.setAttribute("marchio", marchio);
			request.setAttribute("descrizione", descrizione);
			request.setAttribute("produttore", produttore);
			request.setAttribute("formato", formato);
			request.setAttribute("codice", idStr);
			request.setAttribute("disponibilita", disponibilitaStr);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreCatalogo/modificaProdotto.jsp"));
			dispatcher.forward(request, response);
			return;
		}

		InputStream streamFoto = null; 
		
		Part filePart = request.getPart("foto");
		if (filePart != null) {
			streamFoto = filePart.getInputStream();
		}
		
		try {
			Prodotto prodotto = new Prodotto();
			prodotto.setId(id);
			prodotto.setNome(nome);
			prodotto.setMarchio(marchio);
			prodotto.setProduttore(produttore);
			prodotto.setFormato(formato);
			prodotto.setDescrizione(descrizione);
			prodotto.setDisponibilita(disponibilita);
			prodotto.setPrezzo(prezzo);
			prodotto.setFoto(streamFoto.readAllBytes());
			

			model.doUpdate(prodotto);
			
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/updateError.jsp"));
			return;
		}
	}
}



