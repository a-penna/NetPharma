<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%	
// boolean isGestoreOrdini = request.getSession(false) != null && request.getSession(false).getAttribute("gestoreOrdini")!= null;
// if (isGestoreOrdini != true) {
	// response.sendRedirect(""); //Aggiungere redirect a login
	
// }
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
 <!-- Inserire controllo di accesso -->
<a href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Lista Ordini</a>



</body>
</html>