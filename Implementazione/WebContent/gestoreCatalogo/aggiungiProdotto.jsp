<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, shop online, operazioni gestore catalogo, gestore catalogo, aggiungi, aggiungi prodotto, prodotto">
	<meta name="description" content="Aggiungi Prodotto">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>Netpharma &dash; Aggiungi Prodotto</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/script.js"></script>
    <script>
	    
    </script>
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<p>Inserisci i dati nel seguente form per aggiungere un nuovo prodotto&colon; <p>
				<form action="<%=response.encodeURL(request.getContextPath() + "/AggiungiProdotto")%>" method="post" enctype="multipart/form-data"> 
				        <fieldset>
				        	<legend>Informazioni sul prodotto&colon; </legend>
				        	<div class="form-group">
					            <label for="id">Codice Prodotto&ast;&colon;</label>
					            	<%
										if ((request.getAttribute("erroreCodice") != null)) {
											%><input type="text" class="form-control is-invalid" id="id" value="<%=request.getAttribute("codice")%>" name="id"><% 
										} else if (request.getAttribute("codice") != null) {
											%><input type="text" class="form-control" id="id" value="<%=request.getAttribute("codice")%>" name="id"><% 
										} else {
											%><input type="text" class="form-control" id="id" name="id" placeholder="Codice"><% 
										}
					            	%>
					            		
										<div class="invalid-feedback">Questo codice non sembra essere valido&excl;</div> 
										 
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
					            		
										<div class="invalid-feedback">Inserisci un nome&excl;</div>
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
										<div class="invalid-feedback">Inserisci un marchio&excl;</div>
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
					            <label for="categoria">Categoria&colon;</label>
					            	<%
										if ((request.getAttribute("erroreCategoria") != null)) {
											%><input type="text" class="form-control is-invalid" id="categoria" value="<%=request.getAttribute("categoria")%>" name="categoria"><% 
										} else if (request.getAttribute("categoria") != null) {
											%><input type="text" class="form-control" id="categoria" value="<%=request.getAttribute("categoria")%>" name="categoria"><% 
										} else {
											%><input type="text" class="form-control" id="categoria" name="categoria" placeholder="Categoria"><% 
										} 
					            	%>
										<div class="invalid-feedback">Inserire il nome di una categoria esistente&excl;</div>
										 
					        </div>
							<div class="form-group">
					        	<label for="foto">Carica Foto&lpar;Max&period; size&equals;10MB&rpar;&colon;</label>
					       	    <input id="foto" type="file" accept="image/*" name="foto"> 
					        	<br>
					        </div>
				        </fieldset>
				       
				        <button type="submit" class="btn btn-primary">Aggiungi</button>
				</form>
		</div>
	</div>
	</div> 

</body>
</html> 