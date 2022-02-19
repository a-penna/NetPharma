
package test.control.account;


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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import main.bean.Account;
import main.bean.Ruoli;
import main.bean.UtenteRegistrato;
import main.control.account.RegistrazioneControl;
import main.model.AccountDAO;
import main.model.UtenteRegistratoDAO;
import main.utils.Utility;

public class RegistrazioneControlTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RegistrazioneControl spy;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(session);
		spy = Mockito.spy(new RegistrazioneControl());
		Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
		ServletContext context = Mockito.mock(ServletContext.class); 
		Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
		Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
    public void testRegistrazioneValid() throws SQLException, ServletException, IOException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Account acc = new Account(0, username, password, 0);
		UtenteRegistrato utente = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli r = new Ruoli();
		r.addRuolo(Ruoli.Ruolo.CL);
		Mockito.when(accountModel.register(acc, utente, r)).thenReturn(true);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");		
		
		Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
		}
    
    @Test
	public void testUsernameExisting() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Mockito.when(accountModel.checkUsername(username)).thenReturn(true);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("usernameEsistente", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
	
    @Test
	public void testUsernameInvalid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta@";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("erroreUsername", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    

    @Test
	public void testUsernameTooLong() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "giannimagentacommercialistadiudinevianapoli27";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		Account acc = new Account(0, username, password, 0);
		UtenteRegistrato utente = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli r = new Ruoli();
		r.addRuolo(Ruoli.Ruolo.CL);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(accountModel).register(acc, utente, r);
		
		spy.doPost(request,response);
		
		Mockito.verify(response).encodeURL("/error/genericError.jsp");
	}

    @Test
	public void testNomeTooLong() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "GianniAntonioMariaFilippo";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		Account acc = new Account(0, username, password, 0);
		UtenteRegistrato utente = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli r = new Ruoli();
		r.addRuolo(Ruoli.Ruolo.CL);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(accountModel).register(acc, utente, r);
		
		spy.doPost(request,response);
		
		Mockito.verify(response).encodeURL("/error/genericError.jsp");
	}
    
    @Test
	public void testCognomeTooLong() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Quondammagentamarianico";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		Account acc = new Account(0, username, password, 0);
		UtenteRegistrato utente = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli r = new Ruoli();
		r.addRuolo(Ruoli.Ruolo.CL);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(accountModel).register(acc, utente, r);
		
		spy.doPost(request,response);
		
		Mockito.verify(response).encodeURL("/error/genericError.jsp");
	}
    
    @Test
	public void testEmailTooLong() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "giannimagentacommercialistadiudinevianapoli27@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		Account acc = new Account(0, username, password, 0);
		UtenteRegistrato utente = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
		Ruoli r = new Ruoli();
		r.addRuolo(Ruoli.Ruolo.CL);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(accountModel).register(acc, utente, r);
		
		spy.doPost(request,response);
		
		Mockito.verify(response).encodeURL("/error/genericError.jsp");
	}
    
    @Test
	public void testEmailInvalid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagentagmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("erroreEmail", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    

    @Test
	public void testEmailExisting() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Mockito.when(utenteModel.checkEmail(email)).thenReturn(true);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("emailEsistente", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    
    @Test
	public void testNomeInvalid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni1";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("erroreNome", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    
    @Test
	public void testCognomeValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta1";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("erroreCognome", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    
    @Test
	public void testEtaValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "2020-12-12";
		String password = "P4ssw0rd!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePassword", "true");
		
		
		Mockito.verify(request).setAttribute("erroreData", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
	
    @Test
	public void testPasswordInvalid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Gianni";
		String cognome = "Magenta";
		String username = "gmagenta";
		String email = "gmagenta@gmail.com";
		String nascita = "1980-12-12";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		spy.setAccountDAO(accountModel);
		spy.setUtenteRegistratoDAO(utenteModel);
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreUsername", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCognome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreEmail", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("emailEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreData", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("usernameEsistente", "true");
		
		
		Mockito.verify(request).setAttribute("errorePassword", "true");
		Mockito.verify(request).setAttribute("sesso", sesso);
		Mockito.verify(request).setAttribute("nome", nome);
		Mockito.verify(request).setAttribute("cognome", cognome);
		Mockito.verify(request).setAttribute("username", username);
		Mockito.verify(request).setAttribute("email", email);
		Mockito.verify(request).setAttribute("nascita", nascita);
		Mockito.verify(request).setAttribute("password", password);
		Mockito.verify(response).encodeURL("/registrazione.jsp");
	}
    
    
    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

