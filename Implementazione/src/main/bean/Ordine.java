package main.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class Ordine {

	private String nomeRicevente;
	private String cognomeRicevente;
	private String email;
	private String cellulare;
	private int ncivico;
	private String citta;
	private String via;
	private String paese;
	private String provincia;
	private String CAP;
	private Date data_ordine;
	private Date data_arrivo;
	private String id;
	private BigDecimal prezzo;
	private String stato;
	private String cliente;
	private Collection<RigaOrdine> righeOrdine;
	
	public Ordine() {
		
	}
	
	public Collection<RigaOrdine> getRigheOrdine() {
		return righeOrdine;
	}

	public void setRigheOrdine(Collection<RigaOrdine> righe) {
		this.righeOrdine = righe;
	}
	
	public String getNomeRicevente() {
		return nomeRicevente;
	}

	public void setNomeRicevente(String nomeRicevente) {
		this.nomeRicevente = nomeRicevente;
	}

	public String getCognomeRicevente() {
		return cognomeRicevente;
	}

	public void setCognomeRicevente(String cognomeRicevente) {
		this.cognomeRicevente = cognomeRicevente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public int getNcivico() {
		return ncivico;
	}

	public void setNcivico(int ncivico) {
		this.ncivico = ncivico;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getPaese() {
		return paese;
	}

	public void setPaese(String paese) {
		this.paese = paese;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCAP() {
		return CAP;
	}

	public void setCAP(String cAP) {
		CAP = cAP;
	}
	
	
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
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(BigDecimal prezzo) {
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

	@Override
	public String toString() {
		return "Ordine [nomeRicevente=" + nomeRicevente + ", cognomeRicevente=" + cognomeRicevente + ", email=" + email
				+ ", cellulare=" + cellulare + ", ncivico=" + ncivico + ", citta=" + citta + ", via=" + via + ", paese="
				+ paese + ", provincia=" + provincia + ", CAP=" + CAP + ", data_ordine=" + data_ordine
				+ ", data_arrivo=" + data_arrivo + ", id=" + id + ", prezzo=" + prezzo + ", stato=" + stato
				+ ", cliente=" + cliente + "]";
	}
}
