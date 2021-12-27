<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, bean.*"%>

<%
 	UtenteRegistrato user = (UtenteRegistrato)request.getAttribute("user");
  	
 	if(user == null) {
 		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/TestControl"));
 		return;
 	}
%>    
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="test">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<title>NetPharma &dash; </title>
	
</head>
<body>
	<h1><%=user.getNome()%></h1>
</body>
</html>