<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


  <div class="col-md-4">
  	<div class="list-group" id="list-tab" role="tablist">
		<a id="inserisciProdotto" class="list-group-item list-group-item-action list-group-item-light my-tablist-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiProdotto.jsp">Inserisci Prodotto</a>
		<a id="rimuoviProdotto" class="list-group-item list-group-item-action list-group-item-light my-tablist-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviProdotto.jsp">Rimuovi Prodotto</a>
		<a id="modificaProdotto" class="list-group-item list-group-item-action list-group-item-light my-tablist-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreCatalogo/modificaProdotto.jsp">Modifica Prodotto</a>
		<a id="creaCategoria" class="list-group-item list-group-item-action list-group-item-light my-tablist-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiCategoria.jsp">Crea Categoria</a>
		<a id="rimuoviCategoria" class="list-group-item list-group-item-action list-group-item-light my-tablist-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviCategoria.jsp">Rimuovi Categoria</a>	
   </div>
 </div>
