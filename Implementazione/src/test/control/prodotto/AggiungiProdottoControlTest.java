package test.control.prodotto;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import main.bean.Prodotto;
import main.control.account.LoginControl;
import main.control.prodotto.AggiungiProdottoControl;
import main.model.AccountDAO;
import main.model.ProdottoDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AggiungiProdottoControlTest {

	
	private HttpServletRequest request;
    private HttpServletResponse response;
    @InjectMocks
    private MockitoSession session;

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
    public void testInsertSuccess() throws IOException , ServletException , SQLException {
    	
    	 
    	
    	String idStr = "927116438";
		String nome = "Erba vita gel 200 ml";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita SPA";
		String formato = "tubo";
		String descrizione = "Crema protettiva per viso e corpo a base di aloe vera";
		String disponibilitaStr = "64";
		String categoria = "Viso & corpo";
		String prezzoStr = "4.49";
		int id = 0, disponibilita = 0;
		Prodotto prodotto = new Prodotto();
		
		prodotto.setId(927116438);
		prodotto.setNome("Erba vita gel 200 ml");
		prodotto.setMarchio("Erba Vita");
		prodotto.setProduttore("Erba Vita SPA");
		prodotto.setFormato("tubo");
		prodotto.setDescrizione("Crema protettiva per viso e corpo a base di aloe vera");
		prodotto.setDisponibilita(64);
		prodotto.setCategoria("Viso & corpo");
		prodotto.setPrezzo(new BigDecimal(prezzoStr));
		
		ServletContext servletContext = mock(ServletContext.class);
		HttpSession session = mock(HttpSession.class);
		Mockito.when(request.getSession(false)).thenReturn(session);
		Mockito.when(request.getSession(false).getAttribute("gestoreCatalogoRoles")).thenReturn("true");
		//Mockito.when();
		Mockito.when(request.getParameter("id")).thenReturn(idStr);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("marchio")).thenReturn(marchio);
		Mockito.when(request.getParameter("produttore")).thenReturn(produttore);
		Mockito.when(request.getParameter("formato")).thenReturn(formato);
		Mockito.when(request.getParameter("descrizione")).thenReturn(descrizione);
		Mockito.when(request.getParameter("disponibilita")).thenReturn(disponibilitaStr);
		Mockito.when(request.getParameter("categoria")).thenReturn(categoria);
		Mockito.when(request.getParameter("prezzo")).thenReturn(prezzoStr);
		id = Integer.parseInt(idStr);
		disponibilita = Integer.parseInt(disponibilitaStr);
		
		AggiungiProdottoControl serv = new AggiungiProdottoControl();
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		doCallRealMethod().when(model).doSave(prodotto);
		
		serv.setProdottoDAO(model);
		serv.doPost(request,response);
		assertEquals(""+id, request.getAttribute("id"));
		
    }
	
	
}
