<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%	
boolean isCliente = request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null;
boolean isGestore = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
													|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);
%>

<div class="container-fluid pt-5">   
<nav class="navbar navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#"></a>
  <img src="<%=request.getContextPath()%>/imgs/logo.jpg" alt="NetPharma" width="50" height="50" class="rounded-circle" alt="NetPharma">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/homepage.jsp")%>">Home<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Categorie
        </a>
        <div class="dropdown-menu mydark-dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
          <a class="dropdown-item mydark-dropdown-item" href="#">Mamma&Bambino</a>
          <a class="dropdown-item mydark-dropdown-item" href="#">Cosmetici</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item mydark-dropdown-item" href="#">Prevenzione Antivirale</a>
        </div>
      </li>
	    <form class="form-inline my-2 my-lg-0" action="<%=response.encodeURL(request.getContextPath() + "/RicercaProdotto")%>" method="post">
	      <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
	      <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
	    </form>
	    <%if(isGestore) { %>
	    	<li class="nav-item">
        	<a class="nav-link" href="homepageGestori.jsp">Torna alla tua HomePage<span class="sr-only">(current)</span></a>
	        </li>
	    <% } %>
		<li class="nav-item">
        <a class="nav-link" href="#">Aiuto<span class="sr-only">(current)</span></a>
        </li>
    </ul>
    <ul class="navbar-nav ml-md-auto">
    	<li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/carrello.jsp")%>">Carrello</a>
      	</li> 
    </ul>
      	
      <%if (isCliente || isGestore) { %>
        <div class="my-2 my-lg-0 ml-md-3">
	      	<a href="<%=response.encodeURL(request.getContextPath() + "/Logout")%>" class="btn btn-light">Logout</a>
      	</div>
      <%} else { %>
    	  <div class="my-2 my-lg-0 ml-md-3">
	      	<a href="<%=response.encodeURL(request.getContextPath() + "/login.jsp")%>" class="btn btn-light">Accedi</a>
    	</div>
     <% } %>
  </div>
</nav>
</div>