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

<body>
<%@ include file="/headerGestori.jsp"%>

<%@ include file="footer.jsp"%>
</body>
</html>