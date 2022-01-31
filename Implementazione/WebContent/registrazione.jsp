<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>

<%
boolean loggedIn = request.getSession(false) != null && (request.getSession(false).getAttribute("clienteRoles")!= null 
													  || request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
													  || request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);

if(loggedIn) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/homepage.jsp"));
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="Registrazione">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
    <title>NetPharma &dash; Registrazione</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/script.js"></script>
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
	<script>
	    function validate(obj) {	
	        var valid = true;	

	        var genderM = document.getElementsByName("sesso")[0];
            var genderF = document.getElementsByName("sesso")[1];
            var genderI = document.getElementsByName("sesso")[2];
            if(!genderM.checked && !genderF.checked) {
                valid = false;
                genderM.classList.add("is-invalid");
                genderM.focus();
                genderF.classList.add("is-invalid");
                genderI.classList.add("is-invalid");
            } else {
            	genderM.classList.remove("is-invalid");
            	genderF.classList.remove("is-invalid");
            	genderI.classList.remove("is-invalid");
            }
            
            var nome = document.getElementsByName("nome")[0];
            if(!checkNomeCognome(nome)) {
                valid = false;
                nome.classList.add("is-invalid");
                nome.focus();
            } else {
            	nome.classList.remove("is-invalid");
            }
            
            var cognome = document.getElementsByName("cognome")[0];
            if(!checkNomeCognome(cognome)) {
                valid = false;
                cognome.classList.add("is-invalid");
                cognome.focus();
            } else {
            	cognome.classList.remove("is-invalid");
            }
            
	        var username = document.getElementsByName("username")[0];
            if(!checkUsername(username)) {
                valid = false;
                username.classList.add("is-invalid");
                username.focus();
            } else {
            	username.classList.remove("is-invalid");
            }
            
            var pw = document.getElementsByName("password")[0];
            if(!checkPassword(pw)) {
                valid = false;
                pw.classList.add("is-invalid");
                pw.focus();
            } else {
            	pw.classList.remove("is-invalid");
            }
           
	        if(valid) obj.submit();
	    }
	    
	    function showPassword() {
	    	var pw = document.getElementById("password");
	    	if (pw.type === "password") {
	    	    pw.type = "text";
	    	} else {
	    		pw.type = "password";
	    	}
	    }
    
	</script> 
</head>            

<body>
<header class="py-4"></header>
<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#"></a>
  <img src="./imgs/logo.jpg" alt="NetPharma" width="50" height="50" class="rounded-circle alt="NetPharma" ></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">HomePage <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Categorie
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Mamma&Bambino</a>
          <a class="dropdown-item" href="#">Cosmetici</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Prevenzione Antivirale</a>
        </div>
      </li>
	    <form class="form-inline my-2 my-lg-0">
	      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
	      <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
	    </form>
		<li class="nav-item active">
        <a class="nav-link" href="#">Aiuto<span class="sr-only">(current)</span></a>
        </li>      
    </ul>
      <li class="nav-item ">
	        <a class="nav-link " href="#">Carrello</a>
      </li>
        <div class="my-2 my-lg-0">
	      	<a href="#" class="btn btn-light">Accedi</a>
      	  </div>
  </div>
</nav>

