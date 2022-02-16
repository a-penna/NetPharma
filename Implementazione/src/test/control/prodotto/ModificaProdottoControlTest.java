package test.control.prodotto;


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


public class ModificaProdottoControlTest {

	private HttpServletRequest request;
    private HttpServletResponse response;


    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
    @Test
   	public void editProductCodeSuccess() throws ServletException, IOException, SQLException {
	
    	
    	String nome = "Erba Vita Aloe Vera 200 ml";
    	String codice = "927116436";
    	String marchio = "Erba vita";
    	String produttore = "Erba vita SPA";
    	String formato = "tubo";
    	
	
    }
}
