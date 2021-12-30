package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Account;
import bean.Ruoli;
import model.*;
import utils.*;
 
@WebServlet("/Login")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp")); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			boolean loggedIn = request.getSession(false) != null && (request.getSession(false).getAttribute("clienteRoles")!= null 
																  || request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
																  || request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);
			
			if (loggedIn) { 
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
				return;
			}
			
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		    AccountDAO model = new AccountDAO(ds);
			
			String username = request.getParameter("username");
			String password = request.getParameter("password"); 
			
			if (username == null || password == null) {   
			 	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
			 	return;
			}
			
			try {
				boolean usernameExists = model.checkUsername(username); 
			
				if (!usernameExists) { 
					request.setAttribute("erroreUser", "true");
					request.setAttribute("errorePass", "true");
					username = Utility.filter(username); 
					password = Utility.filter(password);
					request.setAttribute("username", username);
					request.setAttribute("password", password);
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/login.jsp"));
					dispatcher.forward(request, response);
					return;
				}  
				
				Account bean = model.authenticate(username, password); 
				
				if (bean != null) {
					RuoliDAO ruoliModel = new RuoliDAO(ds);
					Ruoli r = ruoliModel.doRetrieveByAccount(bean.getId());
					Collection<Ruoli.Ruolo> roles = r.getRuoli();
					
					if (roles.contains(Ruoli.Ruolo.CL)) {
						request.getSession().setAttribute("clienteRoles", "true");	
					}
					
					if (roles.contains(Ruoli.Ruolo.GO)) {
						request.getSession().setAttribute("gestoreOrdiniRoles", "true");	
					}
					
					if (roles.contains(Ruoli.Ruolo.GC)) {
						request.getSession().setAttribute("gestoreCatalogoRoles", "true");	
					}
					
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
					return;
				} else { 
					request.setAttribute("errorePass", "true");
					username = Utility.filter(username);
					password = Utility.filter(password);
					request.setAttribute("username", username);
					request.setAttribute("password", password);
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/login.jsp"));
					dispatcher.forward(request, response);
					return;
				}
					
			} catch(SQLException e) {
				Utility.printSQLException(e);
				//response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
				return;
			}
	}	

}
