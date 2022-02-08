<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="HomePage - Gestore Ordini">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<%@ include file="/commonSources.jsp"%>
	<title>HOME-PAGE GESTORE ORDINI</title>
</head>

<body class="pt-5">
	<%@ include file="/headerGestori.jsp"%>
	<a href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Lista completa ordini</a>
	<a href="<%=request.getContextPath()%>/gestoreOrdini/SpedisciOrdini.jsp">Visualizza ordini da spedire</a>


</body>
</html>