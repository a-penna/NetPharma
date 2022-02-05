package main.control.prodotto;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

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


@WebServlet("/AggiungiProdotto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB after which the file will be temporarily stored on disk
				maxFileSize = 1024 * 1024 * 10) // 10MB maximum size allowed for uploaded files
public class AggiungiProdottoControl extends HttpServlet {
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
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String marchio = request.getParameter("marchio");
		String produttore = request.getParameter("produttore");
		String formato = request.getParameter("formato");
		String descrizione = request.getParameter("descrizione");
		int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
		String categoria = request.getParameter("categoria");
		BigDecimal prezzo = new BigDecimal(request.getParameter("prezzo"));
		
		
		if (nome == null || marchio == null || produttore == null || formato == null || descrizione == null || categoria == null || prezzo == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/gestoreCatalogo/aggiungiProdotto.jsp"));
			return;
		}
		
		nome = Utility.filter(nome);
		marchio = Utility.filter(marchio);
		descrizione = Utility.filter(descrizione);
		produttore = Utility.filter(produttore);
		formato = Utility.filter(formato);

		
		InputStream streamFoto = null; 
		
		Part filePart = request.getPart("foto");
		if (filePart != null) {
			streamFoto = filePart.getInputStream();
		}
		

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO model = new ProdottoDAO(ds);

		try {
			Prodotto prodotto = new Prodotto();
			prodotto.setId(id);
			prodotto.setNome(nome);
			prodotto.setMarchio(marchio);
			prodotto.setProduttore(produttore);
			prodotto.setFormato(formato);
			prodotto.setDescrizione(descrizione);
			prodotto.setDisponibilita(disponibilita);
			prodotto.setCategoria(categoria);
			prodotto.setPrezzo(prezzo);
			prodotto.setFoto(streamFoto.readAllBytes());

			model.doSave(prodotto);
			
		    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		    return;

		} catch(SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/insertError.jsp"));
			return;
		}
	}
}


