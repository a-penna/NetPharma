package control;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.DatiSpedizione;
import bean.Ordine;
import model.DatiSpedizioneDAO;
import model.OrdineDAO;
import utils.Utility;

/**
 * Servlet implementation class CheckoutControl
 */
@WebServlet("/CheckoutControl")
public class CheckoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		DatiSpedizione dati = new DatiSpedizione(nome,cognome,email,cellulare,numeroCivico,citta,indirizzo,paese,provincia,cap,email);
		try {
			
			model2.doSave(dati);
			Ordine ordine = new Ordine();
			ordine.setStato("non gestito");
			ordine.setCliente(email);
			ordine.setData_ordine(new Date(System.currentTimeMillis()));
			//Settare prezzo dal prodotto del carrello
			//Mandare dati a gestore ordine che deve settare data di arrivo
			model.doSaveCheck(ordine, model2.doRetriveIdByEmail(email).getId());
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/success.jsp"));
			
		} catch (SQLException e) {
			Utility.printSQLException(e);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
		}
		
		
		
		
		
	}

}
