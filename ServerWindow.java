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
	private JTextField txtStartkapital;
	private JTextField txtKreditzinssatz;
	private JTextField txtMaximalKredit;
	private JTextField txtMaximaleSpieler;
	private JTable table;
	private int Spieleranzahl = 0;
	
	public List<Spieler> spieler = new ArrayList<>();
	
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
	
	public void runServer(int startKapital, int maximalerKredit, float zinssatz, boolean startKapitalKredit, int maximaleSpieleranzahl) throws IOException{
		ServerSocket ss = new ServerSocket(23554);
		System.out.println("Server: Serverstart folgt..");
		while (true) {
			Socket s = null;
			System.out.println("Server: Server gestartet. Werte: Startkapital: "+startKapital+" Maximaler Kredit: "+maximalerKredit+" "
					+ "Kreditzinssatz: " + zinssatz + "% Maximale Spieler: "+maximaleSpieleranzahl);
			System.out.println("Server: Halte Ausschau nach Clients..");
			try {
				s = ss.accept();
				
				System.out.println("Server: Neuer Client verbunden: " + s);
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				System.out.println("Server: Neuen Thread für diesen Client erstellen");
				
				Thread t = new ClientHandler(this, s, dis, dos, startKapital, maximalerKredit, startKapitalKredit, maximaleSpieleranzahl);
				
				t.start();
				
			}catch(Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}
	
	public void updateTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for(int i=0;i<spieler.size();i++) {
			Spieler s = spieler.get(i);
			model.addRow(new Object[] {i, s.Name, s.Kapital, s.Kredit, s.Geldaenderung});
			//new DefaultTableModel(new Object[][] {},new String[] {"ID", "Name", "Kapital", "Kredit", "Geld\u00E4nderung [R]"})
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
		
		txtStartkapital = new JTextField();
		txtStartkapital.setText("250000");
		txtStartkapital.setBounds(104, 8, 59, 20);
		contentPane.add(txtStartkapital);
		txtStartkapital.setColumns(10);
		
		JCheckBox checkStartkapitalKredit = new JCheckBox("Startkapital ist Kredit");
		checkStartkapitalKredit.setSelected(true);
		checkStartkapitalKredit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		checkStartkapitalKredit.setBounds(6, 83, 140, 23);
		contentPane.add(checkStartkapitalKredit);
		
		JLabel lblZinssatz = new JLabel("Kreditzinssatz");
		lblZinssatz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZinssatz.setBounds(9, 62, 98, 14);
		contentPane.add(lblZinssatz);
		
		txtKreditzinssatz = new JTextField();
		txtKreditzinssatz.setText("9");
		txtKreditzinssatz.setColumns(10);
		txtKreditzinssatz.setBounds(104, 59, 59, 20);
		contentPane.add(txtKreditzinssatz);
		
		JLabel lblMaximalerKreditIn = new JLabel("Maximaler Kredit");
		lblMaximalerKreditIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMaximalerKreditIn.setBounds(10, 36, 112, 14);
		contentPane.add(lblMaximalerKreditIn);
		
		JLabel label = new JLabel("\u20AC");
		label.setBounds(167, 11, 16, 14);
		contentPane.add(label);
		
		txtMaximalKredit = new JTextField();
		txtMaximalKredit.setText("500000");
		txtMaximalKredit.setColumns(10);
		txtMaximalKredit.setBounds(104, 33, 59, 20);
		contentPane.add(txtMaximalKredit);
		
		JLabel label_1 = new JLabel("\u20AC");
		label_1.setBounds(167, 36, 16, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("%");
		label_2.setBounds(167, 62, 16, 14);
		contentPane.add(label_2);
		
		txtMaximaleSpieler = new JTextField();
		txtMaximaleSpieler.setText("5");
		txtMaximaleSpieler.setColumns(10);
		txtMaximaleSpieler.setBounds(104, 124, 59, 20);
		contentPane.add(txtMaximaleSpieler);
		
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
							int sKapital = Integer.parseInt(txtStartkapital.getText());
							int mKredit = Integer.parseInt(txtMaximalKredit.getText());
							float zSatz = Float.parseFloat(txtKreditzinssatz.getText());
							boolean startKapitalKredit = checkStartkapitalKredit.isSelected();
							int maxSpieler = Integer.parseInt(txtMaximaleSpieler.getText());
							runServer(sKapital, mKredit, zSatz, startKapitalKredit, maxSpieler);
						} catch (IOException e) {
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
			},
			new String[] {
				"ID", "Name", "Kapital", "Kredit", "Geld\u00E4nderung [R]"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		JButton btnNaechsteRunde = new JButton("N\u00E4chste Runde");
		btnNaechsteRunde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,spieler.get(0).Name);
			}
		});
		btnNaechsteRunde.setEnabled(false);
		btnNaechsteRunde.setBounds(6, 186, 157, 23);
		contentPane.add(btnNaechsteRunde);
	}
}
