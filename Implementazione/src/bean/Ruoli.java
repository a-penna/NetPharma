package bean;

import java.util.ArrayList;

public class Ruoli {
	
	public enum Ruolo { CL, GO, GC } // CL(Cliente), GO(Gestore degli ordini), GC(Gestore del catalogo)
	
	private int Account;
	private ArrayList<Ruolo> ruoli;
	
	public Ruoli() {
		ruoli = new ArrayList<Ruolo>();
	}
	
	public Ruoli(int account, ArrayList<Ruolo> ruoli) {
		Account = account;
		this.ruoli = ruoli;
	}

	public int getAccount() {
		return Account;
	}

	public void setAccount(int account) {
		Account = account;
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
		return "Ruoli [Account=" + Account + ", ruoli=" + ruoli + "]";
	}
	
}
