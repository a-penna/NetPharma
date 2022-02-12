<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop online, shop, operazioni gestore catalogo, gestore catalogo, rimuovi, prodotto, rimuovi prodotto">
	<meta name="description" content="Rimuovi Prodotto">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>NetPharma &dash; Rimuovi Prodotto</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		$(document).ready(function() {
			$(".my-tablist-element").removeClass("active");
			$("#rimuoviProdotto").addClass("active");
		});
	</script>
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<form action="<%=response.encodeURL(request.getContextPath() + "/RimuoviProdotto")%>" method="post"> 
					    <fieldset>
							<div class="form-group">
								
					        	<label for="id">Codice Prodotto&colon;</label>
								<input id="id" type="text" class="form-control" name="id" placeholder="Codice prodotto">
				   			</div>
					        <button type="submit" class="btn btn-primary">Elimina</button>
					   </fieldset>
				</form> 
			</div>
		</div>
	</div>
</body>
</html> 