package main.control.cliente;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Carrello;
import main.bean.ContenutoCarrello;
import main.bean.DatiSpedizione;
import main.bean.Ordine;
import main.model.CarrelloDAO;
import main.model.DatiSpedizioneDAO;
import main.model.OrdineDAO;
import main.utils.Utility;


@WebServlet("/Checkout")
public class CheckoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDAO model = new OrdineDAO(ds);
		DatiSpedizioneDAO model2 = new DatiSpedizioneDAO(ds);
		
		String email = request.getParameter("email");
		String nome = request.getParameter("name");
		String cognome = request.getParameter("surname");
		String citta = request.getParameter("city");
		String paese = request.getParameter("country");
		String provincia = request.getParameter("provincia");
		String cap = request.getParameter("cap");
		String indirizzo = request.getParameter("address");
		int numeroCivico = Integer.parseInt(request.getParameter("number"));
		String cellulare = request.getParameter("cellulare");
		
		if(email == null || nome == null || cognome == null || citta == null || paese == null || provincia == null || cap == null || indirizzo == null || numeroCivico == 0|| cellulare == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/checkout.jsp"));
			return;
		}
		
		try {
			DatiSpedizione dati = new DatiSpedizione(nome,cognome,email,cellulare,numeroCivico,citta,indirizzo,paese,provincia,cap,email);
			model2.doSave(dati);
			
			CarrelloDAO carrelloModel = new CarrelloDAO(ds);
			String username = (String) request.getSession().getAttribute("user");
			Carrello carrello = carrelloModel.doRetrieveByUsername(username);
			
			//Update carrello e quantit� prodotto
			Ordine ordine = new Ordine();
			ordine.setPrezzo(carrello.getTotale());
			ordine.setStato("No");
			ordine.setCliente((String) request.getSession().getAttribute("email"));
			ordine.setData_ordine(new Date(System.currentTimeMillis()));
			
			model.doSaveCheck(ordine, model2.doRetriveIdByEmail(email).getId());
			//Clear cart
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/success.jsp"));
			
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
		}
		
	}
}
