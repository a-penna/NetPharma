<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, model.* , bean.*"%>
    
    <% 
   
    
    Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");

    if (ordini == null) {
        response.sendRedirect(response.encodeRedirectURL("/ListaOrdini"));
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


<%
Iterator<?> it = ordini.iterator();
while(it.hasNext()) {
	Ordine ordine = (Ordine)it.next();
%>

<p><%=ordine.getCliente()%> </p>
<p><%=ordine.getPrezzo()%> </p>
<%
}
%>
</body>
</html>