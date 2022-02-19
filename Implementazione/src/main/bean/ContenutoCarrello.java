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
		return  getClass().getName() +" [prodotto=" + prodotto + ", quantity=" + quantity + "]";
	}
	
	@Override
	public boolean equals(Object otherObject){
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ContenutoCarrello other = (ContenutoCarrello)otherObject; 
		return this.prodotto.equals(other.prodotto)
				&& (this.quantity == other.quantity);
	}
}
