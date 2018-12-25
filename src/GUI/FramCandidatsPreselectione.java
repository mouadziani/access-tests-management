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

import controller.CandidatController;
import model.Candidat;

import java.awt.Window.Type;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
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

public class FramCandidatsPreselectione extends JFrame {
	private JTable tbl_candidats;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramCandidatsPreselectione() throws Exception {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 874, 401);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_candidats = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_candidats);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filterer les condidats", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("Button.foreground")));
		panel.setBounds(10, 11, 874, 87);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Diplomes");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(26, 40, 58, 14);
		panel.add(lblNewLabel);
		
		JLabel lblSpecialites = new JLabel("Sp\u00E9cialit\u00E9s");
		lblSpecialites.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSpecialites.setBounds(260, 40, 63, 14);
		panel.add(lblSpecialites);
		
		JLabel lblTypeDiplome = new JLabel("Type diplome");
		lblTypeDiplome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTypeDiplome.setBounds(552, 40, 78, 14);
		panel.add(lblTypeDiplome);
		
		JComboBox cmb_diplomes = new JComboBox();
		cmb_diplomes.setBounds(87, 33, 145, 30);
		panel.add(cmb_diplomes);
		
		JComboBox cmb_specialites = new JComboBox();
		cmb_specialites.setBounds(333, 33, 200, 30);
		panel.add(cmb_specialites);
		
		JComboBox cmb_diplome_types = new JComboBox();
		cmb_diplome_types.setBounds(640, 33, 103, 30);
		panel.add(cmb_diplome_types);
		
		JButton btn_filtrer = new JButton("Filtrer");
		btn_filtrer.setBounds(761, 33, 103, 30);
		panel.add(btn_filtrer);

		// Initialise table candidats
		LinkedList<Candidat> candidats = CandidatController.getAllCandidats();
		for(Candidat candidat: candidats) {
			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
			tblCandidatModel.addRow(data);
		}
		
		// Initialise diplomes
		ArrayList<String> diplomes = CandidatController.getDiplomes();
		cmb_diplomes.setModel(new DefaultComboBoxModel(diplomes.toArray()));

		// Initialise specialites
		ArrayList<String> specialites = CandidatController.getSpecialites();
		cmb_specialites.setModel(new DefaultComboBoxModel(specialites.toArray()));
		
		// Initialise types diplome
		ArrayList<String> diplomeTypes = CandidatController.getDiplomeTypes();
		cmb_diplome_types.setModel(new DefaultComboBoxModel(diplomeTypes.toArray()));
		
		/* ==== Actions ==== */
		btn_filtrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typeDiplome = cmb_diplome_types.getSelectedItem().toString();
				String diplome = cmb_diplomes.getSelectedItem().toString();
				String specialite = cmb_specialites.getSelectedItem().toString();
				
				tblCandidatModel.setRowCount(0);
				for(Candidat candidat: candidats) {
					if(cmb_diplome_types.getSelectedIndex() != 0 && cmb_diplomes.getSelectedIndex() != 0 && cmb_specialites.getSelectedIndex() != 0) {
						if(candidat.getType_diplome().equals(typeDiplome) && candidat.getDiplome().equals(diplome) && candidat.getSpecialite().equals(specialite)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_diplome_types.getSelectedIndex() != 0 && cmb_diplomes.getSelectedIndex() != 0) {
						if(candidat.getType_diplome().equals(typeDiplome) && candidat.getDiplome().equals(diplome)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_diplome_types.getSelectedIndex() != 0 && cmb_specialites.getSelectedIndex() != 0) {
						if(candidat.getType_diplome().equals(typeDiplome) && candidat.getSpecialite().equals(specialite)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_diplomes.getSelectedIndex() != 0 && cmb_specialites.getSelectedIndex() != 0) {
						if(candidat.getDiplome().equals(diplome) && candidat.getSpecialite().equals(specialite)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_diplome_types.getSelectedIndex() != 0 && (cmb_diplomes.getSelectedIndex() == 0 && cmb_specialites.getSelectedIndex() == 0)) {
						if(candidat.getType_diplome().equals(typeDiplome)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_diplomes.getSelectedIndex() != 0 && (cmb_diplome_types.getSelectedIndex() == 0 && cmb_specialites.getSelectedIndex() == 0)) {
						if(candidat.getDiplome().equals(diplome)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else if(cmb_specialites.getSelectedIndex() != 0 && (cmb_diplome_types.getSelectedIndex() == 0 && cmb_diplomes.getSelectedIndex() == 0)) {
						if(candidat.getDiplome().equals(specialite)) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} else {
						Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
						tblCandidatModel.addRow(data);
					}
				}
			}
		});
		
	}
}

