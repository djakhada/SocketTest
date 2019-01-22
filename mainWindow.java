import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainWindow extends JFrame {

	private JPanel contentPane;
	ServerWindow server = new ServerWindow();
	ClientWindow client = new ClientWindow();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 122);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnServerHosten = new JButton("Server hosten");
		btnServerHosten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server.setVisible(true);
				btnServerHosten.setEnabled(false);
			}
		});
		btnServerHosten.setBounds(10, 45, 120, 23);
		contentPane.add(btnServerHosten);
		
		JButton btnClientStarten = new JButton("Client starten");
		btnClientStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.setVisible(true);
			}
		});
		btnClientStarten.setBounds(10, 11, 120, 23);
		contentPane.add(btnClientStarten);
		
		JLabel lblImNetzwerkNach = new JLabel("Im Netzwerk nach Spielen suchen...");
		lblImNetzwerkNach.setBounds(154, 15, 213, 14);
		contentPane.add(lblImNetzwerkNach);
	}
}
