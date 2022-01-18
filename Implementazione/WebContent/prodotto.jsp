<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, bean.*"%>

<%
	Prodotto prodotto = (Prodotto)request.getAttribute("prodotto");
	if(prodotto == null) {
 		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ProdottoControl"));
 		return;
 	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, e-commerce, ecommerce,  piattaforma e-commerce, shop, farmacia, info prodotto, prodotto<%=prodotto.getNome()%>">
	<meta name="description" content="Pagina Prodotto">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<title>NetPharma &dash; <%=prodotto.getNome()%> </title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">						
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body class="bg-light">
	<div class="container py-5">  
		<h1 align="center"><%=prodotto.getNome()%></h1>
		
		<br>
		<img src="PhotoControl?type=partito&id=<%=prodotto.getNome()%>" class ="mx-auto d-block rounded" height="225" width="225" onerror="this.src='./imgs/nologo.png'" alt="foto">
	    <br>
		<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getMarchio()%></p>
		<br>
		<br>
		<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getFormato()%></p>
		<br>
		<br>
		<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getCategoria()%></p>
		<br>
		<br>
		<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getDescrizione()%></p>
		<br>
	    <br>
		<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getPrezzo()%></p>
		<br>
		<hr>
		<br>
	</div>
	
	<form method="get" action="<%=response.encodeURL(request.getContextPath() + "/AggiungiProdottoCarrello")%>"> 
		<label for="quantity">Quantit&agrave;&colon;</label>
			 <input type="hidden" name="prodotto"  value="<%=prodotto.getId()%>">
			 <input type="number" id="quantity" value="1" min="1" name="quantity">
		<input type="submit" class="btn btn-dark" value="AGGIUNGI AL CARRELLO"/>
	</form> 

</body>
</html>