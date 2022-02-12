<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


  <div class="col-md-2">
  	<div class="list-group" id="list-tab" role="tablist">
		<a id="storico-ordini" class="list-group-item list-group-item-action list-group-item-light my-list-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreOrdini/ListaOrdini.jsp">Storico Ordini</a>
		<a id="ordini-da-spedire" class="list-group-item list-group-item-action list-group-item-light my-list-element bg-dark text-white" href="<%=request.getContextPath()%>/gestoreOrdini/SpedisciOrdini.jsp">Ordini da spedire</a>
   </div>
 </div>
