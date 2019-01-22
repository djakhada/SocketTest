import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class ClientHandler extends Thread
{
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
	}
	
	@Override
	public void run() 
	{
		System.out.println("Höre Client: "+s+" jetzt zu.");
		String received;
		while (true) 
		{
			try {				
				received = dis.readUTF();
				
				if(received.equals("quit")) {
					System.out.println("Client " + this.s + " möchte quitten.");
					this.s.close();
					break;
				}else if(received.contains("join:")){
					
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
