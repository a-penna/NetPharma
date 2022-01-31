<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%	
boolean loggedIn = request.getSession(false) != null && request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null;

if(loggedIn) {

%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="HomePage - Gestore Ordini">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
<title>HOME-PAGE GESTORE ORDINI</title>
</head>

<body>

<a href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Lista completa ordini</a>
<a href="<%=request.getContextPath()%>/gestoreOrdini/SpedisciOrdini.jsp">Visualizza ordini da spedire</a>


</body>
<% }
else {
	response.sendRedirect("/NetPharma/login.jsp");
}
%>
</html>