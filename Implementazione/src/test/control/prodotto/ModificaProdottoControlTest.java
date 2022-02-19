package test.control.prodotto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import main.bean.Prodotto;
import main.control.prodotto.ModificaProdottoControl;
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

public class ModificaProdottoControlTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ModificaProdottoControl spy;
    private ProdottoDAO prodottoModel;

    @BeforeEach
    void setUp() throws Exception {
    	request = Mockito.mock(HttpServletRequest.class) ;
		response = Mockito.mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		Mockito.when(request.getSession(false)).thenReturn(session);
		Mockito.when(session.getAttribute("gestoreCatalogoRoles")).thenReturn("true");
		spy = Mockito.spy(new ModificaProdottoControl());
		prodottoModel = Mockito.mock(ProdottoDAO.class);
		spy.setProdottoDAO(prodottoModel);
		Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
		ServletContext context = Mockito.mock(ServletContext.class); 
		Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
		Mockito.when(spy.getServletContext()).thenReturn(context);
    }
    
    private void sendParameter(String nome, String codice, String marchio, String produttore, String formato, String descrizione, String prezzo, String disponibilita) {
    	Mockito.when(request.getParameter("nome")).thenReturn(nome);
    	Mockito.when(request.getParameter("marchio")).thenReturn(marchio);
    	Mockito.when(request.getParameter("produttore")).thenReturn(produttore);
    	Mockito.when(request.getParameter("formato")).thenReturn(formato);
    	Mockito.when(request.getParameter("descrizione")).thenReturn(descrizione);
    	Mockito.when(request.getParameter("disponibilita")).thenReturn(disponibilita);
    	Mockito.when(request.getParameter("prodotto")).thenReturn(codice);
    	Mockito.when(request.getParameter("prezzo")).thenReturn(prezzo);
    }
    
    private void verifyFormReturnAttribute(String nome, String codice, String marchio, String produttore, String formato, String descrizione, String prezzo, String disponibilita) {
    	Mockito.verify(request).setAttribute("nome", Utility.filter(nome));
    	Mockito.verify(request).setAttribute("prezzo", prezzo);
    	Mockito.verify(request).setAttribute("marchio", Utility.filter(marchio));
    	Mockito.verify(request).setAttribute("descrizione", Utility.filter(descrizione));
    	Mockito.verify(request).setAttribute("produttore", Utility.filter(produttore));
    	Mockito.verify(request).setAttribute("formato", Utility.filter(formato));
    	Mockito.verify(request).setAttribute("codice", codice);
    	Mockito.verify(request).setAttribute("disponibilita", disponibilita);
    }

    @Test
   	public void testSuccess() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "885";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		
    	Mockito.when(prodottoModel.checkProdotto(Integer.parseInt(codice))).thenReturn(true);
		spy.doPost(request,response);
    	
    	Prodotto prodotto = new Prodotto();
		prodotto.setId(Integer.parseInt(codice));
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilita));
		prodotto.setPrezzo(new BigDecimal(prezzo));
		
		Mockito.verify(prodottoModel).doUpdate(prodotto);
		Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/successo.jsp"));
    }
    
    @Test
   	public void testErroreLunghezzaNome() throws ServletException, IOException, SQLException {
    	String nome = "";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		
		spy.doPost(request,response);
	    	
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
   		
		Mockito.verify(request).setAttribute("erroreNome", "true");
		verifyFormReturnAttribute(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		Mockito.verify(response).encodeURL("/gestoreCatalogo/modificaProdotto.jsp");
    }
    
    @Test
    public void testErroreCodiceNonPresente() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "887895";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	Mockito.when(prodottoModel.checkProdotto(Integer.parseInt(codice))).thenReturn(false);
    	spy.setProdottoDAO(prodottoModel);
    	
    	spy.doPost(request,response);
    
    	Mockito.verify(response).sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
    }
    
    @Test
    public void testErroreLunghezzaMarchio() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	spy.doPost(request,response);
    	
    	Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
   		
		Mockito.verify(request).setAttribute("erroreMarchio", "true");
		verifyFormReturnAttribute(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		Mockito.verify(response).encodeURL("/gestoreCatalogo/modificaProdotto.jsp");
	}
    
    @Test
    public void testErroreLunghezzaProduttore() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa, PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	Prodotto prodotto = new Prodotto();
		prodotto.setId(Integer.parseInt(codice));
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilita));
		prodotto.setPrezzo(new BigDecimal(prezzo));
    	
		Mockito.when(prodottoModel.checkProdotto(Integer.parseInt(codice))).thenReturn(true);
    	Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(prodottoModel).doUpdate(prodotto);
    	
    	spy.doPost(request,response);
   		
    	Mockito.verify(response).encodeURL("/error/genericError.jsp");
    }
    
    @Test
    public void testErroreLunghezzaFormato() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine, PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	Prodotto prodotto = new Prodotto();
		prodotto.setId(Integer.parseInt(codice));
		prodotto.setNome(nome);
		prodotto.setMarchio(marchio);
		prodotto.setProduttore(produttore);
		prodotto.setFormato(formato);
		prodotto.setDescrizione(descrizione);
		prodotto.setDisponibilita(Integer.parseInt(disponibilita));
		prodotto.setPrezzo(new BigDecimal(prezzo));
    	
		Mockito.when(prodottoModel.checkProdotto(Integer.parseInt(codice))).thenReturn(true);
    	Mockito.doThrow(new MysqlDataTruncation("", 0, false, false, 0, 0, 0)).when(prodottoModel).doUpdate(prodotto);
    	
    	spy.doPost(request,response);
   		
    	Mockito.verify(response).encodeURL("/error/genericError.jsp");
    }
    
    @Test
    public void testErroreLunghezzaDescrizione() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "";
    	String prezzo = "13";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	spy.doPost(request,response);
    	
    	Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
   		
		Mockito.verify(request).setAttribute("erroreDescrizione", "true");
		verifyFormReturnAttribute(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		Mockito.verify(response).encodeURL("/gestoreCatalogo/modificaProdotto.jsp");
	}
    
    @Test
    public void testPrezzoNonPositivo() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "0";
    	String disponibilita = "100";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	spy.doPost(request,response);
    	
    	Mockito.verify(request, Mockito.never()).setAttribute("erroreDisponibilita", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
   		
		Mockito.verify(request).setAttribute("errorePrezzo", "true");
		verifyFormReturnAttribute(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		Mockito.verify(response).encodeURL("/gestoreCatalogo/modificaProdotto.jsp");
	}
    
    @Test
    public void testDisponibilitaNonPositiva() throws ServletException, IOException, SQLException {
    	String nome = "PAPAYA FERMENTATA FQ BUSTINE, Antiossidante naturale";
    	String codice = "890";
    	String marchio = "Erba Vita";
    	String produttore = "Erba Vita group spa";
    	String formato = "bustine";
    	String descrizione = "Antiossidante naturale";
    	String prezzo = "13";
    	String disponibilita = "0";
    	
    	sendParameter(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
    	
    	spy.doPost(request,response);
    	
    	Mockito.verify(request, Mockito.never()).setAttribute("errorePrezzo", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreDescrizione", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreNome", "true");
		Mockito.verify(request, Mockito.never()).setAttribute("erroreMarchio", "true");
   		
		Mockito.verify(request).setAttribute("erroreDisponibilita", "true");
		verifyFormReturnAttribute(nome, codice, marchio, produttore, formato, descrizione, prezzo, disponibilita);
		Mockito.verify(response).encodeURL("/gestoreCatalogo/modificaProdotto.jsp");
	}
}
