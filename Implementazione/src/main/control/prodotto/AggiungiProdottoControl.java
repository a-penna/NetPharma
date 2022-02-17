package main.control.prodotto;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

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
import main.model.AccountDAO;
import main.model.ProdottoDAO;
import main.utils.Utility;


@WebServlet("/AggiungiProdotto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB after which the file will be temporarily stored on disk
				maxFileSize = 1024 * 1024 * 10) // 10MB maximum size allowed for uploaded files
public class AggiungiProdottoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean loggedIn = request.getSession(false) != null && request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null;
		if(!loggedIn) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		String idStr = request.getParameter("id");
		String nome = request.getParameter("nome");
		String marchio = request.getParameter("marchio");
		String produttore = request.getParameter("produttore");
		String formato = request.getParameter("formato");
		String descrizione = request.getParameter("descrizione");
		String disponibilitaStr = request.getParameter("disponibilita");
		String prezzoStr = request.getParameter("prezzo");
		int id = 0, disponibilita = 0;
		
		
		if (idStr == null || nome == null || marchio == null || produttore == null || formato == null || descrizione == null || disponibilitaStr == null || prezzoStr == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/gestoreCatalogo/aggiungiProdotto.jsp"));
			return;
		}
		
		boolean error = false;
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);

		try {
			id = Integer.parseInt(idStr);
			if (model.checkProdotto(id)) {
				request.setAttribute("codiceEsistente", "true");
				error = true;
			}
		} catch (NumberFormatException e) {
			request.setAttribute("erroreCodice", "true");
			error = true;
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/insertError.jsp"));
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
		
		InputStream streamFoto = null; 
		
		Part filePart = request.getPart("foto");
		if (filePart != null) {
			streamFoto = filePart.getInputStream();
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
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp"));
			dispatcher.forward(request, response);
			return;
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

			model.doSave(prodotto);
			request.setAttribute("id", id);
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/insertError.jsp"));
			return;
		}
	}
	
	
	public void setProdottoDAO(ProdottoDAO model) {
		this.modelTest = model;
	}
	
	private ProdottoDAO modelTest;
	
}


