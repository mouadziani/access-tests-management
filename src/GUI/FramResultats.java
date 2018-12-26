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
import model.Candidat;

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
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class FramResultats extends JFrame {
	private JTable tbl_candidatsAdmis;
	private JTable tbl_candidatsListAtt;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramResultats() throws Exception {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 735);
		getContentPane().setLayout(null);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier", "Note test ecrit"};
	    DefaultTableModel tblCandidatAdmisModel = new DefaultTableModel(colMedHdr, 0);
		
		String[] colMedHdr2 = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier", "Note test ecrit"};
	    DefaultTableModel tblCandidatAttModel = new DefaultTableModel(colMedHdr2, 0);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste principale des admis", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 874, 337);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 854, 257);
		panel.add(scrollPane);
		
		tbl_candidatsAdmis = new JTable(tblCandidatAdmisModel);
		scrollPane.setViewportView(tbl_candidatsAdmis);
		
		JButton button = new JButton("Exporter en PDF");
		button.setBounds(712, 296, 152, 30);
		panel.add(button);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste d'attent", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 359, 874, 337);
		getContentPane().add(panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 23, 854, 262);
		panel_1.add(scrollPane_1);
		tbl_candidatsAdmis = new JTable(tblCandidatAttModel);
		scrollPane_1.setViewportView(tbl_candidatsAdmis);
		
		JButton button_1 = new JButton("Exporter en PDF");
		button_1.setBounds(712, 296, 152, 30);
		panel_1.add(button_1);

		// Initialise table candidats admis		
		LinkedList<Candidat> candidatsAdmis = CandidatController.getResultCandidats().get(1);
		for(Candidat candidat: candidatsAdmis) {
			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_orale()};         
			tblCandidatAdmisModel.addRow(data);
		}
		
		// Initialise table liste d'att
		LinkedList<Candidat> candidatsListAtt = CandidatController.getResultCandidats().get(2);
		for(Candidat candidat: candidatsListAtt) {
			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_orale()};         
			tblCandidatAttModel.addRow(data);
		}
	}
}



