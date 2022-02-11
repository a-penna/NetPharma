package main.bean;

import java.math.BigDecimal;

public class RigaOrdine {
	private int prodotto;
	private String ordine;
	private int quantity;
	private BigDecimal prezzoAlPezzo;
	
	public RigaOrdine() { }

	
	public RigaOrdine(int prodotto, String ordine, int quantity, BigDecimal prezzoAlPezzo) {
		super();
		this.prodotto = prodotto;
		this.ordine = ordine;
		this.quantity = quantity;
		this.prezzoAlPezzo = prezzoAlPezzo;
	}

	public int getProdotto() {
		return prodotto;
	}

	public void setProdotto(int prodotto) {
		this.prodotto = prodotto;
	}

	public String getOrdine() {
		return ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrezzoAlPezzo() {
		return prezzoAlPezzo;
	}

	public void setPrezzoAlPezzo(BigDecimal prezzoAlPezzo) {
		this.prezzoAlPezzo = prezzoAlPezzo;
	}

	@Override
	public String toString() {
		return "RigaOrdine [prodotto=" + prodotto + ", ordine=" + ordine + ", quantity=" + quantity + ", prezzoAlPezzo="
				+ prezzoAlPezzo + "]";
	}
}