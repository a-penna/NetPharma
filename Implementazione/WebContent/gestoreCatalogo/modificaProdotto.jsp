<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<% 
	Collection<?> prodotti = (Collection<?>) request.getAttribute("listaProdotti");
    if (prodotti == null) {
        response.sendRedirect(response.encodeURL(request.getContextPath() + "/ModificaProdotto"));
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, shop online, operazioni gestore catalogo, gestore catalogo, modifica, modifica prodotto, prodotto">
	<meta name="description" content="Modifica Prodotto">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>NetPharma &dash; Modifica Prodotto</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/script.js"></script>
    <script>
	    function validate(obj) {	
	    	var valid = true;
	    	
	        var p = document.getElementsByName("prodotto")[0];
	        if(p.value == "Seleziona Prodotto") {
	            valid = false;
	            p.classList.add("is-invalid");
	            p.focus();
	        } else {
	        	p.classList.remove("is-invalid");
	        }
	       
	        if(valid) obj.submit();
	    }
	    
		$(document).ready(function() {
			$(".my-tablist-element").removeClass("bg-dark text-white");
			$("#modificaProdotto").addClass("bg-dark text-white");
			
			$('#prodotto').change(function() {
				var id = $(this).val();
				$.ajax({
					url:"ProdottoAJAX?id=" + id,
					method:"GET",
				})
				.done(function(msg) {
					var prodotti = msg.prodotto;
					$("#prodotto").removeClass("is-invalid");
					$("#nome").val(prodotti[0]);
					$("#nome").removeClass("is-invalid");
					$("#marchio").val(prodotti[1]);
					$("#marchio").removeClass("is-invalid");
					$("#produttore").val(prodotti[2]);
					$("#produttore").removeClass("is-invalid");
					$("#formato").val(prodotti[3]);
					$("#formato").removeClass("is-invalid");
					$("#descrizione").val(prodotti[4]);
					$("#descrizione").removeClass("is-invalid");
					$("#disponibilita").val(prodotti[5]);
					$("#disponibilita").removeClass("is-invalid");
					$("#prezzo").val(prodotti[6]);
					$("#prezzo").removeClass("is-invalid");
					})
				.fail(function(xhr, textStatus) {
					alert("Errore");
				});	
					
				});
			});
	</script> 
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<p>Inserisci i dati nel seguente form per modificare un nuovo prodotto&colon; <p>
				<form action="<%=response.encodeURL(request.getContextPath() + "/ModificaProdotto")%>" method="post" enctype="multipart/form-data" onsubmit="event.preventDefault(); validate(this)"> 
				        <fieldset>
				        	<legend>Informazioni sul prodotto&colon; </legend>
				        	<div class="form-group">
						        	<select class="custom-select" id="prodotto" name="prodotto">
						        		<option disabled selected>Seleziona Prodotto</option>
							            <%
							            String oldValue = (String)request.getAttribute("codice");
										int old = -1;
										try {
											old = Integer.parseInt(oldValue);
										} catch(NumberFormatException e) {
										}
							            Iterator<?> it = prodotti.iterator();
							            while(it.hasNext()) {
											Prodotto bean = (Prodotto)it.next();
											if (old == bean.getId()) {%>
							            		<option selected value="<%=bean.getId()%>"><%=bean.getNome()%></option>
							            <%	} else { %>
							            		<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>
							            <%  }
										} 
							            %>
							     </select> 
							     <div class="invalid-feedback">Seleziona il prodotto da modificare&excl;</div>
				   			</div>
				   			<div class="form-group">
					            <label for="nome">Nome&ast;&colon;</label>
					            	<%
										if ((request.getAttribute("erroreNome") != null)) {
											%><input type="text" class="form-control is-invalid" id="nome" value="<%=request.getAttribute("nome")%>" name="nome"><% 
										} else if (request.getAttribute("nome") != null) {
											%><input type="text" class="form-control" id="nome" value="<%=request.getAttribute("nome")%>" name="nome"><% 
										} else {
											%><input type="text" class="form-control" id="nome" name="nome" placeholder="Nome prodotto"><% 
										}
					            	%>
					            		
										<div class="invalid-feedback">Inserisci un nome &lpar;max&period; 100 caratteri&rpar;</div>
					        </div>
				   			<div class="form-group">
					            <label for="marchio">Marchio&ast;&colon;</label>
					            	<%
										if ((request.getAttribute("erroreMarchio") != null)) {
											%><input type="text" class="form-control is-invalid" id="marchio" value="<%=request.getAttribute("marchio")%>" name="marchio"><% 
										} else if (request.getAttribute("marchio") != null) {
											%><input type="text" class="form-control" id="marchio" value="<%=request.getAttribute("marchio")%>" name="marchio"><% 
										} else {
											%><input type="text" class="form-control" id="marchio" name="marchio" placeholder="Nome marchio"><% 
										}
					            		%>
										<div class="invalid-feedback">Inserisci un marchio &lpar;max&period; 50 caratteri&rpar;</div>
					        </div>
				    		<div class="form-group">
					            <label for="produttore">Produttore&colon;</label>
					            	<%
										if (request.getAttribute("produttore") != null) {
											%><input type="text" class="form-control" id="produttore" value="<%=request.getAttribute("produttore")%>" name="produttore"><% 
										} else {
											%><input type="text" class="form-control" id="produttore" name="produttore" placeholder="Nome produttore"><% 
										}
							%>					            	
					        </div>	
				    		<div class="form-group">
					            <label for="formato">Formato&colon;</label>
					            	<%
										if (request.getAttribute("formato") != null) {
											%><input type="text" class="form-control" id="formato" value="<%=request.getAttribute("formato")%>" name="formato"><% 
										} else {
											%><input type="text" class="form-control" id="formato" name="formato" placeholder="Formato"><% 
										}
							%>					            	
					        </div>
				    		<div class="form-group">
					            <label for="descrizione">Descrizione&ast;&colon;</label>
					            	<%
										if ((request.getAttribute("erroreDescrizione") != null)) {
											%><textarea id="descrizione" class="form-control is-invalid" name="descrizione" rows="10" cols="48"><%=request.getAttribute("descrizione")%></textarea><% 
										} else if (request.getAttribute("descrizione") != null) {
											%><textarea id="descrizione" class="form-control" name="descrizione" rows="10" cols="48"><%=request.getAttribute("descrizione")%></textarea><%
										} else {
											%><textarea id="descrizione" class="form-control" name="descrizione" rows="10" cols="48"></textarea><% 
										}
					            		%>
										<div class="invalid-feedback">Inserisci una descrizione&excl;</div>
					        </div>
					        <div class="form-group">
					            <label for="disponibilita">Disponibilit&agrave;&ast;&colon;</label>
					            	<%
					            		if ((request.getAttribute("erroreDisponibilita") != null)) {
										    %><input type="text" class="form-control is-invalid" id="disponibilita" value="<%=request.getAttribute("disponibilita")%>" name="disponibilita"><% 
										} else if (request.getAttribute("disponibilita") != null) {
											%><input type="text" class="form-control" id="disponibilita" value="<%=request.getAttribute("disponibilita")%>" name="disponibilita"><% 
										} else {
											%><input type="text" class="form-control" id="disponibilita" name="disponibilita" placeholder="Disponibilit&agrave;"><% 
										} %>
					            	<div class="invalid-feedback">Disponibilit&agrave; deve essere un intero positivo&excl;</div>
					        </div>	
				    		<div class="form-group">
					            <label for="prezzo">Prezzo&colon;&ast;</label>
					            	<%
					            		if ((request.getAttribute("errorePrezzo") != null)) {
										    %><input type="text" class="form-control is-invalid" id="prezzo" value="<%=request.getAttribute("prezzo")%>" name="prezzo"><% 
										} else if (request.getAttribute("prezzo") != null) {
											%><input type="text" class="form-control" id="prezzo" value="<%=request.getAttribute("prezzo")%>" name="prezzo"><% 
										} else {
											%><input type="text" class="form-control" id="prezzo" name="prezzo" placeholder="0.00"><% 
										}
							%>		
								<div class="invalid-feedback">Il prezzo inserito non &egrave; valido&excl;</div>			            	
					        </div>
				    		
							<div class="form-group">
					        	<label for="foto">Modifica Foto&lpar;Max&period; size&equals;10MB&rpar;&colon;</label>
					       	    <input id="foto" type="file" accept="image/*" name="foto"> 
					        	<br>
					        </div>
				        </fieldset>
				       
				        <button type="submit" class="btn btn-dark">Modifica</button>
				</form>
		</div>
	</div>
	</div> 
<%@ include file="/footer.jsp"%> 
</body>
</html> 