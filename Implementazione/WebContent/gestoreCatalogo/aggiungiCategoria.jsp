<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<% 
	Collection<?> prodotti = (Collection<?>) request.getAttribute("listaProdotti");
    if (prodotti == null) {
        response.sendRedirect(response.encodeURL(request.getContextPath() + "/AggiungiCategoria"));
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, shop online, operazioni gestore catalogo, gestore catalogo, aggiungi, aggiungi categoria, categoria">
	<meta name="description" content="Aggiungi Categoria">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>Netpharma &dash; Aggiungi Categoria</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/script.js"></script>   
	<script>
		function validate(obj) {	
	    	var valid = true;
	    	
	        var n = document.getElementsByName("nome")[0];
	        if(n.value.trim() == "") {
	            valid = false;
	            n.classList.add("is-invalid");
	            n.focus();
	        } else {
	        	n.classList.remove("is-invalid");
	        }
	       
	        if(valid) obj.submit();
	    }
	
		$(document).ready(function() {
			$(".my-tablist-element").removeClass("bg-dark text-white");
			$("#creaCategoria").addClass("bg-dark text-white");
		});
	</script>
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	

	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<p>Inserisci i dati nel seguente form per aggiungere una nuova categoria&colon;<p>
				<form action="<%=response.encodeURL(request.getContextPath() + "/AggiungiCategoria")%>" method="post" onsubmit="event.preventDefault(); validate(this)"> 
				        <fieldset>
				        	<legend>Informazioni sulla categoria&colon; </legend>
							<div class="form-group">
					        	<label for="nome">Nome&colon;</label>
					        	<%
										if ((request.getAttribute("nomeEsistente") != null)) {
											%><input type="text" class="form-control is-invalid" id="nome" value="<%=request.getAttribute("nome")%>" name="nome"><% 
										} else {
											%><input id="nome" type="text" class="form-control" name="nome" placeholder="Nome categoria"><% 
										}
					            
					            		if((request.getAttribute("nomeEsistente") != null))	{ %>
								 			<div class="invalid-feedback">Attenzione&excl; Esiste gi&agrave; una categoria di prodotti con il nome specificato</div> 
								 		<%}else{ %>
								 			<div class="invalid-feedback">Inserisci un nome per la categoria&lpar;max&period; 50 caratteri&rpar;</div> 
								 		<%} %>
										
				    		</div>
					    		<select class="custom-select" name="idProdotti" multiple>
							        	<option disabled selected>Seleziona prodotti da aggiungere&colon;</option>
								        <%
								     	    Iterator<?> it = prodotti.iterator();
								            while(it.hasNext()) {
												Prodotto bean = (Prodotto)it.next();
												 %>
								            		<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>	            		
								            <%	  
											} 
								        %>
								</select>
				        </fieldset>
				        
				        <button type="submit" class="btn btn-dark">Aggiungi</button>
				</form>
		</div>
	</div>
	</div>
<%@ include file="footer.jsp"%> 
</body>
</html> 