<div class="container my-3">
	<div class="card text-dark bg-light">
 	 <div class="card-header text-center"><h4>REGISTRAZIONE UTENTE</h4></div>
		<form action="<%=response.encodeURL(request.getContextPath() + "/Registrazione")%>" method="post" onsubmit="event.preventDefault(); validate(this)"> 
		        <fieldset>
		        <%
		               	String sesso = (String)request.getAttribute("sesso");
						if (sesso != null && sesso.equals("M")) {
							%>
		            		<div class="custom-control custom-radio">
			           		  <input type="radio" class="custom-control-input" id="maschio" name="sesso" value="M" checked>
							  <label for="maschio" class="custom-control-label">Uomo</label>
			           		</div>
			           		<div class="custom-control custom-radio"> 
			           		  <input type="radio" class="custom-control-input" id="femmina" name="sesso" value="F">
			            	  <label for="femmina" class="custom-control-label">Donna</label>
			            	</div>
			            	<div class="custom-control custom-radio mb-3"> 
			           		  <input type="radio" class="custom-control-input" id="indefinito" name="sesso" value="I">
			            	  <label for="femmina" class="custom-control-label">Indefinito</label>
			            	</div>
							<% 
						} else if (sesso != null && sesso.equals("F")) { %>
							<div class="custom-control custom-radio">
			           		 	<input type="radio" class="custom-control-input" id="maschio" name="sesso" value="M">
								<label for="maschio" class="custom-control-label">Uomo</label><br>
			           		 </div>
			           		 <div class="custom-control custom-radio">
			           		 	<input type="radio" class="custom-control-input mb-3" id="femmina" name="sesso" value="F" checked>
			            	  	<label for="femmina" class="custom-control-label">Donna</label><br>
							 </div>
							 <div class="custom-control custom-radio mb-3"> 
			           		   <input type="radio" class="custom-control-input" id="indefinito" name="sesso" value="I">
			            	   <label for="femmina" class="custom-control-label">Indefinito</label><br>
			            	 </div>
			            <%} else if (sesso != null && sesso.equals("I")) { %>
							<div class="custom-control custom-radio">
			           		 	<input type="radio" class="custom-control-input" id="maschio" name="sesso" value="M">
								<label for="maschio" class="custom-control-label">Uomo</label><br>
			           		 </div>
			           		 <div class="custom-control custom-radio">
			           		 	<input type="radio" class="custom-control-input mb-3" id="femmina" name="sesso" value="F">
			            	  	<label for="femmina" class="custom-control-label">Donna</label><br>
							 </div>
							 <div class="custom-control custom-radio mb-3"> 
			           		   <input type="radio" class="custom-control-input" id="indefinito" name="sesso" value="I" checked>
			            	   <label for="femmina" class="custom-control-label">Indefinito</label><br>
			            	 </div>
						<% 			 
						} else {
						%>  
							<div class="custom-control custom-radio">
					        	<input type="radio" id="maschio" class="custom-control-input" name="sesso" value="M">
					        	<label for="maschio" class="custom-control-label">Maschio</label><br>
					        </div>
					        <div class="custom-control custom-radio">
					        	<input type="radio" id="femmina" class="custom-control-input" name="sesso" value="F">
					        	<label for="femmina" class="custom-control-label">Femmina</label><br> 
					        </div>
					        <div class="custom-control custom-radio mb-3"> 
			           		   <input type="radio" class="custom-control-input" id="indefinito" name="sesso" value="I">
			            	   <label for="femmina" class="custom-control-label">Indefinito</label><br>
			            	   <div class="invalid-feedback">Selezionare il sesso&excl;</div>
			            	</div>	<%
						}
					%>
					<div class="form-group">
						 <div class="input-group">
			           		<%
								if (request.getAttribute("erroreNome") != null) {
									%><input type="text" placeholder="Nome&ast;" class="form-control is-invalid" id="nome" value="<%=request.getAttribute("nome")%>" name="nome"><% 
								} else if (request.getAttribute("nome") != null) {
									%><input type="text" placeholder="Nome&ast;" class="form-control" id="nome" value="<%=request.getAttribute("nome")%>" name="nome"><% 
								} else {
									%><input type="text" placeholder="Nome&ast;" class="form-control" id="nome" name="nome"><% 
								}
							%>
						<div class="invalid-feedback">Inserire nome, non sono previsti numeri o caratteri speciali&excl;</div> 
					 </div>
			        </div>   
		            <div class="form-group">
			            	<%
								if (request.getAttribute("erroreCognome") != null) {
									%><input type="text" class="form-control is-invalid" id="cognome" placeholder="Cognome&ast;" value="<%=request.getAttribute("cognome")%>" name="cognome"><% 
								} else if (request.getAttribute("cognome") != null) {
									%><input type="text" class="form-control" id="cognome" placeholder="Cognome&ast;" value="<%=request.getAttribute("cognome")%>" name="cognome"><% 
								} else {
									%><input type="text" class="form-control" id="cognome" placeholder="Cognome&ast;" name="cognome"><% 
								}
							%>
		                    <div class="invalid-feedback">Inserire cognome, non sono previsti numeri o caratteri speciali&excl;</div> 
			        </div>
			        <div class="form-group">
			            <label for="nickname">Username&ast;</label>
			            	<%
								if ((request.getAttribute("erroreUsername") != null) || (request.getAttribute("usernameEsistente") != null)) {
									%><input type="text" class="form-control is-invalid" id="username" value="<%=request.getAttribute("username")%>" name="username"><% 
								} else if (request.getAttribute("username") != null) {
									%><input type="text" class="form-control" id="username" value="<%=request.getAttribute("username")%>" name="username"><% 
								} else {
									%><input type="text" class="form-control" id="username" name="username"><% 
								}
				            	if (request.getAttribute("usernameEsistente") != null) {
									%><div class="invalid-feedback">Username gi&agrave; utilizzata&comma; si prega di sceglierne un&apos;altra&excl;</div><%
			            		} else {
									%><div class="invalid-feedback">Inserire username, puoi usare solo lettere e numeri&excl;</div><% 
								}%> 
			        </div>
			        <div class="form-group">
			            <label for="email">E&dash;mail&ast;</label>
			            	<%
								if ((request.getAttribute("erroreEmail") != null) || (request.getAttribute("emailEsistente") != null)) {
									%><input type="email" class="form-control is-invalid" id="email" value="<%=request.getAttribute("email")%>" name="email"><% 
								} else if (request.getAttribute("email") != null) {
									%><input type="email" class="form-control" id="email" value="<%=request.getAttribute("email")%>" name="email"><% 
								} else {
									%><input type="email" class="form-control" id="email" name="email"><% 
								}
			            	
			            		if (request.getAttribute("emailEsistente") != null) {
									%><div class="invalid-feedback">Email gi&agrave; utilizzata&comma; si prega di sceglierne un&apos;altra&excl;</div><%
			            		} else {
									%><div class="invalid-feedback">Questo indirizzo email non sembra essere valido&excl;</div><% 
								}%> 
			        </div>
			        <div class="form-group">
			            <label for="data">Data di nascita&ast;</label>
			            	<%
								if (request.getAttribute("erroreData") != null) {
									%><input type="date" class="form-control is-invalid" id="nascita" value="<%=request.getAttribute("nascita")%>" name="nascita"><% 
								} else if (request.getAttribute("nascita") != null) {
									%><input type="date" class="form-control" id="nascita" value="<%=request.getAttribute("nascita")%>" name="nascita"><% 
								} else {
									%><input type="date" class="form-control" id="nascita" name="nascita"><% 
								}
							%>
		                <div class="invalid-feedback">Inserire una data valida&excl;</div> 
			        </div>
			        <div class="form-group">
			            <label for="password">Password&ast;</label>
			            	<%
								if (request.getAttribute("errorePassword") != null) {
									%><input type="password" class="form-control is-invalid" id="password" value="<%=request.getAttribute("password")%>" name="password"><% 
								} else if (request.getAttribute("password") != null) {
									%><input type="password" class="form-control" id="password" value="<%=request.getAttribute("password")%>" name="password"><% 
								} else {
									%><input type="password" class="form-control" id="password" name="password"><%
								}
							%>
		                    <div class="invalid-feedback">La password deve contenere almeno 8 caratteri&comma; una lettera minuscola ed una maiuscola&comma; un numero ed un carattere speciale tra &commat; &excl; &num; &dollar; &percnt; &dash; &sol; &equals; &Hat; &lowbar; &lbrace; &rbrace; &tilde; &plus;</div> 
			        	<input type="checkbox" onclick="showPassword()"> Show Password
			        </div>
					<input type="submit" class="btn btn-dark btn-block" value="CONTINUA">
				</fieldset>
		    </form> 
	</div>
</div>
</body>
</html>