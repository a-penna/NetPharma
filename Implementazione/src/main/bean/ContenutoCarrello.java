package main.bean;

public class ContenutoCarrello {
	private Prodotto prodotto;
	private int quantity;
	
	public ContenutoCarrello() { }

	public ContenutoCarrello(Prodotto prodotto, int quantity) {
		this.prodotto = prodotto;
		this.quantity = quantity;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ContenutoCarrello [prodotto=" + prodotto + ", quantity=" + quantity + "]";
	}
	
}
