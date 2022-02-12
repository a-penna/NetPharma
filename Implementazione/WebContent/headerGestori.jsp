<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%	
boolean isGestoreOrdini = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null);
boolean isGestoreCatalogo = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);
if(isGestoreOrdini || isGestoreCatalogo) {
	boolean isCliente = request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null;
	%>
	<div class="container-fluid pt-5">   
	<nav class="navbar navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
	  <a class="navbar-brand" href="#"></a>
	  <img src="<%=request.getContextPath()%>/imgs/logo.jpg" alt="NetPharma" width="50" height="50" class="rounded-circle alt="NetPharma" ></a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/homepage.jsp")%>">Home<span class="sr-only">(current)</span></a>
	      </li>
	      <%if (isGestoreCatalogo) {%>
		      <li class="nav-item">
		        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/gestoreCatalogo/aggiungiProdotto.jsp")%>">Gestione Catalogo<span class="sr-only">(current)</span></a>
		      </li>
		  <%} %>
		   <%if (isGestoreOrdini) {%>
	      <li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/gestoreOrdini/ListaOrdini.jsp")%>">Ordini<span class="sr-only">(current)</span></a>
	      </li>
	      <%} %>
	    </ul>
	        <div class="my-2 my-lg-0">
		      	<a href="<%=response.encodeURL(request.getContextPath() + "/Logout")%>" class="btn btn-light">Logout</a>
	      	</div>
	  </div>
	</nav>
	</div>
<% } %>