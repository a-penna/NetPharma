package main.control.account;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import main.bean.Account;
import main.bean.Ruoli;
import main.bean.UtenteRegistrato;
import main.model.AccountDAO;
import main.model.UtenteRegistratoDAO;
import main.utils.Utility;

@WebServlet("/Registrazione")
public class RegistrazioneControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDAO accountModelTest;
	private UtenteRegistratoDAO utenteModelTest;

	public void setAccountDAO(AccountDAO model) {
		this.accountModelTest = model;
	}
	
	public void setUtenteRegistratoDAO(UtenteRegistratoDAO model) {
		this.utenteModelTest = model;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			boolean loggedIn = request.getSession(false) != null && (request.getSession(false).getAttribute("clienteRoles")!= null 
																  || request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
																  || request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);

			if(loggedIn) {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
				return;
			}

			String sesso = request.getParameter("sesso");
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String nascita = request.getParameter("nascita");
			String password = request.getParameter("password");

			
			if (sesso == null  || nome == null || cognome == null || username == null || nascita == null || password == null) {
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/registrazione.jsp"));
				dispatcher.forward(request, response);
				return;
			}
			
			if (!sesso.equals("M") && !sesso.equals("F") && !sesso.equals("I")) {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/registrazione.jsp"));
	 			return;
			}
			
			boolean error = false;
			
			if (!Utility.checkNomeCognome(nome)) {
				request.setAttribute("erroreNome", "true");
				error = true;
			}
			
			if (!Utility.checkNomeCognome(cognome)) {
				request.setAttribute("erroreCognome", "true");
				error = true;
			}

			AccountDAO accountModel;
			DataSource ds = null;
			
			if(accountModelTest != null) {
				accountModel = accountModelTest;
			}else {
				ds = (DataSource) getServletContext().getAttribute("DataSource");
				accountModel = new AccountDAO(ds);
			}
			boolean usernameIsValid = Utility.checkUsername(username);
			if (!usernameIsValid) {
				request.setAttribute("erroreUsername", "true");
				error = true;
			}
			
			try {
				if (usernameIsValid && accountModel.checkUsername(username)) {
					request.setAttribute("usernameEsistente", "true");
					error = true;
				}
			} catch(SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
				return;
			}
			
			boolean emailIsValid = Utility.checkEmail(email);
			if (!emailIsValid) {
				request.setAttribute("erroreEmail", "true");
				error = true;
			}
			
			try {
				UtenteRegistratoDAO userModel;
				if(utenteModelTest != null) {
					userModel = utenteModelTest;
				}else {
					userModel = new UtenteRegistratoDAO(ds);
				}
				
				if (emailIsValid && userModel.checkEmail(email)) {
					request.setAttribute("emailEsistente", "true");
					error = true;
				}
			} catch(SQLException e) {
				Utility.printSQLException(e);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
				return;
			}
			
			if (!Utility.checkEta(nascita)) {
				request.setAttribute("erroreData", "true");
				error = true;
			}
			
			if (!Utility.checkPassword(password)) {
				request.setAttribute("errorePassword", "true");
				error = true;
			}
			
			if (error) { 
				sesso = Utility.filter(sesso);
				nome = Utility.filter(nome);
				cognome = Utility.filter(cognome);
				username = Utility.filter(username);
				email = Utility.filter(email);
				nascita = Utility.filter(nascita);
				password = Utility.filter(password);
				request.setAttribute("sesso", sesso);
				request.setAttribute("nome", nome);
				request.setAttribute("cognome", cognome);
				request.setAttribute("username", username);
				request.setAttribute("email", email);
				request.setAttribute("nascita", nascita);
				request.setAttribute("password", password);
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/registrazione.jsp"));
				dispatcher.forward(request, response);
				return;
			}
			
			Account acc = new Account(0, username, password, 0);
			UtenteRegistrato user = new UtenteRegistrato(sesso, nome, cognome, email, Utility.toSqlDate(Utility.formatStringToDate(nascita)), 0);
			Ruoli r = new Ruoli();
			r.addRuolo(Ruoli.Ruolo.CL);
			
			try {
				boolean saved = accountModel.register(acc, user, r);
				if (!saved) {
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
					return;
				}
			} catch(com.mysql.cj.jdbc.exceptions.MysqlDataTruncation e) {
				Utility.printSQLException(e);
				request.setAttribute("message", "Probabilmente alcuni dati sono troppo lunghi per essere inseriti");
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error/genericError.jsp"));
				dispatcher.forward(request, response);
				return;
			} catch(SQLException e) {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/genericError.jsp"));
				return;
			}
			
			request.getSession().setAttribute("clienteRoles", "true");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
			return;
	}

}
