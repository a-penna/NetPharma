<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<% 
    Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti");

    if (prodotti == null) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error/generic.jsp"));
        return;
    }
%>    
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, farmacia, shop online, prodotti, lista prodotti">
	<meta name="description" content="E-commerce, lista prodotti">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
    <title>NetPharma &dash; Prodotti</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/commonSources.jsp"%>
    <script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 						
</head>            

<body class="bg-light">
     <%@ include file="/header.jsp"%>
    <div class="container py-5"> 
		<div class="row">
			<div class="col-md">
			    <h2 class="display-5 text-center pt-5">Lista prodotti</h2>
			</div>
		</div>
				<br>
				<table class="table text-center">
						  <tbody> 
					  <%
					        Iterator<?> it = prodotti.iterator();
					        while(it.hasNext()) {
					            Prodotto prodotto = (Prodotto)it.next(); 
					  %>		
						  <tr>
								<td><img src="FotoControl?id=<%=prodotto.getId()%>" class ="rounded float-left" height="50" width="50" onerror="this.src='./imgs/noPhoto.png'" onerror="this.src='./imgs/noPhoto.png' alt="foto"></td>
									<td><div class="row"><a href="<%=response.encodeURL(request.getContextPath() + "/Prodotto?id=" + prodotto.getId())%>"><%=prodotto.getNome()%></a></div>
									<%String des = prodotto.getDescrizione();
									if (des.length() >= 80) {
										char[] smallDes = new char[80];
						         		des.getChars(0, 79, smallDes, 0);%>
										<div class="row"><%=smallDes%>...</div>
										<%
									} else {%>
										<div class="row"><%=des%></div>
								<%}%>
								</td>
								<td><p class="h6 text-right font-weight-normal"><%=prodotto.getPrezzo()%> &euro;</p></td>
								<td><a class="btn btn-dark"href="<%=response.encodeURL(request.getContextPath() + "/AggiungiProdottoCarrello?prodotto=" + prodotto.getId())%>&quantity=1">Aggiungi al carrello</a></td>
							  
							  <% } %>
						  </tr>
					

						</tbody>
						</table>
				</div>
    
<%@ include file="footer.jsp"%>
</body>
</html>