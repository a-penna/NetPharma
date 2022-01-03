package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Logout")
public class LogoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		if(request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null) {
			request.getSession().removeAttribute("elettoreRoles");
		}

		if(request.getSession(false) != null && request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null) {
			request.getSession().removeAttribute("gestoreOrdiniRoles");
		}
				
		if(request.getSession(false) != null && request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null) {
			request.getSession().removeAttribute("gestoreCatalogoRoles");
		}
		
		request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath() + "/login.jsp");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
    
}
