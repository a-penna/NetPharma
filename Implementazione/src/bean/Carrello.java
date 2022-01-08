package bean;

import java.util.ArrayList;

public class Carrello {
	ArrayList<ContenutoCarrello> prodotti;

	public Carrello() {
		prodotti = new ArrayList<ContenutoCarrello>();
	}

	public void addItem(Prodotto p) {
		for (ContenutoCarrello c : prodotti) {
			if (c.getProdotto().getId() == p.getId()) {
				c.setQuantity(c.getQuantity() + 1);
				return;
			}
		}

		prodotti.add(new ContenutoCarrello(p, 1));
	}
	
	public void setItem(Prodotto p, int quantity) {
		for (ContenutoCarrello c : prodotti) {
			if (c.getProdotto().getId() == p.getId()) {
				c.setQuantity(quantity);
				return;
			}
		}

		prodotti.add(new ContenutoCarrello(p, quantity));
	}
	
	public ArrayList<ContenutoCarrello> getItems() {
		return prodotti;
	}

	public void clearCart() {
		prodotti.clear();
	}
}
