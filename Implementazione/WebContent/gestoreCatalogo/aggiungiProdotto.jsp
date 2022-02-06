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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/script.js"></script>
   
</head>            

<body class="bg-light">
	<%@ include file="/header.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<div class="col-md-10">
				<p>Inserisci i dati nel seguente form per aggiungere un nuovo prodotto&colon; <p>
				<form action="<%=response.encodeURL(request.getContextPath() + "/AggiungiProdotto")%>" method="post" enctype="multipart/form-data"> 
				        <fieldset>
				        	<legend>Informazioni sul prodotto&colon; </legend>
							<div class="form-group">
					        	<label for="id">Codice Prodotto&colon;</label>
								<input id="id" type="text" class="form-control" name="id" placeholder="Codice">
				    		</div>
							<div class="form-group">
					        	<label for="nome">Nome&colon;</label>
								 <input id="nome" type="text" class="form-control" name="nome" placeholder="Nome prodotto">
				    		</div>
				    		
							<div class="form-group">
					        	<label for="marchio">Marchio&colon;</label>
								<input id="marchio" type="text" class="form-control" name="marchio" placeholder="Nome marchio">
				    		</div>
				    		
							<div class="form-group">
					        	<label for="produttore">Produttore&colon;</label>
								<input id="produttore" type="text" class="form-control" name="produttore" placeholder="Nome produttore">
				    		</div>
				    		
				    		<div class="form-group">
					        	<label for="formato">Formato&colon;</label>
								<input id="formato" type="text" class="form-control" name="formato" placeholder="Formato">
				    		</div>
				    	
				    		<div class="form-group">
					        	<label for="descrizione">Descrizione&colon;</label>
					        	<textarea id="descrizione" class="form-control" name="descrizione" rows="10" cols="48" placeholder="Inserisci qui la descrizione del prodotto"></textarea>
							</div>
							
							<div class="form-group">
					        	<label for="disponibilita">disponibilita&colon;</label>
								<input id="disponibilita" type="text" class="form-control" name="disponibilita" placeholder="Disponibilità">
				    		</div>
				    		
				    		<div class="form-group">
					        	<label for="prezzo">Prezzo&colon;</label>
								<input id="prezzo" type="text" class="form-control" name="prezzo" placeholder="Prezzo">
				    		</div>
				    		
				    		<div class="form-group">
					        	<label for="cagtegoria">Categoria&colon;</label>
								<input id="categoria" type="text" class="form-control" name="categoria" placeholder="Categoria">
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