<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop, farmacia, shop online, prodotti, checkout">
	<meta name="description" content="E-commerce, checkout">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
    <title>NetPharma &dash; Checkout</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Latest compiled and minified CSS --> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">						
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>            

<body class="bg-light">
<h3>Indirizzo di spedizione : </h3>
<form method="post" action="<%=response.encodeURL(request.getContextPath() + "/Checkout")%>">
			<fieldset>
		    	<div class="form-group">
		             <label for="email">E-Mail&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend1">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="email" aria-describedby="inputGroupPrepend1" placeholder="Inserisci e-mail" value="<%=request.getAttribute("email")%>" name="email">
						</div>
						</div>
					<div class="form-group">
		             <label for="name">Nome&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend2">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="name" aria-describedby="inputGroupPrepend2" placeholder="Inserisci nome" value="<%=request.getAttribute("name")%>" name="name">
						</div>
						</div>
							<div class="form-group">
		             <label for="surname">Cognome&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend3">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="surname" aria-describedby="inputGroupPrepend3" placeholder="Inserisci cognome" value="<%=request.getAttribute("surname")%>" name="surname">
						</div>
						</div>
							<div class="form-group">
		             <label for="city">Città&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend4">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="city" aria-describedby="inputGroupPrepend4" placeholder="Inserisci città" value="<%=request.getAttribute("city")%>" name="city">
						</div>
						</div>
							<div class="form-group">
		             <label for="country">Paese&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend5">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="country" aria-describedby="inputGroupPrepend5" placeholder="Inserisci paese" value="<%=request.getAttribute("country")%>" name="country">
						</div>
						</div>
							<div class="form-group">
		             <label for="provincia">Provincia&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend6">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="provincia" aria-describedby="inputGroupPrepend6" placeholder="Inserisci provincia" value="<%=request.getAttribute("provincia")%>" name="provincia">
						</div>
						</div>
							<div class="form-group">
		             <label for="cap">CAP&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend7">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="cap" aria-describedby="inputGroupPrepend7" placeholder="Inserisci cap" value="<%=request.getAttribute("cap")%>" name="cap">
						</div>
						</div>
							<div class="form-group">
		             <label for="address">Indirizzo&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend8">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="address" aria-describedby="inputGroupPrepend8" placeholder="Inserisci address" value="<%=request.getAttribute("address")%>" name="address">
						</div>
						</div>
							<div class="form-group">
		             <label for="number">Numero Civico&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend9">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="number" aria-describedby="inputGroupPrepend9" placeholder="Inserisci numero civico" value="<%=request.getAttribute("number")%>" name="number">
						</div>
						</div>
							<div class="form-group">
		             <label for="cellulare">Cellulare&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend10">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <input type="text" class="form-control" id="cellulare" aria-describedby="inputGroupPrepend10" placeholder="Inserisci cellulare" value="<%=request.getAttribute("cellulare")%>" name="cellulare">
						</div>
						</div>
						<div class="form-check">
  							<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
  								<label class="form-check-label" for="flexRadioDefault1">
   								Corriere di default			 
  								</label>
						</div>
						
						<!-- Altri corrieri -->
						<!-- Da mettere in altra sezione della pagina tramite la grafica a sezioni -->
						<h3>Fatturazione :</h3>
						<div class="form-check">
  							<input class="form-check-input" type="radio" name="flexRadioDefault2" id="flexRadioDefault2">
  								<label class="form-check-label" for="flexRadioDefault2">
   								Riepilogo non fiscale	 
  								</label>
						</div>
						<div class="form-check">
  							<input class="form-check-input" type="radio" name="flexRadioDefault2" id="flexRadioDefault3">
  								<label class="form-check-label" for="flexRadioDefault3">
   								Invio telematico 
  								</label>
						</div>
						<div class="form-check">
  							<input class="form-check-input" type="radio" name="flexRadioDefault2" id="flexRadioDefault4">
  								<label class="form-check-label" for="flexRadioDefault4">
   								Fattura elettronica
  								</label>
						</div>
						<!--  Metodi di pagamento -->
						<!-- Da mettere in altra sezione della pagina tramite la grafica a sezioni -->
			</fieldset>
</form>



<body>

</body>
</html>