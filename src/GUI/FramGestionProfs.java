package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.EncryptedDocumentException;

import com.itextpdf.text.DocumentException;

import controller.CandidatController;
import controller.ProfController;
import model.Candidat;
import model.Professeur;

import java.awt.Window.Type;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FramGestionProfs extends JFrame {
	private JTable tbl_profs;
	private JTextField txt_nom;
	private JTextField txt_prenom;
	private JTextField txt_email;
	private JTextField txt_password;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramGestionProfs() throws Exception {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 178, 874, 332);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"#", "Nom", "Prenom", "Email", "Password"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_profs = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_profs);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informations du professeur", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 618, 156);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(24, 46, 60, 17);
		panel.add(lblNewLabel);
		
		txt_nom = new JTextField();
		txt_nom.setBounds(94, 43, 184, 26);
		panel.add(txt_nom);
		txt_nom.setColumns(10);
		
		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrenom.setBounds(24, 94, 60, 17);
		panel.add(lblPrenom);
		
		txt_prenom = new JTextField();
		txt_prenom.setColumns(10);
		txt_prenom.setBounds(94, 91, 184, 26);
		panel.add(txt_prenom);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(288, 46, 88, 17);
		panel.add(lblEmail);
		
		txt_email = new JTextField();
		txt_email.setColumns(10);
		txt_email.setBounds(386, 43, 201, 26);
		panel.add(txt_email);
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
		txt_password.setBounds(386, 91, 201, 26);
		panel.add(txt_password);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMotDePasse.setBounds(288, 94, 88, 17);
		panel.add(lblMotDePasse);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(638, 11, 246, 156);
		getContentPane().add(panel_1);
		
		JButton btn_ajouter = new JButton("Ajouter");
		btn_ajouter.setBounds(20, 27, 204, 32);
		panel_1.add(btn_ajouter);
		
		JButton btn_modifier = new JButton("Modifier");
		btn_modifier.setBounds(20, 70, 204, 32);
		panel_1.add(btn_modifier);
		
		JButton btn_supprimer = new JButton("Supprimer");
		btn_supprimer.setBounds(20, 113, 204, 32);
		panel_1.add(btn_supprimer);

		// Initialise table candidats
		LinkedList<Professeur> professeurs = ProfController.getAllProfs();
		for(Professeur professeur: professeurs) {
			Object[] data = {professeur.getNom(), professeur.getPrenom(), professeur.getEmail(), professeur.getPassword()};         
			tblCandidatModel.addRow(data);
		}
		
		/* === Actions ===*/
		// Clicked on ajouter
		btn_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Professeur professeur = new Professeur(txt_nom.getText(), txt_prenom.getText(), txt_email.getText(), txt_password.getText());
					boolean result = ProfController.addProfesseur(professeur);
					if(result) {
						professeurs.add(professeur);
						Object[] data = {professeur.getNom(), professeur.getPrenom(), professeur.getEmail(), professeur.getPassword()};         
						tblCandidatModel.addRow(data);
						txt_nom.setText("");
						txt_prenom.setText("");
						txt_email.setText("");
						txt_password.setText("");
						JOptionPane.showMessageDialog(null, "Le professeur a ete ajouter avec succès !");
					} else {
						JOptionPane.showMessageDialog(null, "Erreurs !!");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					//JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
		// Clicked on modifier
		btn_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// Clicked on supprimer
		btn_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}



