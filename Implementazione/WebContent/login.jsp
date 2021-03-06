<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.*"%>

<%
	if(request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
											|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null)) {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepageGestori.jsp"));
		return;
	}

	if(request.getSession(false) != null && request.getSession(false).getAttribute("clienteRoles")!= null) {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
	 	return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online, login, autenticazione">
	<meta name="description" content="Login">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
    <title>NetPharma &dash; Login</title>
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 
	
	<script>
        function validate(obj) {	
            var valid = true;	
			
            var login = document.getElementsByName("username")[0];
            if(login.value.trim() == "") {
                valid = false;
                login.classList.add("is-invalid");
                login.focus();
            } else {
            	login.classList.remove("is-invalid");
            }
            
            var pass = document.getElementsByName("password")[0];
            if(pass.value.trim() == "") {
                valid = false;
                pass.classList.add("is-invalid");
                pass.focus();
            } else {
            	pass.classList.remove("is-invalid");
            }
            
            if(valid) obj.submit();
        }
    </script> 
</head>
<body class="container-login pt-5">
<%@ include file="header.jsp"%>

<div class="card text-white bg-dark col-lg-4 col-md-4 col-sm-4 container justify-content-center">
  	<div class="card-header col-lg-4 col-md-4 col-sm-4 container justify-content-center">
	<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
  		<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
  		<path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
	</svg>
	</div>
	<div class="bg-login text-black pb-3">
		<form method="post" action="<%=response.encodeURL(request.getContextPath() + "/Login")%>"> 
			<fieldset>
		    	<div class="form-group">
		             <label for="username">Username&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend1">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				            <%
								if (request.getAttribute("erroreUser") != null) {
									%><input type="text" class="form-control is-invalid" id="username" aria-describedby="inputGroupPrepend1" placeholder="Inserisci username" value="<%=request.getAttribute("username")%>" name="username"><% 
								} else if (request.getAttribute("username") != null) {
									%><input type="text" class="form-control is-valid" id="username" aria-describedby="inputGroupPrepend1" placeholder="Inserisci username" value="<%=request.getAttribute("username")%>" name="username"><% 
								} else {
									%><input type="text" class="form-control" id="username" aria-describedby="inputGroupPrepend1" placeholder="Inserisci username" name="username"><% 
								}
							%>
	                        <div class="valid-feedback">Corretto&excl;</div>
	                        <div class="invalid-feedback">Username errato&excl;</div> 
				      </div>
				</div>
			    <div class="form-group">
			         <label for="password">Password&colon;</label>
				      <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend2">

				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
								<path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
							</svg>
						  </span>
				        </div>
			           		<%
								if (request.getAttribute("errorePass") != null) {
									%><input type="password" class="form-control is-invalid" id="password"  aria-describedby="inputGroupPrepend2" placeholder="Inserisci password" value="<%=request.getAttribute("password")%>" name="password"><% 
								} else if (request.getAttribute("password") != null) {
									%><input type="password" class="form-control" id="password" placeholder="Inserisci password"  aria-describedby="inputGroupPrepend2" value="<%=request.getAttribute("password")%>" name="password"><% 
								} else {
									%><input type="password" class="form-control" id="password" placeholder="Inserisci password"  aria-describedby="inputGroupPrepend2" name="password"><% 
								}
							%>
	                        <div class="valid-feedback">Corretto&excl;</div>
	                        <div class="invalid-feedback">Password errata&excl;</div> 
	                  </div>
		        </div>
		        <div class="container-accesso col-lg-4 col-md-4 col-sm-4 container justify-content-center">
		          	<input type="submit" class="btn btn-light" value="   LOGIN   ">
		          	</div>
		          	<div class="container-nessun-account pt-5">
		          	<label for="nessun-account">Non hai un account&quest;</label>
		   			<br>
		   			<a class="btn btn-light container" href="<%=response.encodeURL(request.getContextPath() + "/registrazione.jsp")%>" role="button">CREA ACCOUNT</a>
		          	</div>
		    	</fieldset>
		    </form>
		    </div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>