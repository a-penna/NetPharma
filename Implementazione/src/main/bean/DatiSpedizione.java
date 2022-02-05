package main.bean;

public class DatiSpedizione {

	private int id;
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
	private String cliente;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public DatiSpedizione() {
		super();
	}
	
	
	
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public DatiSpedizione(String nomeRicevente, String cognomeRicevente, String email, String cellulare,
			int ncivico, String citta, String paese, String provincia, String cAP, String cliente,String via) {
		super();
		
		this.nomeRicevente = nomeRicevente;
		this.cognomeRicevente = cognomeRicevente;
		this.email = email;
		this.cellulare = cellulare;
		this.ncivico = ncivico;
		this.citta = citta;
		this.paese = paese;
		this.provincia = provincia;
		this.CAP = cAP;
		this.cliente = cliente;
		this.via = via;
	}
	
	@Override
	public String toString() {
		return "DatiSpedizione [id=" + id + ", nomeRicevente=" + nomeRicevente + ", cognomeRicevente="
				+ cognomeRicevente + ", email=" + email + ", cellulare=" + cellulare + ", ncivico=" + ncivico
				+ ", citta=" + citta + ", paese=" + paese + ", provincia=" + provincia + ", CAP=" + CAP + ", cliente="
				+ cliente + "]";
	}
	
	
	
	
}
