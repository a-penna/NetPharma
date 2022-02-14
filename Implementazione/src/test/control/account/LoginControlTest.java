
package test.control.account;


import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.control.account.LoginControl;
import main.model.AccountDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginControlTest {
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
		
		LoginControl serv = new LoginControl();
		AccountDAO model = Mockito.mock(AccountDAO.class);
		Mockito.when(model.checkUsername(user)).thenReturn(false);
		serv.setAccountDAO(model);
		serv.doPost(request,response);
		assertEquals("true", request.getAttribute("erroreUser"));
	}

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

