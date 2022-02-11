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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">						
	<%@ include file="/commonSources.jsp"%>
</head>             

<body>
	<%@ include file="header.jsp"%>
	<div class="container py-5">
		<div class="alert alert-success" role="alert">
		  <h4 class="alert-heading">SUCCESSO&excl;</h4>
		  <p>L&apos;operazione scelta &egrave; stata completata correttamente</p>
		  <hr>
		</div>
	</div>
</body>
</html> 