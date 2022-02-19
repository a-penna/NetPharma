<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*, main.model.*, main.bean.*"%>

<% 
	Collection<?> categorie = (Collection<?>) request.getAttribute("listaCategorie");
    if (categorie == null) {
        response.sendRedirect(response.encodeURL(request.getContextPath() + "/RimuoviCategoria"));
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="NetPharma, piattaforma e-commerce, e-commerce, shop online, shop, operazioni gestore catalogo, gestore catalogo, rimuovi, categoria, rimuovi categoria">
	<meta name="description" content="Rimuovi Categoria">
	<meta name="author" content="Bene Sabato, Cozzolino Lidia, Napoli Riccardo, Penna Alessandro">    
	<title>NetPharma &dash; Rimuovi Categoria</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/commonSources.jsp"%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		$(document).ready(function() {
			$(".my-tablist-element").removeClass("bg-dark text-white");
			$("#rimuoviCategoria").addClass("bg-dark text-white");
		});
	</script>
</head>            

<body class="bg-light">
	<%@ include file="/headerGestori.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">
			<%@ include file="gestoreCatalogoMenu.jsp"%>
			<div class="col-md-8">
				<form action="<%=response.encodeURL(request.getContextPath() + "/RimuoviCategoria")%>" method="post"> 
					    <fieldset>
					        <legend>Inserisci informazioni sulla categoria&colon; </legend>
							<div class="form-group">
						        <select class="custom-select" name="id">
						        		<option disabled selected>Seleziona Categoria</option>
							            <%
							            Iterator<?> it = categorie.iterator();
							            while(it.hasNext()) {
											Categoria bean = (Categoria)it.next();
											 %>
							            		<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>	            		
							            <%	  
										} 
							            %>
							     </select> 
							     <div class="invalid-feedback">Seleziona la categoria da eliminare&excl;</div>
				   			</div>
					        <button type="submit" class="btn btn-dark">Elimina</button>
					   </fieldset>
				</form> 
			</div>
		</div>
	</div>
	<%@ include file="/footer.jsp"%> 
</body>
</html> 