<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    <%@ include file="/commonSources.jsp"%>
    <script src="<%=request.getContextPath()%>/scripts/categoria-navbar.js"></script> 					
	<script>
	$(document).ready(function(){

		var current_fs, next_fs, previous_fs; //fieldsets
		var opacity;

		$(".next").click(function(){

		current_fs = $(this).parent();
		next_fs = $(this).parent().next();

		//Add Class Active
		$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

		//show the next fieldset
		next_fs.show();
		//hide the current fieldset with style
		current_fs.animate({opacity: 0}, {
		step: function(now) {
		// for making fielset appear animation
		opacity = 1 - now;

		current_fs.css({
		'display': 'none',
		'position': 'relative'
		});
		next_fs.css({'opacity': opacity});
		},
		duration: 600
		});
		});

		$(".previous").click(function(){

		current_fs = $(this).parent();
		previous_fs = $(this).parent().prev();

		//Remove class active
		$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

		//show the previous fieldset
		previous_fs.show();

		//hide the current fieldset with style
		current_fs.animate({opacity: 0}, {
		step: function(now) {
		// for making fielset appear animation
		opacity = 1 - now;

		current_fs.css({
		'display': 'none',
		'position': 'relative'
		});
		previous_fs.css({'opacity': opacity});
		},
		duration: 600
		});
		});

		$('.radio-group .radio').click(function(){
		$(this).parent().find('.radio').removeClass('selected');
		$(this).addClass('selected');
		});

		$(".submit").click(function(){
		return false;
		})

		});
	</script>
</head>            

