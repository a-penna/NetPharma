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
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="pt-5">
<%@ include file="/headerGestori.jsp"%>
<div class="container-ordini py-4 px-4">
<table class="table table-dark shadow-lg p-3 mb-5 bg-dark">
  <thead class="thead">
    <tr>
      <th scope="col">N&ordm; Ordine</th>
      <th scope="col">Lista prodotti &amp; Quantit&agrave;</th>
      <th scope="col">Dati Spedizione</th>
      <th scope="col">Data ordine</th>
      <th scope="col">Data arrivo</th>
    </tr>
  </thead>
  <tbody>
        <%
Iterator<?> it = ordini.iterator();
while(it.hasNext()) {
	Ordine ordine = (Ordine)it.next();
%>
		<tr>
	      <th scope="row"><%=ordine.getId()%></th>
	      <td>
	      <%Iterator<?> it2 = ordine.getRigheOrdine().iterator();
	      while(it2.hasNext()) {
	    	  RigaOrdine ro = (RigaOrdine)it2.next();
	    	  %>&lsqb;Codice prodotto&colon; <%=ro.getProdotto()%>&semi;
	    	  Quantit&agrave;&colon; <%=ro.getQuantity()%> 
	    	  Prezzo al pezzo&colon; <%=ro.getPrezzoAlPezzo()%> &euro;&rsqb; 
	   <% } %>
	      
	      </td>
	      <td>Nome ricevente&colon; <%=ordine.getNomeRicevente()%>&semi;
	     	  Cognome ricevente&colon; <%=ordine.getCognomeRicevente()%>&semi;
	      	  Email ricevente&colon; <%=ordine.getEmail()%>&semi;
	      	  Cellulare&colon; <%=ordine.getCellulare()%>&semi;
			  Via&colon; <%=ordine.getVia()%>&semi;
			  N&ordm; Civico&colon; <%=ordine.getNcivico()%>&semi;
			  Citt&agrave;&colon; <%=ordine.getCitta()%>&semi;
			  Paese&colon; <%=ordine.getPaese()%>&semi;
			  Provincia&colon; <%=ordine.getProvincia()%>&semi;
			  Cap&colon; <%=ordine.getCAP()%>&semi;
			  Account&colon; <%=ordine.getCliente()%>&semi; 
	      <td><%=ordine.getData_ordine()%></td>
	      <%if (ordine.getData_arrivo()!=null) {%>
				<td><%=ordine.getData_arrivo()%></td>
			<% } else {%>
				<td>Data non disponibile</td>
			<% }%>
	    </tr>
<%
}
%>
  </tbody>
</table>
</div>
</body>
</html>