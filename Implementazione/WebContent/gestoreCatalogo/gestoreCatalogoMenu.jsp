<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
  <div class="col-2 mx-1">
  	<div class="list-group" id="list-tab" role="tablist">
		<a class="list-group-item list-group-item-action active" id="list-inserisci-prodotto" data-toggle="list" role="tab" aria-controls="inserisci-prodotto" href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiProdotto.jsp">Inserisci Prodotto</a>
		<a class="list-group-item list-group-item-action" id="list-rimuovi-prodotto" data-toggle="list" role="tab" aria-controls="rimuovi-prodotto" href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviProdotto.jsp">Rimuovi Prodotto</a>
		<a class="list-group-item list-group-item-action" id="list-modifica-prodotto" data-toggle="list" role="tab" aria-controls="modifica-prodotto" href="<%=request.getContextPath()%>/gestoreCatalogo/modificaProdotto.jsp">Modifica Prodotto</a>
		<a class="list-group-item list-group-item-action" id="list-crea-categoria" data-toggle="list" role="tab" aria-controls="crea-categoria" href="<%=request.getContextPath()%>/gestoreCatalogo/aggiungiCategoria.jsp">Crea Categoria</a>
		<a class="list-group-item list-group-item-action" id="list-rimuovi-categoria" data-toggle="list" role="tab" aria-controls="rimuovi-categoria" href="<%=request.getContextPath()%>/gestoreCatalogo/rimuoviCategoria.jsp">Rimuovi Categoria</a>	
   </div>
 </div>

  <div class="col-9">
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade show active" id="list-inserisci-prodotto" role="tabpanel" aria-labelledby="inserisci-prodotto">Inserire collegamento alla pagina inserisci prodotto</div>
      <div class="tab-pane fade" id="list-rimuovi-prodotto" role="tabpanel" aria-labelledby="rimuovi-prodotto">Inserire collegamento alla pagina rimuovi prodotto</div>
      <div class="tab-pane fade" id="list-modifica-prodotto" role="tabpanel" aria-labelledby="modifica-prodotto">Inserire collegamento alla pagina modifica prodotto</div>
      <div class="tab-pane fade" id="list-crea-categoria" role="tabpanel" aria-labelledby="crea-categoria">Inserire collegamento alla pagina crea categoria</div>
      <div class="tab-pane fade" id="list-rimuovi-categoria" role="tabpanel" aria-labelledby="rimuovi-categoria">Inserire collegamento alla pagina rimuovi categoria</div>
    </div>
  </div>
</div>