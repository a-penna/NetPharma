<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">
	<title>NetPharma &dash; Errore</title>
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
	<div class="container py-5">
		<div class="alert alert-danger" role="alert">
			<h4 class="alert-heading">ERRORE&excl;</h4>
			<% String message = (String)request.getAttribute("message");
			if(message != null) { %>
				<p><%=message%></p>
			<%} else { %>
				<p>Errore generico - Qualcosa è andato storto&excl;</p>
				<p>Riprova ad effettuare l'operazione richiesta </p>
			<%} %>
			<hr>
		</div>
	</div>
</body>
</html>