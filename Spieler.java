
public class Spieler {
	private String Name;
	private int Kapital;
	private int Kredit;
	
	public Spieler(String Name, int Kapital, int Kredit) {
		this.Name = Name;
		this.Kapital = Kapital;
		this.Kredit = Kredit;
	}
	
	public void setName(String arg1) {
		Name = arg1;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setKapital(int arg1) {
		Kapital = arg1;
	}
	
	public int getKapital() {
		return Kapital;
	}
	
	public void setKredit(int arg1) {
		Kredit = arg1;
	}
	
	public int getKredit() {
		return Kredit;
	}
}
