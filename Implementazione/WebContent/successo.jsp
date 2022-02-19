<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, farmacia, shop online, prodotti, ricerca, ricerca prodotti">
	<meta name="description" content="E-commerce, lista prodotti">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
    <title>NetPharma &dash; Successo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 
</head>             

<body>
	<%if(request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
										|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null)) { %>
		<%@ include file="headerGestori.jsp"%>
	<%} else {%>
		<%@ include file="header.jsp"%>
	<%} %>
	<div class="container py-5">
		<div class="alert alert-success" role="alert">
		  <h4 class="alert-heading">SUCCESSO&excl;</h4>
		  <p>L&apos;operazione scelta &egrave; stata completata correttamente</p>
		  <hr>
		</div>
	</div>
	<%@ include file="footer.jsp"%> 
</body>
</html> 