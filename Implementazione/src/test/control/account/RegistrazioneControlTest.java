
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
	public void testUsernameNotExisting() throws ServletException, IOException, SQLException {
		String user = "vrossi";
		String pass = "pw";
		
		Mockito.when(request.getParameter("username")).thenReturn(user);
		Mockito.when(request.getParameter("password")).thenReturn(pass);
		
		RegistrazioneControl serv = new RegistrazioneControl();
		AccountDAO accountModel = Mockito.mock(AccountDAO.class);
		UtenteRegistratoDAO utenteModel = Mockito.mock(UtenteRegistratoDAO.class);
		Mockito.when(accountModel.checkUsername(user)).thenReturn(false);
		Mockito.when(utenteMode.checkUsername(user)).thenReturn(false);
		serv.setAccountDAO(accountModel);
		serv.setUtenteRegistratoDAO(utenteModel);
		serv.doPost(request,response);
		assertEquals("true", request.getAttribute("erroreUser"));
	}

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

