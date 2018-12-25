package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.security.auth.login.LoginContext;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import controller.AuthController;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_email;
	private JTextField txt_password;

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 227);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setVerticalAlignment(SwingConstants.TOP);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(43, 37, 99, 27);
		contentPane.add(lblEmail);
		
		txt_email = new JTextField();
		txt_email.setBounds(152, 35, 166, 26);
		contentPane.add(txt_email);
		txt_email.setColumns(10);
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
		txt_password.setBounds(152, 75, 166, 26);
		contentPane.add(txt_password);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMotDePasse.setVerticalAlignment(SwingConstants.TOP);
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMotDePasse.setBounds(43, 77, 89, 27);
		contentPane.add(lblMotDePasse);
		
		JButton btn_login = new JButton("Connecter");
		btn_login.setBounds(43, 129, 275, 32);
		contentPane.add(btn_login);
		/* 
		 * Clicked on login button
		 * */
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login login = new Login();
				try {
					boolean result = AuthController.login(txt_email.getText(), txt_password.getText());
					if(result) {
						
						JOptionPane.showMessageDialog(login, "test");
					} else {
						JOptionPane.showMessageDialog(login, "test");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
	}
}
