<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<%
	Prodotto prodotto = (Prodotto)request.getAttribute("prodotto");
	if(prodotto == null) {
 		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Prodotto"));
 		return;
 	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, e-commerce, ecommerce,  piattaforma e-commerce, shop, farmacia, info prodotto, prodotto, <%=prodotto.getNome()%>">
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
<%@ include file="header.jsp"%>
	
	<div class="row">
	<div class="col-md-8 my-4 mx-4">
  			<div class="container-product my-4">  
					<img src="PhotoControl?type=prodotto&id=<%=prodotto.getNome()%>" class ="rounded float-left" height="225" width="225" onerror="this.src='./imgs/nologo.png'" alt="foto">
				   	<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getNome()%></p>
					<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getMarchio()%></p>
					<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getCategoria()%></p>
					<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getDescrizione()%> prodotto</p>
					<p class="h5 text-justify font-weight-normal py-4"><%=prodotto.getFormato()%></p>
		    </div>
	</div>
					<div class="col-md-3 my-5 mx-4">
					<form method="get" action="<%=response.encodeURL(request.getContextPath() + "/AggiungiProdottoCarrello")%>">
						<div class="card border-dark mb-3" style="max-width: 16rem;">
							  	<div class="card-body text-dark pl-2 pr-0">
							  		<div class="row px-2">
										<div class="col-8">
										<p>Prezzo di listino </p>
										</div>
										<div class="col-4">
											<p class="h6 text-right font-weight-normal"><%=prodotto.getPrezzo()%> &euro;</p>
										</div>
									</div>
									<br>
									<br>
									<div class="row px-2">
										<div class="col-6">
										<p>Quantit&agrave; </p>
										</div>
										<div class="col-6">
											  	<span class="input-number input-number-quantity text-right">
											  		<input type="hidden" name="prodotto" value="<%=prodotto.getId()%>">
											   		 <label>
											    		<input class="container-quantity col-xl-8" type="number" id="quantity" name="quantity" value="1" min="1" step="1">
											   		 </label>
											 	</span>
										</div>
									</div>								    
								 </div>
								 <input type="submit" class="btn btn-dark" value="AGGIUNGI AL CARRELLO"/>
						</div>						
					</form>
					</div>
	</div>

</body>
</html>