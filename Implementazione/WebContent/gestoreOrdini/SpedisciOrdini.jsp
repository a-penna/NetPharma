<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.* , main.bean.*"%>
    
    <% 
   
    
    Collection<?> ordini = (Collection<?>) request.getAttribute("ordini");
    if (ordini == null) {
        response.sendRedirect(response.encodeRedirectURL("/NetPharma/OrdiniDaSpedire"));
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
	<title>NetPharma &dash; Spedisci Ordini</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	    <script>
	    $(document).ready(function() {
			$(".my-list-element").removeClass("bg-dark text-white");
			$("#ordini-da-spedire").addClass("bg-dark text-white");
		});
    </script>
</head>

<body class="pt-5">
<%@ include file="/headerGestori.jsp"%>
<div class="container-ordini py-4 px-4">
		<div class="row">
			<%@ include file="gestoreOrdiniMenu.jsp"%>
			<div class="col-md-10">
				<table class="table shadow-lg p-3 mb-5 bg-dark">
				  <thead class="thead-dark">
				    <tr class="table">
				      <th scope="col">N&ordm; Ordine</th>
				      <th scope="col">Lista prodotti &amp; Quantit&agrave;</th>
				      <th scope="col">Dati Spedizione</th>
				      <th scope="col">Data ordine</th>
				      <th scope="col">Giorni previsti per l&apos;arrivo</th>
				      <th scope="col">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
				    </tr>
				  </thead>
				  <tbody class="table-light text-dark">
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
					      <td>
						      <form class="form-inline" method="get" action="<%=response.encodeURL("/NetPharma/GestisciOrdini")%>">
						      	<input type="number" placeholder="0" min="2" max="10" class="form-control" name="giorni"> 
						      	<input type="hidden" id="scelta" name="scelta" value=<%=ordine.getId()%>>
						    
						  </td>
						  <td>
					     		<button type="submit" class="btn btn-dark btn-sm">Invia Conferma Spedizione</button>
					      	</form>
					      </td>
					    </tr>
				<%
				}
				%>
				  </tbody>
				</table>
			</div>
		</div>
</div>

<%@ include file="/footer.jsp"%> 
</body>
</html>