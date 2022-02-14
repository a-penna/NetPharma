<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop online, shop, operazioni gestore catalogo, gestore catalogo, rimuovi, prodotto, rimuovi prodotto">
	<meta name="description" content="Rimuovi Prodotto">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>NetPharma &dash; Rimuovi Prodotto</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<%@ include file="/commonSources.jsp"%>
	<script>
		$(document).ready(function() {
			$(".my-tablist-element").removeClass("bg-dark text-white");
			$("#rimuoviProdotto").addClass("bg-dark text-white");
			
			$('#elimina').click(function() {
				var id = $('#id').val();
				$.ajax({
					url:"/NetPharma/ProdottoAJAX?id=" + id,
					method:"GET",
				})
				.done(function(msg) {
					var prodotti = msg.prodotto;
					$("#modalLabel").html(prodotti[0]);
					$("#marchio").html("<b>Marchio:</b> " + prodotti[1]);
					$("#produttore").html("<b>Produttore:</b> " + prodotti[2]);
					$("#formato").html("<b>Formato:</b> " + prodotti[3]);
					$("#prezzo").html("<b>Prezzo: </b>" + prodotti[6] + "&euro;");
					$('#myModal').modal('show');
				})
				.fail(function(xhr, textStatus) {
					alert("Non esiste alcun prodotto con questo codice");
				});	
			});
		});
	</script>
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<form action="<%=response.encodeURL(request.getContextPath() + "/RimuoviProdotto")%>" method="post" onsubmit="event.preventDefault();"> 
					    <fieldset>
							<div class="form-group">
								
					        	<label for="id">Codice Prodotto&colon;</label>
								<input id="id" type="text" class="form-control" name="id" placeholder="Codice prodotto">
				   			</div>
					        <button type="button" id="elimina" class="btn btn-dark">Elimina</button>
					   </fieldset>
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="modalLabel"></h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						        <div id="marchio"></div>
						        <div id="produttore"></div>
						        <div id="formato"></div>
						        <div id="prezzo"></div>
						      </div>
						      <div class="modal-footer">
						        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
						        <button type="button" onclick="submit()" class="btn btn-dark">Elimina</button>
						      </div>
						    </div>
						  </div>
						</div>
				</form> 
			</div>
		</div>
	</div>
</body>
</html> 