<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.* , main.bean.* , java.sql.Date"%>
    
    <% 
   
    
    Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");

    if (ordini == null) {
        response.sendRedirect(response.encodeRedirectURL("/NetPharma/ListaOrdini"));
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
	<title>NetPharma &dash; Lista Ordini</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
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


<table>
<tbody>
<tr>
<th scope="row">Cliente</th>
<th scope="row">Prezzo</th>
<th scope="row">Data_Ordine</th>
<th scope="row">Data_Arrivo</th>
</tr>

<%
Iterator<?> it = ordini.iterator();
while(it.hasNext()) {
	
	Ordine ordine = (Ordine)it.next();
	
		
	
%>
<tr>
<td><%=ordine.getCliente()%> </td>
<td><%=ordine.getPrezzo()%> </td>
<td><%=ordine.getData_ordine() %></td>
<td><%=ordine.getStato()%></td>
<%

if (ordine.getData_arrivo()!=null) {
	
	
	
	
	%>
<td><%=ordine.getData_arrivo()%></td>
<% }
else {
%>

<td>Data non disponibile</td>
<% } %>
</tr>

<%
}

%>
</tbody>
</table>
</body>
</html>