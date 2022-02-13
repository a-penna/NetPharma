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
	  <img src="<%=request.getContextPath()%>/imgs/logo5.jpeg" alt="NetPharma" width="50" height="50" class="rounded-circle alt="NetPharma" ></a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/homepage.jsp")%>">
	        	<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-house-door-fill" viewBox="0 0 16 16">
				  <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"/>
				</svg>	Home<span class="sr-only">(current)</span></a>
	      </li>
	      <%if (isGestoreCatalogo) {%>
		      <li class="nav-item">
		        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/gestoreCatalogo/aggiungiProdotto.jsp")%>">
		        	<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
					  <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"/>
					</svg>	Gestione Catalogo<span class="sr-only">(current)</span></a>
		      </li>
		  <%} %>
		   <%if (isGestoreOrdini) {%>
	      <li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/gestoreOrdini/ListaOrdini.jsp")%>">
	        	<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-border-width" viewBox="0 0 16 16">
				  <path d="M0 3.5A.5.5 0 0 1 .5 3h15a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H.5a.5.5 0 0 1-.5-.5v-2zm0 5A.5.5 0 0 1 .5 8h15a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H.5a.5.5 0 0 1-.5-.5v-1zm0 4a.5.5 0 0 1 .5-.5h15a.5.5 0 0 1 0 1H.5a.5.5 0 0 1-.5-.5z"/>
				</svg>	Ordini<span class="sr-only">(current)</span></a>
	      </li>
	      <%} %>
	    </ul>
	        <div class="my-2 my-lg-0">
		      	<a href="<%=response.encodeURL(request.getContextPath() + "/Logout")%>" class="btn btn-light">Logout
		      		<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-box-arrow-left" viewBox="0 0 16 16">
					  <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
					  <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
					</svg></a>
	      	</div>
	  </div>
	</nav>
	</div>
<% } %>