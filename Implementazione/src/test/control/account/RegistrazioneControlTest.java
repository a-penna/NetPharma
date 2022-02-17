
package test.control.account;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.control.account.RegistrazioneControl;
import main.model.AccountDAO;
import main.model.UtenteRegistratoDAO;

public class RegistrazioneControlTest {
    private HttpServletRequest request;
    private HttpServletResponse response;


    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
	public void testUsernameExisting() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Mockito.when(accountModel.checkUsername(username)).thenReturn(false);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("true", request.getAttribute("usernameEsistente"));
	}
    
    @Test
	public void testUsernameValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("erroreUser"));
	}

    @Test
	public void testEmailValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("erroreEmail"));
	}
    

    @Test
	public void testEmailExisting() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Mockito.when(utenteModel.checkEmail(email)).thenReturn(false);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("true", request.getAttribute("emailEsistente"));
	}
    
    @Test
	public void testNomeValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("erroreNome"));
	}
    
    @Test
	public void testCognomeValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("erroreCognome"));
	}
    
    @Test
	public void testEtaValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "pw";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("erroreCognome"));
	}
	
	public void testPasswordValid() throws ServletException, IOException, SQLException {
		String sesso = "M";
		String nome = "Riccardo";
		String cognome = "Rossi";
		String username = "rrossi";
		String email = "rrossi@gmail.com";
		String nascita = "1980-06-21";
		String password = "P4ssword!";
		
		Mockito.when(request.getParameter("sesso")).thenReturn(sesso);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("email")).thenReturn(email);
		Mockito.when(request.getParameter("nascita")).thenReturn(nascita);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("false", request.getAttribute("errorePassword"));
	}
    
    
    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

