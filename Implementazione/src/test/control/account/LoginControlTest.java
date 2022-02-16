
package test.control.account;


import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.control.account.LoginControl;
import main.model.AccountDAO;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginControlTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private LoginControl spy;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		spy = Mockito.spy(new LoginControl());
		Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
		ServletContext context = Mockito.mock(ServletContext.class); 
		Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
		Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
	public void testUsernameNotExisting() throws ServletException, IOException, SQLException {
		String user = "vrossi";
		String pass = "pw";
		
		Mockito.when(request.getParameter("username")).thenReturn(user);
		Mockito.when(request.getParameter("password")).thenReturn(pass);
		
		AccountDAO model = Mockito.mock(AccountDAO.class);
		Mockito.when(model.checkUsername(user)).thenReturn(false);
		spy.setAccountDAO(model);
		spy.doPost(request,response);

		Mockito.verify(request).setAttribute("erroreUser", "true");
		Mockito.verify(request).setAttribute("errorePass", "true");
		Mockito.verify(request).setAttribute("username", user);
		Mockito.verify(request).setAttribute("password", pass);
		Mockito.verify(response).encodeURL("/login.jsp");
	}

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}

