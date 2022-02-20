<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<%	
boolean isCliente = request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null;
boolean isGestore = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
													|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);
%>

<div class="container-fluid pt-5">   
<nav class="navbar navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="<%=response.encodeURL(request.getContextPath() + "/homepage.jsp")%>">
  <img src="<%=request.getContextPath()%>/imgs/logo5.jpeg" alt="NetPharma" width="50" height="50" class="rounded-circle" alt="NetPharma">
  </a>
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
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        	<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-justify" viewBox="0 0 16 16">
  				<path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
			</svg>
          Categorie
        </a>
        <div class="dropdown-menu mydark-dropdown-menu bg-dark" id="categorie" aria-labelledby="navbarDropdown">
        </div>
      </li>
      <script>
	      function validateSearch(obj) {
				var searchBar = document.getElementsByName("cerca-per-prodotto")[0];
				if (searchBar.value.trim() != "") {
					obj.submit();
				}
			}	
      </script>
	    <form class="form-inline my-2 my-lg-0" action="<%=response.encodeURL(request.getContextPath() + "/RicercaProdotto")%>" method="post" onsubmit="event.preventDefault(); validateSearch(this)">
	      <input class="form-control mr-sm-2" type="cerca-per-prodotto" name="cerca-per-prodotto" placeholder="Cerca per prodotto" aria-label="Cerca per prodotto">
	      <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Cerca</button>
	    </form>
	    <%if(isGestore) { %>
	    	<li class="nav-item">
        	<a class="nav-link" href="homepageGestori.jsp">Torna alla tua HomePage<span class="sr-only">(current)</span></a>
	        </li>
	    <% } %>
    </ul>
    <ul class="navbar-nav ml-md-auto">
    	<li class="nav-item">
	        <a class="nav-link" href="<%=response.encodeURL(request.getContextPath() + "/carrello.jsp")%>">
	        	<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-cart3" viewBox="0 0 16 16">
  					<path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l.84 4.479 9.144-.459L13.89 4H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
				</svg>		Carrello</a>
      	</li> 
    </ul>
      	
      <%if (isCliente || isGestore) { %>
        <div class="my-2 my-lg-0 ml-md-3">
	      	<a href="<%=response.encodeURL(request.getContextPath() + "/Logout")%>" class="btn btn-light">Logout
	      		<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-box-arrow-in-left" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M10 3.5a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 1 1 0v2A1.5 1.5 0 0 1 9.5 14h-8A1.5 1.5 0 0 1 0 12.5v-9A1.5 1.5 0 0 1 1.5 2h8A1.5 1.5 0 0 1 11 3.5v2a.5.5 0 0 1-1 0v-2z"/>
				  <path fill-rule="evenodd" d="M4.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H14.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
				</svg></a>
      	</div>
      <%} else { %>
    	  <div class="my-2 my-lg-0 ml-md-3">
	      	<a href="<%=response.encodeURL(request.getContextPath() + "/login.jsp")%>" class="btn btn-light">Accedi
	      		<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-box-arrow-in-right" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M6 3.5a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 0-1 0v2A1.5 1.5 0 0 0 6.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-8A1.5 1.5 0 0 0 5 3.5v2a.5.5 0 0 0 1 0v-2z"/>
				  <path fill-rule="evenodd" d="M11.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H1.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
				</svg></a>
    	</div>
     <% } %>
  </div>
</nav>
</div>