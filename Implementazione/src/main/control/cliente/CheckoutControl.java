package main.control.cliente;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDAO model = new OrdineDAO(ds);
		
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
			CarrelloDAO carrelloModel = new CarrelloDAO(ds);
			String username = (String) request.getSession(false).getAttribute("user");
			Carrello carrello = carrelloModel.doRetrieveByUsername(username);
			
			//Update carrello e quantit� prodotto
			Ordine ordine = new Ordine();
			
			int orderCount = (int)request.getSession(false).getAttribute("orderCount");
			System.out.println(orderCount);
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

			RigaOrdineDAO rigaOrdineModel = new RigaOrdineDAO(ds);
			Collection<ContenutoCarrello> items = carrello.getItems();
			Iterator<ContenutoCarrello> it = items.iterator();
			while(it.hasNext()) {
				ContenutoCarrello contenuto = it.next();
				rigaOrdineModel.doSave(new RigaOrdine(contenuto.getProdotto().getId(), ordineID, contenuto.getQuantity(), contenuto.getProdotto().getPrezzo()));
			}
			carrelloModel.clearCart(username);
			
			AccountDAO accountModel = new AccountDAO(ds);
			accountModel.updateOrderCount((int)request.getSession(false).getAttribute("id"), orderCount + 1);
			
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
			
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
		}
		
	}
}
