package main.control.cliente;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Carrello;
import main.bean.ContenutoCarrello;
import main.bean.Ordine;
import main.bean.RigaOrdine;
import main.model.AccountDAO;
import main.model.CarrelloDAO;
import main.model.OrdineDAO;
import main.model.RigaOrdineDAO;
import main.utils.Utility;


@WebServlet("/Checkout")
public class CheckoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDAO accountModelTest;
	private OrdineDAO ordineModelTest;
	private CarrelloDAO carrelloModelTest;
	private RigaOrdineDAO rigaModelTest;
	
	public void setAccountDAO(AccountDAO model) {
		this.accountModelTest = model;
	}
	
	public void setOrdineDAO(OrdineDAO model) {
		this.ordineModelTest = model;
	}
	
	public void setCarrelloDAO(CarrelloDAO model) {
		this.carrelloModelTest = model;
	}
	
	public void setRigaOrdineDAO(RigaOrdineDAO model) {
		this.rigaModelTest = model;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isCliente = request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null;

		if (!isCliente) { 
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Logout"));
			return;
		}
		
		String email = request.getParameter("email");
		String nome = request.getParameter("name");
		String cognome = request.getParameter("surname");
		String citta = request.getParameter("city");
		String paese = request.getParameter("country");
		String provincia = request.getParameter("provincia");
		String cap = request.getParameter("cap");
		String indirizzo = request.getParameter("address");
		String nCivicoStr = request.getParameter("number");
		String cellulare = request.getParameter("cellulare");

		if(email == null || nome == null || cognome == null || citta == null || paese == null || provincia == null || cap == null || indirizzo == null || nCivicoStr == null|| cellulare == null) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/checkout.jsp"));
			return;
		}
		boolean error = false;
		
		int numeroCivico =-1;
		try {
			numeroCivico = Integer.parseInt(nCivicoStr);
		} catch(NumberFormatException e) {
			request.setAttribute("erroreCivico", "true");
			error = true;
		}
		
		if(email.trim().equals("") || email.length() > 20) {
			request.setAttribute("erroreEmail", "true");
			error = true;
		} else if (!Utility.checkEmail(email)) {
			request.setAttribute("erroreFormatoEmail", "true");
			error = true;
		}
		
		if(nome.trim().equals("") || nome.length() > 20) {
			request.setAttribute("erroreNome", "true");
			error = true;
		} else if (!Utility.checkNomeCognome(nome)) {
			request.setAttribute("erroreFormatoNome", "true");
			error = true;
		}
		
		if(cognome.trim().equals("") || cognome.length() > 20) {
			request.setAttribute("erroreCognome", "true");
			error = true;
		} else if (!Utility.checkNomeCognome(cognome)) {
			request.setAttribute("erroreFormatoCognome", "true");
			error = true;
		}
		
		if(citta.trim().equals("") || citta.length() > 50) {
			request.setAttribute("erroreCity", "true");
			error = true;
		}
		
		if(paese.trim().equals("") || paese.length() > 50) {
			request.setAttribute("errorePaese", "true");
			error = true;
		}
		
		if(provincia.trim().equals("") || provincia.length() > 50) {
			request.setAttribute("erroreProvincia", "true");
			error = true;
		}
		
		if(cap.trim().equals("") || cap.length() > 5) {
			request.setAttribute("erroreCAP", "true");
			error = true;
		} else if (!Utility.checkCAP(cap)) {
			request.setAttribute("erroreFormatoCAP", "true");
			error = true;
		}
		
		if(indirizzo.trim().equals("") || indirizzo.length() > 50) {
			request.setAttribute("erroreIndirizzo", "true");
			error = true;
		}
		
		if(cellulare.trim().equals("") || cellulare.length() > 15) {
			request.setAttribute("erroreCellulare", "true");
			error = true;
		} else if (!Utility.checkCellulare(cellulare)) {
			request.setAttribute("erroreFormatoCellulare", "true");
			error = true;
		}
		
		if (error) { 
			nome = Utility.filter(nome);
			cognome = Utility.filter(cognome);
			citta = Utility.filter(citta);
			email = Utility.filter(email);
			paese = Utility.filter(paese);
			provincia = Utility.filter(provincia);
			cap = Utility.filter(cap);
			indirizzo = Utility.filter(indirizzo);
			nCivicoStr = Utility.filter(nCivicoStr);
			cellulare = Utility.filter(cellulare);
			request.setAttribute("name", nome);
			request.setAttribute("email", email);
			request.setAttribute("surname", cognome);
			request.setAttribute("city", citta);
			request.setAttribute("country", paese);
			request.setAttribute("provincia", provincia);
			request.setAttribute("cap", cap);
			request.setAttribute("address", indirizzo);
			request.setAttribute("number", nCivicoStr);
			request.setAttribute("cellulare", cellulare);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/checkout.jsp"));
			dispatcher.forward(request, response);
			return;
		}

		DataSource ds = null;
		OrdineDAO model = null;
		if (ordineModelTest == null) {
			ds = (DataSource) getServletContext().getAttribute("DataSource");
			model = new OrdineDAO(ds);
		} else {
			model = ordineModelTest;
		}
		
		try {
			CarrelloDAO carrelloModel = null;
			if (carrelloModelTest == null) {
				carrelloModel = new CarrelloDAO(ds);
			} else {
				carrelloModel = carrelloModelTest;
			}

			String username = (String) request.getSession(false).getAttribute("user");
			Carrello carrello = carrelloModel.doRetrieveByUsername(username);
			
			//Update carrello e quantit� prodotto
			Ordine ordine = new Ordine();
			
			int orderCount = (int)request.getSession(false).getAttribute("orderCount");
			String ordineID = request.getSession(false).getAttribute("id") + "-" + (orderCount+1);
			request.getSession(false).setAttribute("orderCount", orderCount+1);
			ordine.setId(ordineID);
			
			ordine.setNomeRicevente(nome);
			ordine.setCognomeRicevente(cognome);
			ordine.setEmail(email);
			ordine.setCellulare(cellulare);
			ordine.setNcivico(numeroCivico);
			ordine.setCitta(citta);
			ordine.setVia(indirizzo);
			ordine.setPaese(paese);
			ordine.setProvincia(provincia);
			ordine.setCAP(cap);
			ordine.setPrezzo(carrello.getTotale());
			ordine.setStato("No");
			ordine.setCliente((String) request.getSession(false).getAttribute("email"));
			ordine.setData_ordine(new Date(System.currentTimeMillis()));
			model.doSaveCheck(ordine);

			RigaOrdineDAO rigaOrdineModel = null;
			if (rigaModelTest == null) {
				rigaOrdineModel = new RigaOrdineDAO(ds);
			} else {
				rigaOrdineModel = rigaModelTest;
			}
			Collection<ContenutoCarrello> items = carrello.getItems();
			Iterator<ContenutoCarrello> it = items.iterator();
			while(it.hasNext()) {
				ContenutoCarrello contenuto = it.next();
				rigaOrdineModel.doSave(new RigaOrdine(contenuto.getProdotto().getId(), ordineID, contenuto.getQuantity(), contenuto.getProdotto().getPrezzo()));
			}
			carrelloModel.clearCart(username);
			
			AccountDAO accountModel = null;
			if (accountModelTest == null) {
				accountModel = new AccountDAO(ds);
			} else {
				accountModel = accountModelTest;
			}
			accountModel.updateOrderCount((int)request.getSession(false).getAttribute("id"), orderCount + 1);
			
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
			
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
		}
		
	}
}
