package main.bean;

import java.math.BigDecimal;
import java.sql.Date;
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
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordine other = (Ordine) obj;
		if (CAP == null) {
			if (other.CAP != null)
				return false;
		} else if (!CAP.equals(other.CAP))
			return false;
		if (cellulare == null) {
			if (other.cellulare != null)
				return false;
		} else if (!cellulare.equals(other.cellulare))
			return false;
		if (citta == null) {
			if (other.citta != null)
				return false;
		} else if (!citta.equals(other.citta))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (cognomeRicevente == null) {
			if (other.cognomeRicevente != null)
				return false;
		} else if (!cognomeRicevente.equals(other.cognomeRicevente))
			return false;
		if (data_arrivo == null) {
			if (other.data_arrivo != null)
				return false;
		} else if (other.data_arrivo == null) {
			return false;
		} else if (!data_arrivo.toString().equals(other.data_arrivo.toString()))
			return false;
		if (data_ordine == null) {
			if (other.data_ordine != null)
				return false;
		} else if (other.data_ordine == null) {
			return false;
		} else if (!data_ordine.toString().equals(other.data_ordine.toString()))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ncivico != other.ncivico)
			return false;
		if (nomeRicevente == null) {
			if (other.nomeRicevente != null)
				return false;
		} else if (!nomeRicevente.equals(other.nomeRicevente))
			return false;
		if (paese == null) {
			if (other.paese != null)
				return false;
		} else if (!paese.equals(other.paese))
			return false;
		if (prezzo == null) {
			if (other.prezzo != null)
				return false;
		} else if (prezzo.compareTo(other.prezzo) != 0)
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		if (righeOrdine == null) {
			if (other.righeOrdine != null)
				return false;
		} else if (!righeOrdine.equals(other.righeOrdine))
			return false;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		if (via == null) {
			if (other.via != null)
				return false;
		} else if (!via.equals(other.via))
			return false;
		return true;
	}
	
}
