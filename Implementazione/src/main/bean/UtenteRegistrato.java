package main.bean;

import java.sql.Date;

public class UtenteRegistrato {
	private String genere;
	private String nome;
	private String cognome;
	private String email;
	private Date nascita;
	private int account;
	
	public UtenteRegistrato() { }
	
	public UtenteRegistrato(String genere, String nome, String cognome, String email,
			Date nascita, int account) {
		this.genere = genere;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.nascita = nascita;
		this.account = account;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getNascita() {
		return nascita;
	}
	
	public void setNascita(Date nascita) {
		this.nascita = nascita;
	}
	
	public int getAccount() {
		return account;
	}
	
	public void setAccount(int account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "UtenteRegistrato [genere=" + genere + ", nome=" + nome + ", cognome=" + cognome + 
				", email=" + email + ", nascita=" + nascita + ", account=" + account
				+ "]";
	} 
}
