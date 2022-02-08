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
<div class="container-ordini py-4 px-4">
<table class="table table-dark shadow-lg p-3 mb-5 bg-dark">
  <thead class="thead">
    <tr>
      <th scope="col">N&ordm; Ordini</th>
      <th scope="col">Lista prodotti &amp; Quantit&agrave;</th>
      <th scope="col">Dati Spedizione</th>
      <th scope="col">Data ordine</th>
      <th scope="col"></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>Mario</td>
      <td>Verdi</td>
      <td>Data ordine 1</td>
      <td><button type="button" class="btn btn-outline-light">Invia Conferma Spedizione</button></td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Francesco</td>
      <td>Bianchi</td>
      <td>Data ordine 2</td>
      <td><button type="button" class="btn btn-outline-light">Invia Conferma Spedizione</button></td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td>Alessandro</td>
      <td>Rossi</td>
      <td>Data ordine 3</td>
      <td><button type="button" class="btn btn-outline-light">Invia Conferma Spedizione</button></td>
    </tr>
  </tbody>
</table>
</div>

<a href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Lista completa ordini</a>
<a href="<%=request.getContextPath()%>/gestoreOrdini/SpedisciOrdini.jsp">Visualizza ordini da spedire</a>


</body>
</html>