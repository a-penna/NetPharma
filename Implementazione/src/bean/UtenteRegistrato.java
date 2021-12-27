package bean;

import java.sql.Date;

public class UtenteRegistrato {
	private String genere;
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;
	private Date nascita;
	private String ruolo;
	
	public UtenteRegistrato() { }
	
	public UtenteRegistrato(String genere, String nome, String cognome, String username, String email, String password,
			Date nascita, String ruolo) {
		this.genere = genere;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
		this.nascita = nascita;
		this.ruolo = ruolo;
	}
	
	public String getGenere() {
		return genere;
	}
	
	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getNascita() {
		return nascita;
	}
	
	public void setNascita(Date nascita) {
		this.nascita = nascita;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}
