<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">
	<title>NetPharma &dash; Unauthorized</title>
	<%@ include file="/commonSources.jsp"%>
	<script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 
</head>
<body>
	<%if(request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
										|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null)) { %>
		<%@ include file="/headerGestori.jsp"%>
	<%} else {%>
		<%@ include file="/header.jsp"%>
	<%} %>
<body class="bg-warning">
	<div class="container py-5">
		<div class="alert alert-warning" role="alert">
			<h4 class="alert-heading">ERRORE&colon;</h4>
			<p>Non sei autorizzato ad accedere a questa risorsa&excl;</p>
			<hr>
		</div>
	</div>
	<%@ include file="/footer.jsp"%> 
</body>
</html>