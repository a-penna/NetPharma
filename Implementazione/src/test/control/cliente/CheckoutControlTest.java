
package test.control.cliente;


import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.Carrello;
import main.bean.Ordine;
import main.control.cliente.CheckoutControl;
import main.model.AccountDAO;
import main.model.CarrelloDAO;
import main.model.OrdineDAO;
import main.model.RigaOrdineDAO;
import main.utils.Utility;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckoutControlTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CheckoutControl spy;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		Mockito.when(request.getSession(false)).thenReturn(session);
		Mockito.when(session.getAttribute("clienteRoles")).thenReturn("true");
		spy = Mockito.spy(new CheckoutControl());
		Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
		ServletContext context = Mockito.mock(ServletContext.class); 
		Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
		Mockito.when(spy.getServletContext()).thenReturn(context);
    }
    
	private void sendParameter(String email, String nome, String cognome, String city, String paese, 
   				String provincia, String cap, String indirizzo, String civico, String cellulare) {
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("name")).thenReturn(nome);
		Mockito.when(request.getParameter("surname")).thenReturn(cognome);
		Mockito.when(request.getParameter("city")).thenReturn(city);
		Mockito.when(request.getParameter("country")).thenReturn(paese);
		Mockito.when(request.getParameter("provincia")).thenReturn(provincia);
		Mockito.when(request.getParameter("cap")).thenReturn(cap);
		Mockito.when(request.getParameter("address")).thenReturn(indirizzo);
		Mockito.when(request.getParameter("number")).thenReturn(civico);
   		Mockito.when(request.getParameter("cellulare")).thenReturn(cellulare);
   	}
   		
	private void verifyFormReturnAttribute(String email, String nome, String cognome, String city, String paese, 
			String provincia, String cap, String indirizzo, String civico, String cellulare) {
		Mockito.verify(request).setAttribute("name", Utility.filter(nome));
		Mockito.verify(request).setAttribute("email", Utility.filter(email));
		Mockito.verify(request).setAttribute("surname", Utility.filter(cognome));
		Mockito.verify(request).setAttribute("city", Utility.filter(city));
		Mockito.verify(request).setAttribute("country", Utility.filter(paese));
		Mockito.verify(request).setAttribute("provincia", Utility.filter(provincia));
		Mockito.verify(request).setAttribute("cap", Utility.filter(cap));
		Mockito.verify(request).setAttribute("address", Utility.filter(indirizzo));
		Mockito.verify(request).setAttribute("number", Utility.filter(civico));
		Mockito.verify(request).setAttribute("cellulare", Utility.filter(cellulare));
	}

    @Test
	public void testSuccess() throws ServletException, IOException, SQLException {
		String email = "mrossi@mail.com";
		String nome = "Mario";
		String cognome = "Rossi";
		String city = "Fisciano";
		String paese = "Italia";
		String provincia = "Salerno";
		String cap = "84084";
		String indirizzo = "Indirizzo";
		String civico =	"0";
		String cellulare = "123456789";
		
		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
		int sessionOrderCount = 0;
		int sessionId = 0;
		String sessionUser = "mrossi";
		String sessionEmail = "mrossi2@mail.com";
		Mockito.when(session.getAttribute("orderCount")).thenReturn(sessionOrderCount);
		Mockito.when(session.getAttribute("id")).thenReturn(sessionId);
		Mockito.when(session.getAttribute("user")).thenReturn(sessionUser);
		Mockito.when(session.getAttribute("email")).thenReturn(sessionEmail);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		spy.setAccountDAO(accountModel);
		OrdineDAO ordineModel = Mockito.mock(OrdineDAO.class);
		spy.setOrdineDAO(ordineModel);
		spy.setRigaOrdineDAO(Mockito.mock(RigaOrdineDAO.class));
		CarrelloDAO carrelloModel = Mockito.mock(CarrelloDAO.class);
		Mockito.when(carrelloModel.doRetrieveByUsername(sessionUser)).thenReturn(new Carrello());
		spy.setCarrelloDAO(carrelloModel);
		
		spy.doPost(request,response);

		Ordine ordine = new Ordine();
		ordine.setId(sessionId + "-" + (sessionOrderCount + 1));
		ordine.setNomeRicevente(nome);
		ordine.setCognomeRicevente(cognome);
		ordine.setEmail(email);
		ordine.setCellulare(cellulare);
		ordine.setNcivico(Integer.parseInt(civico));
		ordine.setCitta(city);
		ordine.setVia(indirizzo);
		ordine.setPaese(paese);
		ordine.setProvincia(provincia);
		ordine.setCAP(cap);
		ordine.setPrezzo(BigDecimal.ZERO);
		ordine.setStato("No");
		ordine.setCliente(sessionEmail);
		ordine.setData_ordine(new Date(System.currentTimeMillis()));

		Mockito.verify(ordineModel).doSaveCheck(ordine);
		Mockito.verify(accountModel).updateOrderCount(sessionId, sessionOrderCount + 1);
		Mockito.verify(carrelloModel).clearCart(sessionUser);
		Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
	}
    
    @Test
	public void testErroreLunghezzaEmail() throws ServletException, IOException, SQLException {
		String email = "";
		String nome = "Mario";
		String cognome = "Rossi";
		String city = "Fisciano";
		String paese = "Italia";
		String provincia = "Salerno";
		String cap = "84084";
		String indirizzo = "Indirizzo";
		String civico =	"0";
		String cellulare = "123456789";
		
		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
		Mockito.verify(request).setAttribute("erroreEmail", "true");
		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
	}
    
    @Test
	public void testErroreFormatoEmail() throws ServletException, IOException, SQLException {
		String email = "mrossi";
		String nome = "Mario";
		String cognome = "Rossi";
		String city = "Fisciano";
		String paese = "Italia";
		String provincia = "Salerno";
		String cap = "84084";
		String indirizzo = "Indirizzo";
		String civico =	"0";
		String cellulare = "123456789";
		
		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
		Mockito.verify(request).setAttribute("erroreFormatoEmail", "true");
		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
	}
    
    @Test
   	public void testErroreLunghezzaNome() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreNome", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreFormatoNome() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "M4rio";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreFormatoNome", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}

    @Test
   	public void testErroreLunghezzaCognome() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreCognome", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreFormatoCognome() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Ross1";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreFormatoCognome", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaCity() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreCity", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaPaese() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("errorePaese", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaProvincia() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreProvincia", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaCAP() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "840844";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreCAP", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreFormatoCAP() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "8408A";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreFormatoCAP", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaIndirizzo() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "";
   		String civico =	"0";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFromatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		
   		Mockito.verify(request).setAttribute("erroreIndirizzo", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreFormatoCivico() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"A";
   		String cellulare = "123456789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		
   		Mockito.verify(request).setAttribute("erroreCivico", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @Test
   	public void testErroreLunghezzaCellulare() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "1234567891234567";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreCellulare", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    
    @Test
   	public void testErroreFormatoCellulare() throws ServletException, IOException, SQLException {
   		String email = "mrossi@mail.com";
   		String nome = "Mario";
   		String cognome = "Rossi";
   		String city = "Fisciano";
   		String paese = "Italia";
   		String provincia = "Salerno";
   		String cap = "84084";
   		String indirizzo = "Indirizzo";
   		String civico =	"0";
   		String cellulare = "123A56789";
   		
   		sendParameter(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		
   		spy.doPost(request,response);
   		
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCivico", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoEmail", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCity", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("errorePaese", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreProvincia", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(request, Mockito.never()).setAttribute("erroreCellulare", "true");
   		
   		Mockito.verify(request).setAttribute("erroreFormatoCellulare", "true");
   		verifyFormReturnAttribute(email, nome, cognome, city, paese, provincia, cap, indirizzo, civico, cellulare);
   		Mockito.verify(response).encodeURL("/cliente/checkout.jsp");
   	}
    
    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
        session=null;
        spy=null;
    }
    
}

