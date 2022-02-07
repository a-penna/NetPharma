<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*, java.math.*"%>

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
<%@ include file="header.jsp"%>
<div class="container-prodotti mx-3 my-2">
	<br>
	<h4>Il tuo Carrello</h4>
	<br>
	<p id="nProdotti"> Prodotti (<%=cart.getNProdotti()%> Articoli)</p>
	<div class="row">
			<div class="col">
	<%if (cart.getNProdotti() > 0) { %>
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
						<td><img src="<%=request.getContextPath()%>/FotoControl?&id=<%=prodotto.getId()%>" height="150" width="150" onerror="this.src='./imgs/noPhoto.png'"></td>
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
						<div class="row">
							<button class="btn btn-block btn-dark col-xl-4 my-2" onclick="remove('<%=prodotto.getId()%>')">&dash;</button>
						</div>
						<div class="row">
							<input class="container-quantity col-xl-4" type="number" id="quantity<%=prodotto.getId()%>" name="quantity<%=prodotto.getId()%>" onchange="updateQuantity('<%=prodotto.getId()%>')" value="<%=quantity%>">
						</div>
						<div class="row">
							<button class="btn btn-block btn-dark col-xl-4 my-2" onclick="add('<%=prodotto.getId()%>')">&plus;</button>
						</div>
						<div class="row">
							<a href="<%=response.encodeURL(request.getContextPath() + "/RimuoviProdottoCarrello?prodotto=" + prodotto.getId())%>">Remove</a>
						</div>
						</td>
						<td><div id="prezzo<%=prodotto.getId()%>"><%=prezzo%>&euro;</div></td>
					</tr>
			        <%   	
			    }     
			 %> 
			  </tbody>
			</table>
	<%} else {%>
		<h4>Non hai ancora prodotti nel tuo carrello&excl;</h4>
	<%} %>
	</div>
	<div class="col-xs-6">
	 	<div class="card text-black bg-light ml-3 shadow p-3 mb-5 bg-white" style="max-width: 18rem;">
	  		<div class="card-header text-center">Riepilogo Ordine</div>
	  			<div class="card-body">
	   				  	<p id="prezzoTotale">Totale <%=cart.getTotale()%> &euro;</p>
	   				  	<%if (cart.getNProdotti() > 0) { %>
	    					<a href="<%=response.encodeURL(request.getContextPath() + "/checkout.jsp")%>" class="btn btn-dark text-center btn-block">Vai alla Cassa</a>
						<%} else { %>
							<a href="" class="btn btn-dark text-center btn-block disabled">Vai alla Cassa</a>
						<%} %>
				</div>
		</div>
			<div class="col-md-12">
			   	<p><b>Consegna in 24/72 ore | Gratuita da 19,90€</b><br>Tramite Corriere Espresso GLS e DHL.<br><br><b>Modalità di pagamento accettate</b></p>
				<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-credit-card-2-back-fill" viewBox="0 0 16 16">
		  			<path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v5H0V4zm11.5 1a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h2a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-2zM0 11v1a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-1H0z"/>
				</svg>
				<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-paypal" viewBox="0 0 16 16">
		  			<path d="M14.06 3.713c.12-1.071-.093-1.832-.702-2.526C12.628.356 11.312 0 9.626 0H4.734a.7.7 0 0 0-.691.59L2.005 13.509a.42.42 0 0 0 .415.486h2.756l-.202 1.28a.628.628 0 0 0 .62.726H8.14c.429 0 .793-.31.862-.731l.025-.13.48-3.043.03-.164.001-.007a.351.351 0 0 1 .348-.297h.38c1.266 0 2.425-.256 3.345-.91.379-.27.712-.603.993-1.005a4.942 4.942 0 0 0 .88-2.195c.242-1.246.13-2.356-.57-3.154a2.687 2.687 0 0 0-.76-.59l-.094-.061ZM6.543 8.82a.695.695 0 0 1 .321-.079H8.3c2.82 0 5.027-1.144 5.672-4.456l.003-.016c.217.124.4.27.548.438.546.623.679 1.535.45 2.71-.272 1.397-.866 2.307-1.663 2.874-.802.57-1.842.815-3.043.815h-.38a.873.873 0 0 0-.863.734l-.03.164-.48 3.043-.024.13-.001.004a.352.352 0 0 1-.348.296H5.595a.106.106 0 0 1-.105-.123l.208-1.32.845-5.214Z"/>
				</svg>
			</div>		
	</div>
	</div>
	

	
	
</div> 	

</body>
</html>