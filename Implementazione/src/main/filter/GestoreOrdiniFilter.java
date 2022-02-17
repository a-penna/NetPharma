package main.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GestoreOrdiniFilter implements Filter{ 

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		
		HttpSession session = hrequest.getSession(false); 
		boolean loggedIn = session != null && session.getAttribute("gestoreOrdiniRoles")!= null;
		
		if(!loggedIn) { 
			hresponse.sendRedirect(hresponse.encodeRedirectURL("/NetPharma/login.jsp"));
		} else {
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException { }

}

