<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="HomePage - Gestore Ordini">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">
	<title>NetPharma &dash; Ordini</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="pt-5">
<%@ include file="/headerGestori.jsp"%>

<a href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Lista completa ordini</a>
<a href="<%=request.getContextPath()%>/gestoreOrdini/SpedisciOrdini.jsp">Visualizza ordini da spedire</a>


</body>
</html>