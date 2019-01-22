import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


//libs benötigt für serverzeugs
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

public class ServerWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	
	private Spieler[] spieler = new Spieler[25];
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow frame = new ServerWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void runServer(int startKapital, int maximalerKredit, float zinssatz, boolean startKapitalKredit, int maximaleSpieleranzahl, Spieler[] arr) throws IOException{
		ServerSocket ss = new ServerSocket(23554);
		System.out.println("Server: Serverstart..");
		while (true) {
			Socket s = null;
			System.out.println("Server: Halte Ausschau nach Clients..");
			try {
				s = ss.accept();
				
				System.out.println("Server: Neuer Client verbunden: " + s);
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				System.out.println("Server: Neuen Thread für diesen Client erstellen");
				
				Thread t = new ClientHandler(s, dis, dos, arr);
				
				t.start();
				
			}catch(Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}
	
	public ServerWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStartkapital = new JLabel("Startkapital");
		lblStartkapital.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStartkapital.setBounds(10, 11, 84, 14);
		contentPane.add(lblStartkapital);
		
		textField = new JTextField();
		textField.setText("250000");
		textField.setBounds(104, 8, 59, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Startkapital ist Kredit");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(6, 83, 140, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JLabel lblZinssatz = new JLabel("Kreditzinssatz");
		lblZinssatz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZinssatz.setBounds(9, 62, 98, 14);
		contentPane.add(lblZinssatz);
		
		textField_1 = new JTextField();
		textField_1.setText("9");
		textField_1.setColumns(10);
		textField_1.setBounds(104, 59, 59, 20);
		contentPane.add(textField_1);
		
		JLabel lblMaximalerKreditIn = new JLabel("Maximaler Kredit");
		lblMaximalerKreditIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaximalerKreditIn.setBounds(10, 36, 112, 14);
		contentPane.add(lblMaximalerKreditIn);
		
		JLabel label = new JLabel("\u20AC");
		label.setBounds(167, 11, 16, 14);
		contentPane.add(label);
		
		textField_2 = new JTextField();
		textField_2.setText("500000");
		textField_2.setColumns(10);
		textField_2.setBounds(104, 33, 59, 20);
		contentPane.add(textField_2);
		
		JLabel label_1 = new JLabel("\u20AC");
		label_1.setBounds(167, 36, 16, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("%");
		label_2.setBounds(167, 62, 16, 14);
		contentPane.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setText("9");
		textField_3.setColumns(10);
		textField_3.setBounds(104, 124, 59, 20);
		contentPane.add(textField_3);
		
		JLabel lblMaximaleSpieler = new JLabel("Maximale Spieler");
		lblMaximaleSpieler.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaximaleSpieler.setBounds(10, 127, 98, 14);
		contentPane.add(lblMaximaleSpieler);
		
		JButton btnSpielStarten = new JButton("Spiel starten");
		btnSpielStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSpielStarten.setEnabled(false);
				System.out.println("Server: Versuche Server zu starten..");
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							runServer(250000,1,1.0f,true,5, spieler);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				System.out.println("Server: Server erfolgreich gestartet.");
				
			}
		});
		btnSpielStarten.setBounds(5, 152, 158, 23);
		contentPane.add(btnSpielStarten);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(186, 4, 358, 205);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Name", "Kapital", "Kredit", "Geld\u00E4nderung [R]", "IP"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JButton btnNchsteRunde = new JButton("N\u00E4chste Runde");
		btnNchsteRunde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,spieler[0].getName());
			}
		});
		btnNchsteRunde.setEnabled(false);
		btnNchsteRunde.setBounds(6, 186, 157, 23);
		contentPane.add(btnNchsteRunde);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
	}
}
