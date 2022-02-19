package main.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Carrello {
	private ArrayList<ContenutoCarrello> prodotti;

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
	
	public void removeItem(Prodotto p) {
		for (int i = 0; i < prodotti.size(); i++) {
			if (prodotti.get(i).getProdotto().getId() == p.getId()) {
				prodotti.remove(i);
				return;
			}
		}
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
	
	public BigDecimal getTotale() {
		BigDecimal totale = BigDecimal.ZERO;
		for(ContenutoCarrello c : prodotti) 
			totale = totale.add(c.getProdotto().getPrezzo().multiply(new BigDecimal(c.getQuantity())));
		
		return totale;
	}
	
	public int getNProdotti() {
		int nProdotti = 0;
		for(ContenutoCarrello c : prodotti) 
			nProdotti += c.getQuantity();
		
		return nProdotti;
	}

	public void clearCart() {
		prodotti.clear();
	}

	@Override
	public boolean equals(Object otherObject){
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Carrello other = (Carrello)otherObject; 
		return this.prodotti.equals(other.prodotti);
	}

	@Override
	public String toString() {
		return getClass().getName() + " [prodotti=" + prodotti + "]";
	}
}