<body class="bg-dark">
<%@ include file="/header.jsp"%>
 <div class="container-fluid" id="grad1">
        <div class="col text-center py-2 px-2">
            <div class="card my-card px-0 pt-4 pb-0 mt-3 mb-3">
                <div class="row">
                    <div class="col-md-12 mx-0">
                        <form id="msform" method="post" action="<%=response.encodeURL(request.getContextPath() + "/Checkout")%>">
							 <ul id="progressbar">
							             <li class="active" id="spedizione"><strong>Spedizione</strong></li>
							             <li id="fatturazione"><strong>Fatturazione</strong></li>
							             <li id="pagamento"><strong>Pagamento</strong></li>
							             <li id="terminato"><strong>Terminato</strong></li>
							</ul>
		<div class="form-card">
			<fieldset>
		    	<div class="form-group text-left px-2">
		             <label for="email"><h4>INDIRIZZO DI SPEDIZIONE</h4><br>E-Mail&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend1">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <%
								if (request.getAttribute("erroreEmail") != null || request.getAttribute("erroreFormatoEmail") != null) {
									%> <input type="text" class="form-control is-invalid" id="email" value="<%=request.getAttribute("email")%>" aria-describedby="inputGroupPrepend1" placeholder="Inserisci email" name="email"><% 
								} else if (request.getAttribute("email") != null) {
									%><input type="text" class="form-control" id="email" aria-describedby="inputGroupPrepend1" value="<%=request.getAttribute("email")%>" placeholder="Inserisci email" name="email"><%
								} else {
									%><input type="text" class="form-control" id="email" aria-describedby="inputGroupPrepend1" placeholder="Inserisci email" name="email"><% 
								}
			            	
			            		if(request.getAttribute("erroreEmail") != null) {%>
		               				 <div class="invalid-feedback">Inserisci un&apos;indirizzo email &lpar;max&period; 40 caratteri&rpar;</div> 
			       			<%  } else {  %>
			       					<div class="invalid-feedback">Il formato deve essere del tipo&colon; x&commat;x</div> 
			       			<% } %>
						
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="name">Nome&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend2">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
			            	<%
								if (request.getAttribute("erroreNome") != null || request.getAttribute("erroreFormatoNome") != null) {
									%> <input type="text" class="form-control is-invalid" id="name" value="<%=request.getAttribute("name")%>" aria-describedby="inputGroupPrepend2" placeholder="Inserisci nome" name="name"><% 
								} else if (request.getAttribute("name") != null) {
									%><input type="text" class="form-control" id="name" aria-describedby="inputGroupPrepend2" value="<%=request.getAttribute("name")%>" placeholder="Inserisci nome" name="name"><%
								} else {
									%><input type="text" class="form-control" id="name" aria-describedby="inputGroupPrepend2" placeholder="Inserisci nome" name="name"><% 
								}
			            	
			            		if(request.getAttribute("erroreNome") != null) {%>
		               				 <div class="invalid-feedback">Inserisci un nome &lpar;max&period; 20 caratteri&rpar;</div> 
			       			<%  } else {  %>
			       					<div class="invalid-feedback">Sono ammesse solo lettere</div> 
			       			<% } %>
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="surname">Cognome&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend3">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <%
								if (request.getAttribute("erroreCognome") != null || request.getAttribute("erroreFormatoCognome") != null) {
									%> <input type="text" class="form-control is-invalid" id="surname" value="<%=request.getAttribute("name")%>" aria-describedby="inputGroupPrepend3" placeholder="Inserisci cognome" name="surname"><% 
								} else if (request.getAttribute("surname") != null) {
									%><input type="text" class="form-control" id="surname" aria-describedby="inputGroupPrepend3" value="<%=request.getAttribute("surname")%>" placeholder="Inserisci cognome" name="surname"><%
								} else {
									%><input type="text" class="form-control" id="surname" aria-describedby="inputGroupPrepend3" placeholder="Inserisci cognome" name="surname"><% 
								}
			            	
			            		if(request.getAttribute("erroreCognome") != null) {%>
		               				 <div class="invalid-feedback">Inserisci un cognome &lpar;max&period; 20 caratteri&rpar;</div> 
			       			<%  } else {  %>
			       					<div class="invalid-feedback">Sono ammesse solo lettere</div> 
			       			<% } %>
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="city">Città&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend4">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <%if (request.getAttribute("erroreCity") != null) {
							%> <input type="text" class="form-control is-invalid" id="city" value="<%=request.getAttribute("city")%>" aria-describedby="inputGroupPrepend4" placeholder="Inserisci citt&agrave;" name="city"><% 
						} else if (request.getAttribute("surname") != null) {
							%><input type="text" class="form-control" id="city" aria-describedby="inputGroupPrepend4" value="<%=request.getAttribute("city")%>" placeholder="Inserisci citt&agrave;" name="city"><%
						} else {
							%><input type="text" class="form-control" id="city" aria-describedby="inputGroupPrepend4" placeholder="Inserisci citt&agrave;" name="city"><% 
						} %>
					  
					  <div class="invalid-feedback">Inserisci una citt&agrave; &lpar;max&period; 50 caratteri&rpar;</div> 
					  </div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="country">Paese&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend5">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				         <%if (request.getAttribute("errorePaese") != null) {
							%> <input type="text" class="form-control is-invalid" id="country" value="<%=request.getAttribute("country")%>" aria-describedby="inputGroupPrepend5" placeholder="Inserisci paese" name="country"> <% 
						} else if (request.getAttribute("country") != null) {
							%><input type="text" class="form-control" id="country" aria-describedby="inputGroupPrepend5" value="<%=request.getAttribute("country")%>" placeholder="Inserisci paese" name="country" ><%
						} else {
							%><input type="text" class="form-control" id="country" aria-describedby="inputGroupPrepend5" placeholder="Inserisci paese" name="country"><% 
						} %>
					  
					  <div class="invalid-feedback">Inserisci un paese  &lpar;max&period; 50 caratteri&rpar;</div> 
					  </div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="provincia">Provincia&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend6">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				         <%if (request.getAttribute("erroreProvincia") != null) {
							%> <input type="text" class="form-control is-invalid" id="provincia" value="<%=request.getAttribute("provincia")%>" aria-describedby="inputGroupPrepend6" placeholder="Inserisci provincia" name="provincia"><% 
						} else if (request.getAttribute("provincia") != null) {
							%><input type="text" class="form-control" id="provincia" aria-describedby="inputGroupPrepend6" value="<%=request.getAttribute("provincia")%>" placeholder="Inserisci provincia" name="provincia"><%
						} else {
							%><input type="text" class="form-control" id="provincia" aria-describedby="inputGroupPrepend6" placeholder="Inserisci provincia" name="provincia"><% 
						} %>
					  
					  <div class="invalid-feedback">Inserisci una provincia  &lpar;max&period; 50 caratteri&rpar;</div> 
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="cap">CAP&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend7">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				        <%
								if (request.getAttribute("erroreCAP") != null || request.getAttribute("erroreFormatoCAP") != null) {
									%> <input type="text" class="form-control is-invalid" id="cap" value="<%=request.getAttribute("cap")%>" aria-describedby="inputGroupPrepend7" placeholder="Inserisci cap" name="cap"><% 
								} else if (request.getAttribute("cap") != null) {
									%><input type="text" class="form-control" id="cap" aria-describedby="inputGroupPrepend7" value="<%=request.getAttribute("cap")%>" placeholder="Inserisci cap" name="cap"><%
								} else {
									%><input type="text" class="form-control" id="cap" aria-describedby="inputGroupPrepend7" placeholder="Inserisci cap" name="cap"><% 
								}
			            	
			            		if(request.getAttribute("erroreCAP") != null) {%>
		               				 <div class="invalid-feedback">Inserisci CAP &lpar;max&period; 5 caratteri&rpar;</div> 
			       			<%  } else {  %>
			       					<div class="invalid-feedback">Sono permessi solo numeri</div> 
			       			<% } %>
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="address">Indirizzo&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend8">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				         <%if (request.getAttribute("erroreIndirizzo") != null) {
							%> <input type="text" class="form-control is-invalid" id="address" value="<%=request.getAttribute("address")%>" aria-describedby="inputGroupPrepend8" placeholder="Inserisci indirizzo" name="address"><% 
						} else if (request.getAttribute("address") != null) {
							%><input type="text" class="form-control" id="address" aria-describedby="inputGroupPrepend8" value="<%=request.getAttribute("address")%>" placeholder="Inserisci indirizzo" name="address"><%
						} else {
							%><input type="text" class="form-control" id="address" aria-describedby="inputGroupPrepend8" placeholder="Inserisci indirizzo" name="address"><% 
						} %>
					  
					  <div class="invalid-feedback">Inserisci un indirizzo  &lpar;max&period; 50 caratteri&rpar;</div> 
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="number">Numero Civico&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend9">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				         <%if (request.getAttribute("erroreCivico") != null) {
							%> <input type="text" class="form-control is-invalid" id="number" value="<%=request.getAttribute("number")%>" aria-describedby="inputGroupPrepend9" placeholder="Inserisci numero civico" name="number"><% 
						} else if (request.getAttribute("number") != null) {
							%><input type="text" class="form-control" id="number" aria-describedby="inputGroupPrepend9" value="<%=request.getAttribute("number")%>" placeholder="Inserisci numero civico" name="number"><%
						} else {
							%><input type="text" class="form-control" id="number" aria-describedby="inputGroupPrepend9" placeholder="Inserisci numero civico" name="number"><% 
						} %>
					  
					  <div class="invalid-feedback">Sono permessi solo numeri</div> 
						
						</div>
				</div>
				<div class="form-group text-left px-2">
		             <label for="cellulare">Cellulare&colon;</label>
					  <div class="input-group">
				        <div class="input-group-prepend">
				          <span class="input-group-text" id="inputGroupPrepend10">
				          	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
								<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>									
							</svg>
						  </span>
				        </div>
				         <%
								if (request.getAttribute("erroreCellulare") != null || request.getAttribute("erroreFormatoCellulare") != null) {
									%> <input type="text" class="form-control is-invalid" id="cellulare" value="<%=request.getAttribute("cap")%>" aria-describedby="inputGroupPrepend10" placeholder="Inserisci cellulare" name="cellulare"><% 
								} else if (request.getAttribute("cap") != null) {
									%><input type="text" class="form-control" id="cellulare" aria-describedby="inputGroupPrepend10" value="<%=request.getAttribute("cellulare")%>" placeholder="Inserisci cellulare" name="cellulare"><%
								} else {
									%><input type="text" class="form-control" id="cellulare" aria-describedby="inputGroupPrepend10" placeholder="Inserisci cellulare" name="cellulare"><% 
								}
			            	
			            		if(request.getAttribute("erroreCellulare") != null) {%>
		               				 <div class="invalid-feedback">Inserisci un numero di cellulare &lpar;max&period; 15 caratteri&rpar;</div> 
			       			<%  } else {  %>
			       					<div class="invalid-feedback">Sono permessi solo numeri</div> 
			       			<% } %>
					  </div>
			    </div>
			    <br>
				<div class="form-check text-left pl-4">
					<p><h4>METODI DI SPEDIZIONE</h4></p>
  							<input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
  								<label class="form-check-label" for="flexRadioDefault1">
   								Corriere di default			 
  								</label>
				</div>
				

						<input type="button" name="next" class="next action-button" value="Continua"/>
			    

					
						
						<!-- Altri corrieri -->
						<!-- Da mettere in altra sezione della pagina tramite la grafica a sezioni -->
			   </fieldset>

			   <fieldset>
			   <div class="form-card">
                      <h2 class="fs-title px-2">Fatturazione</h2>
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
			   </div>
				<input type="button" name="previous" class="previous action-button-previous" value="Previous" /> <input type="button" name="make_payment" class="next action-button" value="Confirm" />
			   </fieldset>
			   <fieldset>
			   <div class="form-card">
			   		<h2 class="fs-title px-2">Pagamento</h2>
			   
			   
						<!--  Metodi di pagamento -->
						<!-- Da mettere in altra sezione della pagina tramite la grafica a sezioni -->
				</div>
				<input type="submit" name="next" class="next action-button" value="Confirm"/>	
				 </fieldset>
				<!-- <fieldset>
	                   <div class="form-card">
	                         <h2 class="fs-title text-center ">Il tuo ordine è andato a buon fine!</h2> <br>
	                             <div class="row justify-content-center">
	                                  <div class="col-3"> <img src="https://img.icons8.com/color/96/000000/ok--v2.png" class="fit-image"> </div>
	                             </div>
	                   </div>
	            </fieldset>-->

				</div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp"%> 
</body>
</html>