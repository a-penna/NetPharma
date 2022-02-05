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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
	<!-- jQuery library --> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
	<!-- Popper JS --> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
	<!-- Latest compiled JavaScript --> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>            

<body class="bg-light">
	<%@ include file="/header.jsp"%>
	<div class="container-fluid py-5">
		<div class="row">

			<div class="col-md-10">
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
					        <button type="submit" class="btn btn-primary">Elimina</button>
					   </fieldset>
				</form> 
			</div>
		</div>
	</div>
</body>
</html> 