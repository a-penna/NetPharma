package test.control.prodotto;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import main.bean.Prodotto;

import main.control.prodotto.AggiungiProdottoControl;

import main.model.ProdottoDAO;
import main.utils.Utility;

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
    private HttpSession session;
    private AggiungiProdottoControl spy;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		Mockito.when(request.getSession(false)).thenReturn(session);
		Mockito.when(session.getAttribute("gestoreCatalogoRoles")).thenReturn("true");
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
    
    private void sendParameter(String idStr, String nome, String marchio, String produttore, String formato, 
				String descrizione, String disponibilitaStr, String prezzoStr) throws IOException, ServletException {
    	Mockito.when(request.getParameter("id")).thenReturn(idStr);
		Mockito.when(request.getParameter("nome")).thenReturn(nome);
		Mockito.when(request.getParameter("marchio")).thenReturn(marchio);
		Mockito.when(request.getParameter("produttore")).thenReturn(produttore);
		Mockito.when(request.getParameter("formato")).thenReturn(formato);
		Mockito.when(request.getParameter("descrizione")).thenReturn(descrizione);
		Mockito.when(request.getParameter("disponibilita")).thenReturn(disponibilitaStr);
		Mockito.when(request.getParameter("prezzo")).thenReturn(prezzoStr);
		Mockito.when(request.getPart("foto")).thenReturn(null);
	}
    
    private void verifyFormReturnAttribute(String idStr, String nome, String marchio, String produttore, String formato, 
			String descrizione, String disponibilitaStr, String prezzoStr) {
		formato = Utility.filter(formato);
    	Mockito.verify(request).setAttribute("nome", Utility.filter(nome));
		Mockito.verify(request).setAttribute("prezzo", prezzoStr);
		Mockito.verify(request).setAttribute("marchio", Utility.filter(marchio));
		Mockito.verify(request).setAttribute("descrizione", descrizione = Utility.filter(descrizione));
		Mockito.verify(request).setAttribute("produttore", produttore = Utility.filter(produttore));
		Mockito.verify(request).setAttribute("formato", formato = Utility.filter(formato));
		Mockito.verify(request).setAttribute("codice", idStr);
		Mockito.verify(request).setAttribute("disponibilita", disponibilitaStr);
    }
    
    @Test
    public void testInsertSuccess() throws IOException , ServletException , SQLException {
		String nome = "Erba Vita Aloe Vera Gel Protettivo Idratante 200 ml";
		String idStr = "927116436";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "tubo";
		String descrizione = "Descrizione Aloe Vera Gel Erba Vita";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		Prodotto prodotto = new Prodotto();
		prodotto.setId(id);
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilitaStr));
		prodotto.setPrezzo(new BigDecimal(prezzoStr));
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(model).doSave(prodotto);
		Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
    }
	
    
    @Test
    public void testInsertNoName() throws IOException , ServletException , SQLException {
    	String nome = "";
		String idStr = "927116439";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("codiceEsistente", "true");
   		
		Mockito.verify(request).setAttribute("erroreNome", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
	}
	
    
    @Test
    public void testCodeAlreadyExist() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "885";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);
		
		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(true);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("cerroreNome", "true");
		
		Mockito.verify(request).setAttribute("codiceEsistente", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
    }
    
    @Test
    public void testMarchioValueError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116439";
		String marchio = "";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("codiceEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("cerroreNome", "true");
		
		Mockito.verify(request).setAttribute("erroreMarchio", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
    }
    
    @Test
    public void testProduttoreLenghtError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116437";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa, PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);

		Prodotto prodotto = new Prodotto();
		prodotto.setId(id);
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilitaStr));
		prodotto.setPrezzo(new BigDecimal(prezzoStr));
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(model).doSave(prodotto);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(request).setAttribute("message", "Probabilmente alcuni dati sono troppo lunghi per essere inseriti");
    	Mockito.verify(response).encodeURL("/error/genericError.jsp");
    }
    
    @Test
    public void testFormatoLenghtError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116437";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine, PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);

		Prodotto prodotto = new Prodotto();
		prodotto.setId(id);
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilitaStr));
		prodotto.setPrezzo(new BigDecimal(prezzoStr));
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(model).doSave(prodotto);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		Mockito.verify(request).setAttribute("message", "Probabilmente alcuni dati sono troppo lunghi per essere inseriti");
    	Mockito.verify(response).encodeURL("/error/genericError.jsp");
    }
    
    @Test
    public void testDescrizioneValueError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116437";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "";
		String prezzoStr = "13";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);
		
		spy.doPost(request,response);
		
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("codiceEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("cerroreNome", "true");
		
		Mockito.verify(request).setAttribute("erroreDescrizione", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
    }
    
    @Test
    public void testPriceValueError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116437";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "0";
		String disponibilitaStr = "100";
		int id = Integer.parseInt(idStr);

		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);

		spy.doPost(request,response);
		
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("codiceEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("cerroreNome", "true");
		
		Mockito.verify(request).setAttribute("errorePrezzo", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
    }
    
    @Test
    public void testDisponibilitaValueError() throws IOException , ServletException , SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
		String idStr = "927116437";
		String marchio = "Erba Vita";
		String produttore = "Erba Vita group spa";
		String formato = "bustine";
		String descrizione = "Antiossidante naturale";
		String prezzoStr = "13";
		String disponibilitaStr = "0";
		int id = Integer.parseInt(idStr);
		
		sendParameter(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr);
		
		ProdottoDAO model = Mockito.mock(ProdottoDAO.class);
		Mockito.when(model.checkProdotto(id)).thenReturn(false);
		spy.setProdottoDAO(model);

		spy.doPost(request,response);

		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("codiceEsistente", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreCodice", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("cerroreNome", "true");
		
		Mockito.verify(request).setAttribute("erroreDisponibilita", "true");
		verifyFormReturnAttribute(idStr, nome, marchio, produttore, formato, descrizione, disponibilitaStr, prezzoStr); 
		Mockito.verify(response).encodeURL("/gestoreCatalogo/aggiungiProdotto.jsp");
    }
}