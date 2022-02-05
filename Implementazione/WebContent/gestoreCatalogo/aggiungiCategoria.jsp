<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, shop online, operazioni gestore catalogo, gestore catalogo, aggiungi, aggiungi categoria, categoria">
	<meta name="description" content="Aggiungi Categoria">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>Netpharma &dash; Aggiungi Categoria</title>
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
<!--
<body class="bg-light">
	<%@ include file="/header.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<div class="col-md-10">
				<p>Inserisci i dati nel seguente form per aggiungere una nuova categoria&colon; <p>
				<form action="<%=response.encodeURL(request.getContextPath() + "/AggiungiCategoria")%>" method="post"> 
				        <fieldset>
				        	<legend>Informazioni sulla categoria&colon; </legend>
							<div class="form-group">
					        	<label for="nome">Nome&colon;</label>
								 <input id="nome" type="text" class="form-control" value="<%=request.getAttribute("nome")%>" name="nome" placeholder="Nome categoria">
				    		</div>
				        </fieldset>
				        
				        lista di tutti i prodotti svincolati e pulsante più da freevote
				       
				        <button type="submit" class="btn btn-primary">Aggiungi</button>
				</form>
		</div>
	</div>
	</div> 

</body>
-->
</html> 
