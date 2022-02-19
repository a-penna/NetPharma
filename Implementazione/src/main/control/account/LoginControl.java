package main.control.account;

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

import main.bean.Account;
import main.bean.Ruoli;
import main.bean.UtenteRegistrato;
import main.model.AccountDAO;
import main.model.RuoliDAO;
import main.model.UtenteRegistratoDAO;
import main.utils.Utility;
 
@WebServlet("/Login")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp")); 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			boolean isCliente = request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null;
			boolean isGestore = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
															|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);
			
			if (isGestore) { 
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepageGestori.jsp"));
				return;
			}
			
			if (isCliente) { 
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
				return;
			}
		
			AccountDAO model; 
			
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			model = new AccountDAO(ds);		    
			
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
						isCliente = true;
					}
					
					if (roles.contains(Ruoli.Ruolo.GO)) {
						request.getSession().setAttribute("gestoreOrdiniRoles", "true");
						isGestore = true;
					}
					
					if (roles.contains(Ruoli.Ruolo.GC)) {
						request.getSession().setAttribute("gestoreCatalogoRoles", "true");	
						isGestore = true;
					}
					
					request.getSession().setAttribute("user", username);
					request.getSession().setAttribute("id", bean.getId());
					request.getSession().setAttribute("orderCount", bean.getOrderCount());
					
					UtenteRegistratoDAO utenteRegistratoModel = new UtenteRegistratoDAO(ds);
					UtenteRegistrato user = utenteRegistratoModel.doRetrieveByAccount(bean.getId());
					
					request.getSession().setAttribute("email", user.getEmail());
					
					if (isGestore) { 
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepageGestori.jsp"));
						return;
					}
					
					if (isCliente) { 
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
						return;
					}
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
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
				return;
			}
	}	

}
