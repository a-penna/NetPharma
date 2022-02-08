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
<title>NetPharma - Gestione catalogo</title>
</head>

<body class="pt-5">
<%@ include file="/headerGestori.jsp"%>
<a href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiProdotto.jsp">Inserisci Prodotto</a>
<a href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviProdotto.jsp">Rimuovi Prodotto</a>
<a href="<%=request.getContextPath()%>/gestoreCatalogo/modificaProdotto.jsp">Modifica Prodotto</a>
<a href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiCategoria.jsp">Crea Categoria</a>
<a href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviCategoria.jsp">Rimuovi Categoria</a>




</body>
</html>