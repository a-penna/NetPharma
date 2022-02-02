<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, bean.*, java.math.*"%>

<% 
    Carrello cart = (Carrello) request.getAttribute("carrello");

    if (cart == null) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/VisualizzaCarrello"));
        return;
    }
%>   

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, farmacia online">
	<meta name="description" content="HomePage">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">  
	<title>NetPharma &dash; Carrello</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
	<script>
		function updateQuantity(prodotto) {
				var quantity = document.getElementsByName("quantity"+prodotto)[0];
				$.ajax({
					url:"ModificaQuantityCarrello?prodotto=" + prodotto + "&quantity=" + quantity.value,
					method:"GET",
				})
				.done(function(msg) {
					if(msg.update == "true") {
						$("#prezzo"+prodotto).html(msg.price + "&euro;");
						$("#prezzoTotale").html("<b>Totale</b> " + msg.prezzoTotale + "&euro;");
						$("#nProdotti").html(msg.nProdotti + " Prodotti");
					} else {
						alert("Errore");
					}
				})
				.fail(function(xhr, textStatus) {
					alert("Errore");
				});	
		}
		
		function add(prodotto) {
			var quantity = document.getElementsByName("quantity"+prodotto)[0];
			quantity.value = parseInt(quantity.value) + 1;
			updateQuantity(prodotto)
		}
		
		function remove(prodotto) {
			var quantity = document.getElementsByName("quantity"+prodotto)[0];
			if (quantity.value > 1) {
				quantity.value = parseInt(quantity.value) - 1;
				updateQuantity(prodotto);
			}
		}
	</script>
	<style>
		/* Chrome, Safari, Edge, Opera */
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button {
		  -webkit-appearance: none;
		  margin: 0;
		}
		
		/* Firefox */
		input[type=number] {
		  -moz-appearance: textfield;
		}
	</style>
</head>
<body>
<header class="py-4"></header>
<%@ include file="header.jsp"%>
<div class="container my-3">
	<br>
	<h4>Il tuo Carrello</h4>
	
	 <br>
	 <p id="nProdotti"> Prodotti (<%=cart.getNProdotti()%> Articoli)</p>
	 <div class="row">
		<div class="col">
		 <table class="table shadow p-3 mb-5 bg-white">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col"></th>
		      <th scope="col">Nome</th>
		      <th scope="col">Descrizione</th>
		      <th scope="col">Quantita'</th>
		      <th scope="col">Prezzo</th>
		    </tr>
		  </thead>
		  <tbody>
		<%
			Prodotto prodotto = null;
			int quantity = 0;
			BigDecimal prezzo = BigDecimal.ZERO;
			
			Collection<?> prodotti = cart.getItems();
			Iterator<?> it = prodotti.iterator();
			while(it.hasNext()) {
		    	ContenutoCarrello cartItem = (ContenutoCarrello)it.next(); 
		    	prodotto = cartItem.getProdotto();
		    	quantity = cartItem.getQuantity();
		    	prezzo = prodotto.getPrezzo().multiply(new BigDecimal(quantity));
		        %>
				<tr>
					<td><img src="<%=request.getContextPath()%>/PhotoControl?&id=<%=prodotto.getId()%>" height="150" width="150" onerror="this.src='./imgs/noPhoto.png'"></td>
					<td><a href="<%=response.encodeURL(request.getContextPath() + "/Prodotto?id=" + prodotto.getId())%>"><%=prodotto.getNome()%></a></td>
					<%String des = prodotto.getDescrizione();
					if (des.length() >= 80) {
						char[] smallDes = new char[80];
		         		des.getChars(0, 79, smallDes, 0);%>
						<td><%=smallDes%>...</td>
						<%
					} else {%>
						<td><%=des%></td>
					<%}%>
					<td>
						<button class="btn btn-block btn-dark" onclick="remove('<%=prodotto.getId()%>')">&dash;</button>
						<input type="number" id="quantity<%=prodotto.getId()%>" name="quantity<%=prodotto.getId()%>" onchange="updateQuantity('<%=prodotto.getId()%>')" value="<%=quantity%>">
						<button class="btn btn-block btn-dark" onclick="add('<%=prodotto.getId()%>')">&plus;</button>
						<a href="<%=response.encodeURL(request.getContextPath() + "/RimuoviProdottoCarrello?prodotto=" + prodotto.getId())%>">Remove</a>
					</td>
					<td><div id="prezzo<%=prodotto.getId()%>"><%=prezzo%>&euro;</div></td>
				</tr>
		        <%   	
		    }     
		 %> 
		  </tbody>
		</table>
	</div>
	<div class="col">
	 	<div class="card text-black bg-light mb-3 shadow p-3 mb-5 bg-white" style="max-width: 18rem;">
	  		<div class="card-header text-center">Riepilogo Ordine</div>
	  			<div class="card-body">
	   				  	<p id="prezzoTotale">Totale <%=cart.getTotale()%> &euro;</p>
	    				<a href="#" class="btn btn-dark text-center btn-block">Vai alla Cassa</a>
				</div>
			</div>		
		</div>
	</div>
</div> 	

</body>
</html>