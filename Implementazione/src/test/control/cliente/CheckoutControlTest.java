
package test.control.cliente;


import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.bean.Carrello;
import main.bean.Ordine;
import main.control.account.LoginControl;
import main.control.cliente.CheckoutControl;
import main.model.AccountDAO;
import main.model.CarrelloDAO;
import main.model.OrdineDAO;
import main.model.ProdottoDAO;
import main.model.RigaOrdineDAO;

import static org.mockito.Mockito.mock;

import java.io.IOException;
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
		
		Mockito.when(session.getAttribute("orderCount")).thenReturn(0);
		Mockito.when(session.getAttribute("id")).thenReturn(0);
		Mockito.when(session.getAttribute("user")).thenReturn("mrossi");
		
		spy.setAccountDAO(Mockito.mock(AccountDAO.class));
		spy.setOrdineDAO(Mockito.mock(OrdineDAO.class));
		spy.setRigaOrdineDAO(Mockito.mock(RigaOrdineDAO.class));
		CarrelloDAO carrelloModel = Mockito.mock(CarrelloDAO.class);
		Mockito.when(carrelloModel.doRetrieveByUsername("mrossi")).thenReturn(new Carrello());
		spy.setCarrelloDAO(carrelloModel);
		spy.doPost(request,response);

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
		
		spy.doPost(request,response);
		Mockito.verify(request).setAttribute("erroreEmail", "true");
		Mockito.verify(response).encodeURL("/checkout.jsp");
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
		
		spy.doPost(request,response);
		Mockito.verify(request).setAttribute("erroreFormatoEmail", "true");
		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreNome", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreFormatoNome", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreCognome", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreFormatoCognome", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreCity", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("errorePaese", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreProvincia", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreCAP", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreFormatoCAP", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreIndirizzo", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreCivico", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreCellulare", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
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
   		
   		spy.doPost(request,response);
   		Mockito.verify(request).setAttribute("erroreFormatoCellulare", "true");
   		Mockito.verify(response).encodeURL("/checkout.jsp");
   	}
    
    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

