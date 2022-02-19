<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>   
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online, home, homepage, home-page">
	<meta name="description" content="HomePage">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<title>HomePage</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
	<script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 
	
</head>
<body>
<%@ include file="header.jsp"%>

	<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
	  <div class="carousel-inner">
	    <div class="carousel-item active pt-3">
	      <img src="./imgs/illustr_1.jpeg" class="d-block w-100" alt="...">
	    </div>
	    <div class="carousel-item">
	      <img src="./imgs/illustra-2.png" class="d-block w-100" alt="...">
	    </div>
	    <div class="carousel-item">
	      <img src="./imgs/illustr-1.png" class="d-block w-100" alt="...">
	    </div>
	  </div>
	  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="sr-only">Precedente</span>
	  </a>
	  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="sr-only">Successivo</span>
	  </a>
	</div>

<%@ include file="footer.jsp"%>
</body>
</html>