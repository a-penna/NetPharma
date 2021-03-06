package main.bean;

import java.util.ArrayList;

public class Ruoli {
	
	public enum Ruolo { CL, GO, GC } // CL(Cliente), GO(Gestore degli ordini), GC(Gestore del catalogo)
	
	private int account;
	private ArrayList<Ruolo> ruoli;
	
	public Ruoli() {
		ruoli = new ArrayList<Ruolo>();
	}
	
	public Ruoli(int account, ArrayList<Ruolo> ruoli) {
		this.account = account;
		this.ruoli = ruoli;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public ArrayList<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(ArrayList<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
	
	public void addRuolo(Ruolo ruolo) {
		if (!ruoli.contains(ruolo)) {
			ruoli.add(ruolo);
		}
	}

	public void removeRuolo(Ruolo ruolo) {
		if (!ruoli.contains(ruolo)) {
			ruoli.remove(ruolo);
		}
	}

	@Override
	public String toString() {
		return  getClass().getName() +" [Account=" + account + ", ruoli=" + ruoli + "]";
	}
	
	public static Ruoli.Ruolo stringToRole(String role) {
		if (role.equals("CL")) 
			return Ruoli.Ruolo.CL;
		else if (role.equals("GO")) 
			return Ruoli.Ruolo.GO;
		else if (role.equals("GC")) 
			return Ruoli.Ruolo.GC;
		return null;
	}
	
	public static String roleToString(Ruoli.Ruolo role) {
		if (role == Ruoli.Ruolo.CL) 
			return "CL";
		else if (role == Ruoli.Ruolo.GO) 
			return "GO";
		else if (role == Ruoli.Ruolo.GC) 
			return "GC";
		return null;
	}
	
	public boolean equals(Object otherObject){
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Ruoli other = (Ruoli)otherObject; 
		return account == (other.account)
				&& ruoli.equals(other.ruoli);
	}
}
