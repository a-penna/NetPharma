package bean;

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
	private Categoria categoria;
	private byte[] foto;
	
	public Prodotto() { }
	
	public Prodotto(int id, String nome, String marchio, String produttore, String formato, String descrizione,
			int disponibilita, BigDecimal prezzo, Categoria categoria, byte[] foto) {
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
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
		return "Prodotto [id=" + id + ", nome=" + nome + ", marchio=" + marchio + ", produttore=" + produttore
				+ ", formato=" + formato + ", descrizione=" + descrizione + ", disponibilita=" + disponibilita
				+ ", prezzo=" + prezzo + ", categoria=" + categoria + ", foto=" + Arrays.toString(foto) + "]";
	}
	
}
