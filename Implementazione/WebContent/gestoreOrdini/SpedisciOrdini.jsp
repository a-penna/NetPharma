<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.* , main.bean.*"%>
    
    <% 
   
    
    Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");
    if (ordini == null) {
        response.sendRedirect(response.encodeRedirectURL("/NetPharma/OrdiniDaSpedire"));
        return;
    }
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="Lista Ordini - Gestore Ordini">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<title>NetPharma &dash; Spedisci Ordini</title>
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

<form method="get" action="<%=response.encodeURL("/NetPharma/GestisciOrdini")%>">
<%
Iterator<?> it = ordini.iterator();
while(it.hasNext()) {
	
	Ordine ordine = (Ordine)it.next();
	if(ordine.getStato().equals("No")) {
		
	
%>
<h3><%=ordine.getCliente()%> </h3>
<h3><%=ordine.getPrezzo()%> </h3>
<h3><%=ordine.getData_ordine() %></h3>
<input type="text" placeholder="Giorni" class="form-control" id="giorni" value="<%=request.getAttribute("giorni")%>" name="giorni">
<input type="submit" class="custom-control-input mb-3" id="scelta" name="scelta" value=<%=ordine.getId()%>>

<%
}
}
%>
</form>
</body>
</html>