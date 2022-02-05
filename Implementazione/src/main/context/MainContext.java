package main.context;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


@WebListener
public class MainContext implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) { 

		ServletContext context = event.getServletContext();
		
		DataSource ds = null; 
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env"); 

			ds = (DataSource) envCtx.lookup("jdbc/NetPharmaDB"); 

		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}

		context.setAttribute("DataSource", ds);
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		context.removeAttribute("DataSource");
	}
}
