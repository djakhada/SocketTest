import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.*;

public class ClientHandler extends Thread
{
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	
	private final ServerWindow sw;
	
	final int startKapital;
	final int maximalerKredit;
	final boolean startkapitalKredit;
	final int maximaleSpielerAnzahl;
	int spielerAnzahl;
	String name;
	List<Spieler> spieler = new ArrayList<>();
	
	public ClientHandler(ServerWindow sw, Socket s, DataInputStream dis, DataOutputStream dos, List<Spieler> spielerListe, int startKapital, int maximalerKredit, boolean startkapitalKredit, int maximaleSpielerAnzahl) {
		this.sw = sw;
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.spieler = spielerListe;
		this.startKapital = startKapital;
		this.maximalerKredit = maximalerKredit;
		this.startkapitalKredit = startkapitalKredit;
		this.maximaleSpielerAnzahl = maximaleSpielerAnzahl;
	}

	public void addSpieler(List<Spieler> spielerListe, String Name,  int startKapital, boolean startkapitalKredit) {
		if(spielerListe.size()<maximaleSpielerAnzahl) {
			Spieler s;
			if(startkapitalKredit) {
				 s = new Spieler(Name, startKapital, startKapital);
				
			}else {
				s = new Spieler(Name, startKapital, 0);
				
			}
			spielerListe.add(s);
			System.out.println("Spieler "+s.Name+" wurde der Spielerliste hinzugefügt.("+spielerListe.size()+"/"+maximaleSpielerAnzahl+")");
		}else{
			System.out.println("Spieler "+Name+" wurde der Spielerliste nicht hinzugefügt, da das Spiel voll ist.");
		}
	}
	
	@Override
	public void run() 
	{
		System.out.println("Server: Höre Client: "+s+" jetzt zu.");
		String received;
		while (true) 
		{
			try {				
				received = dis.readUTF();
				
				if(received.equals("quit")) {
					System.out.println("Server: Client " + this.s + " möchte trennen.");
					this.s.close();
					System.out.println("Server: Client " + this.s + " wurde getrennt.");
					break;
				}else if(received.contains("join:")){
					name = received.substring(5);
					System.out.println("Server: Spieler '"+name+"' ist dem Spiel beigetreten.");
					addSpieler(spieler, name, startKapital, startkapitalKredit);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.dos.close();
			this.dis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
