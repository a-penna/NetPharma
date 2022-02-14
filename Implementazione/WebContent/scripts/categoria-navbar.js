$(document).ready(function() {
				$.ajax({
					url:"ListaCategorie",
					method:"GET",
				})
				.done(function(msg) {
					var categorie = msg.listaCategorie;  
					var c;
					for (c in categorie) {
							$("#categorie").append('<a class="dropdown-item mydark-dropdown-item" href="/NetPharma/ProdottiCategoria?categoria='+ categorie[c][0] + '">' + categorie[c][1] + '</a>');
						}
				})
				.fail(function(xhr, textStatus) {
						alert("Errore");
				});
});
	

