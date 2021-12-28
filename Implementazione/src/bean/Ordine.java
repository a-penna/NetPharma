package bean;

import java.sql.Date;

public class Ordine {

	private Date data_ordine;
	private Date data_arrivo;
	private int id;
	private float prezzo;
	private String stato;
	private String cliente;
	private int dati_spedizione;
	
	
	public Date getData_ordine() {
		return data_ordine;
	}
	
	public void setData_ordine(Date data_ordine) {
		this.data_ordine = data_ordine;
	}
	
	public Date getData_arrivo() {
		return data_arrivo;
	}
	
	public void setData_arrivo(Date data_arrivo) {
		this.data_arrivo = data_arrivo;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public int getDati_spedizione() {
		return dati_spedizione;
	}
	
	public void setDati_spedizione(int dati_spedizione) {
		this.dati_spedizione = dati_spedizione;
	}
	
	

	
}
