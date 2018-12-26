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
import controller.JuryController;
import controller.ProfController;
import model.Candidat;
import model.Jury;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FramGestionJuries extends JFrame {
	private JTable tbl_juries;
	private JTable tbl_profs;
	private JTable tbl_profs_per_jury;
	private JTextField txt_nom;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramGestionJuries() throws Exception {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 529);
		getContentPane().setLayout(null);
		
		String[] colMedHdr = {"#", "Jury"};
	    DefaultTableModel tblJuriesModel = new DefaultTableModel(colMedHdr, 0);

	    String[] colProfsHdr = {"#", "Nom"};
	    DefaultTableModel tblProfsModel = new DefaultTableModel(colProfsHdr, 0);
	    
	    String[] colProfsPerJuryHdr = {"#", "Nom"};
	    DefaultTableModel tblProfsPerJuryModel = new DefaultTableModel(colProfsPerJuryHdr, 0);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Juries", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 284, 436);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txt_nom = new JTextField();
		txt_nom.setBounds(10, 23, 148, 26);
		panel.add(txt_nom);
		txt_nom.setColumns(10);
		
		JButton btn_ajouter = new JButton("Ajouter");
		btn_ajouter.setBounds(168, 23, 104, 26);
		panel.add(btn_ajouter);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 262, 365);
		panel.add(scrollPane);
		tbl_juries = new JTable(tblJuriesModel);
		scrollPane.setViewportView(tbl_juries);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Professerurs par juries", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(304, 11, 580, 436);
		getContentPane().add(panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 60, 235, 365);
		tbl_profs_per_jury = new JTable(tblProfsPerJuryModel);
		scrollPane_1.setViewportView(tbl_profs_per_jury);
		panel_1.add(scrollPane_1);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(335, 60, 235, 365);
		panel_1.add(scrollPane_2);
		tbl_profs = new JTable(tblProfsModel);
		scrollPane_2.setViewportView(tbl_profs);
		
		JButton btn_move_left = new JButton("<<");
		btn_move_left.setBounds(255, 230, 70, 26);
		panel_1.add(btn_move_left);
		
		JButton btn_move_right = new JButton(">>");
		btn_move_right.setBounds(255, 267, 70, 26);
		panel_1.add(btn_move_right);
		
		JLabel lblNewLabel = new JLabel("Liste des professeurs pour la jury selectionnee");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 24, 235, 26);
		panel_1.add(lblNewLabel);
		
		JLabel lblListDesProfesseurs = new JLabel("List des professeurs");
		lblListDesProfesseurs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblListDesProfesseurs.setBounds(335, 23, 102, 26);
		panel_1.add(lblListDesProfesseurs);
		
		JButton btnEnregistrerLesModifications = new JButton("Enregistrer les modifications");
		btnEnregistrerLesModifications.setBounds(666, 452, 218, 37);
		getContentPane().add(btnEnregistrerLesModifications);
		
		// Initisalise tables
		LinkedList<Jury> juries = JuryController.getAllJuries();
		for(Jury jury: juries) {
			Object[] data = {jury.getId(), jury.getNom()};         
			tblJuriesModel.addRow(data);
		}
		
		LinkedList<Professeur> profs = ProfController.getProfsHasNoJury();
		for(Professeur prof: profs) {
			Object[] data = {prof.getId(), prof.getNom() + " " + prof.getPrenom()};         
			tblProfsModel.addRow(data);
		}
		
		// On selected row in profs_per_jury table
		tbl_juries.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					int idJury = Integer.parseInt(tbl_juries.getValueAt(tbl_juries.getSelectedRow(), 0).toString());
					LinkedList<Professeur> profsJuries;
					profsJuries = ProfController.getProfsJury(idJury);
					tblProfsPerJuryModel.setRowCount(0);
					for(Professeur prof: profsJuries) {
						Object[] data = {prof.getId(), prof.getNom()};         
						tblProfsPerJuryModel.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	    });
		
		/* === Actions ===*/
		btn_move_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int idProf = Integer.parseInt(tbl_profs.getModel().getValueAt(tbl_profs.getSelectedRow(), 0).toString());
					int idJury = Integer.parseInt(tbl_juries.getModel().getValueAt(tbl_juries.getSelectedRow(), 0).toString());
					ProfController.setProfToJury(idProf, idJury);
					// Reload table prof of juries
					LinkedList<Professeur> profsJuries;
					profsJuries = ProfController.getProfsJury(idJury);
					tblProfsPerJuryModel.setRowCount(0);
					for(Professeur prof: profsJuries) {
						Object[] data = {prof.getId(), prof.getNom()};         
						tblProfsPerJuryModel.addRow(data);
					}
					// Reload table profs has no jury
					LinkedList<Professeur> profs = ProfController.getProfsHasNoJury();
					tblProfsModel.setRowCount(0);
					for(Professeur prof: profs) {
						Object[] data = {prof.getId(), prof.getNom() + " " + prof.getPrenom()};         
						tblProfsModel.addRow(data);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Vuillez selectionneé le prof ainsi que la jury !");
				}
			}
		});
		
		btn_move_right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idProf = Integer.parseInt(tbl_profs_per_jury.getModel().getValueAt(tbl_profs_per_jury.getSelectedRow(), 0).toString());
					int idJury = Integer.parseInt(tbl_juries.getModel().getValueAt(tbl_juries.getSelectedRow(), 0).toString());
					ProfController.removeProfFromJury(idProf);
					// Reload table prof of juries
					LinkedList<Professeur> profsJuries;
					profsJuries = ProfController.getProfsJury(idJury);
					tblProfsPerJuryModel.setRowCount(0);
					for(Professeur prof: profsJuries) {
						Object[] data = {prof.getId(), prof.getNom()};         
						tblProfsPerJuryModel.addRow(data);
					}
					// Reload table profs has no jury
					LinkedList<Professeur> profs = ProfController.getProfsHasNoJury();
					tblProfsModel.setRowCount(0);
					for(Professeur prof: profs) {
						Object[] data = {prof.getId(), prof.getNom() + " " + prof.getPrenom()};         
						tblProfsModel.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nomJury = txt_nom.getText();
					Jury addedjury = new Jury(nomJury);
					JuryController.addJury(addedjury);
					// Reload table of juries
					LinkedList<Jury> juries = JuryController.getAllJuries();
					for(Jury jury: juries) {
						Object[] data = {jury.getId(), jury.getNom()};         
						tblJuriesModel.addRow(data);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		btnEnregistrerLesModifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JuryController.setJuriesToCandidats();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}
}




