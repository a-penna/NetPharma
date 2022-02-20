<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
boolean isAuthorized = request.getSession(false) != null && (request.getSession(false).getAttribute("gestoreOrdiniRoles")!= null 
														|| request.getSession(false).getAttribute("gestoreCatalogoRoles")!= null);

if (!isAuthorized) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Logout"));
 	return;
}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%@ include file="/commonSources.jsp"%>
	<title>HomePage</title>
</head>

<body class="bg-home-gestori">
<%@ include file="/headerGestori.jsp"%>
<div class="fixed-bottom">
</div>
</body>
</html>