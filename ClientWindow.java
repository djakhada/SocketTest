import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Netzwerk libs
import java.io.*; 
import java.net.*;
import java.util.Scanner; 

class ResponseHandler extends Thread {
	public ResponseHandler(String Name) {
		//hier die antworten wie bspw full oder so joined handlen
	}
}

public class ClientWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPORT;
	private JTextField txtNAME;
	
	private Socket s = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void connectToServer(String ip, int port, String name) throws UnknownHostException, IOException {
		try {
		s = new Socket (InetAddress.getByName(ip), port);
		dis = new DataInputStream(s.getInputStream()); 
		dos = new DataOutputStream(s.getOutputStream()); 
		System.out.println("Client: Erfolgreich zum Server: ["+s+"] verbunden.");
		dos.writeUTF("join:"+name);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Create the frame.
	 */
	public ClientWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 240, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel connectPanel = new JPanel();
		contentPane.add(connectPanel, BorderLayout.CENTER);
		connectPanel.setLayout(null);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(74, 11, 133, 20);
		connectPanel.add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblIpadresse = new JLabel("IP-Adresse");
		lblIpadresse.setBounds(10, 14, 74, 14);
		connectPanel.add(lblIpadresse);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 42, 74, 14);
		connectPanel.add(lblPort);
		
		txtPORT = new JTextField();
		txtPORT.setText("23554");
		txtPORT.setColumns(10);
		txtPORT.setBounds(74, 39, 133, 20);
		connectPanel.add(txtPORT);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 67, 74, 14);
		connectPanel.add(lblName);
		
		txtNAME = new JTextField();
		txtNAME.setText("Djamal");
		txtNAME.setColumns(10);
		txtNAME.setBounds(74, 64, 133, 20);
		connectPanel.add(txtNAME);
		
		JButton btnVerbinden = new JButton("Verbinden");
		btnVerbinden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnVerbinden.setEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							connectToServer(
									txtIP.getText(),
									Integer.parseInt(txtPORT.getText()),
									txtNAME.getText()
									);
						} catch (NumberFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}).start();
				
			}
		});
		btnVerbinden.setBounds(10, 92, 197, 23);
		connectPanel.add(btnVerbinden);
		
		JButton btnVerbindungTrennen = new JButton("Verbindung Trennen");
		btnVerbindungTrennen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Client: Trennen");
					dos.writeUTF("quit");
					
					dis.close();
			        dos.close();
			        s.close();
			        
			        System.out.println("Client: Erfolgreich getrennt");
			        btnVerbinden.setEnabled(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnVerbindungTrennen.setBounds(10, 126, 197, 23);
		connectPanel.add(btnVerbindungTrennen);
	}

}
