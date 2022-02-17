package test.control.prodotto;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import main.bean.Prodotto;

import main.control.prodotto.AggiungiProdottoControl;

import main.model.ProdottoDAO;


import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AggiungiProdottoControlTest {

	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private AggiungiProdottoControl spy;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		spy = Mockito.spy(new AggiungiProdottoControl());
		Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
		ServletContext context = Mockito.mock(ServletContext.class); 
		Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
		Mockito.when(spy.getServletContext()).thenReturn(context);
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
		
		
		HttpSession session = mock(HttpSession.class);
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		Mockito.when(request.getSession(false)).thenReturn(session);
		Mockito.when(session.getAttribute("gestoreCatalogoRoles")).thenReturn("true");
		
		Mockito.when(request.getParameter("id")).thenReturn(idStr);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("marchio")).thenReturn(marchio);
		Mockito.when(request.getParameter("produttore")).thenReturn(produttore);
		Mockito.when(request.getParameter("formato")).thenReturn(formato);
		Mockito.when(request.getParameter("descrizione")).thenReturn(descrizione);
		Mockito.when(request.getParameter("disponibilita")).thenReturn(disponibilitaStr);
		Mockito.when(request.getParameter("categoria")).thenReturn(categoria);
		Mockito.when(request.getParameter("prezzo")).thenReturn(prezzoStr);
		Mockito.when(request.getPart("foto")).thenReturn(null);
		
		
		id = Integer.parseInt(idStr);
		disponibilita = Integer.parseInt(disponibilitaStr);
		
		
		
		Mockito.doNothing().when(model).doSave(prodotto);
		
		spy.setProdottoDAO(model);
		spy.doPost(request,response);
		
		Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
		
    }
	
	
}
