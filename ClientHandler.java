import java.net.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.*;

public class ClientHandler extends Thread
{
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	Spieler[] arr = new Spieler[25];
	public String name;
	
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Spieler[] arr) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.arr = arr;
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
					//arr[0].setName(name);
					//ServerWindow.spieler[0] = new Spieler(name, 250000, 250000);
					
					//"ID", "Name", "Kapital", "Kredit", "Geld\u00E4nderung [R]", "IP"
					//Object[] row = { "0",name,"0","0","0","localhoist" };
					//DefaultTableModel model = (DefaultTableModel)ServerWindow.table.getModel();
					//model.addRow(row);
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
