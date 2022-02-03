<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, model.* , bean.* , java.sql.Date"%>
    
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
<title>Lista Ordini</title>
</head>
<body>
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