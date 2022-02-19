package main.bean;

import java.math.BigDecimal;
import java.util.Arrays;

public class Prodotto {
	private int id;
	private String nome;
	private String marchio;
	private String produttore;
	private String formato;
	private String descrizione;
	private int disponibilita;
	private BigDecimal prezzo;
	private String categoria;
	private byte[] foto;
	
	public Prodotto() { }
	
	public Prodotto(int id, String nome, String marchio, String produttore, String formato, String descrizione,
			int disponibilita, BigDecimal prezzo, String categoria, byte[] foto) {
		this.id = id;
		this.nome = nome;
		this.marchio = marchio;
		this.produttore = produttore;
		this.formato = formato;
		this.descrizione = descrizione;
		this.disponibilita = disponibilita;
		this.prezzo = prezzo;
		this.categoria = categoria;
		this.foto = foto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarchio() {
		return marchio;
	}

	public void setMarchio(String marchio) {
		this.marchio = marchio;
	}

	public String getProduttore() {
		return produttore;
	}

	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return  getClass().getName() +" [id=" + id + ", nome=" + nome + ", marchio=" + marchio + ", produttore=" + produttore
				+ ", formato=" + formato + ", descrizione=" + descrizione + ", disponibilita=" + disponibilita
				+ ", prezzo=" + prezzo + ", categoria=" + categoria + ", foto=" + Arrays.toString(foto) + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prodotto other = (Prodotto) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (disponibilita != other.disponibilita)
			return false;
		if (formato == null) {
			if (other.formato != null)
				return false;
		} else if (!formato.equals(other.formato))
			return false;
		if (id != other.id)
			return false;
		if (marchio == null) {
			if (other.marchio != null)
				return false;
		} else if (!marchio.equals(other.marchio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (prezzo == null) {
			if (other.prezzo != null)
				return false;
		} else if (prezzo.compareTo(other.prezzo) != 0)
			return false;
		if (produttore == null) {
			if (other.produttore != null)
				return false;
		} else if (!produttore.equals(other.produttore))
			return false;
		return true;
	}
	
	
}
