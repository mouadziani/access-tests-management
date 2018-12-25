package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.EncryptedDocumentException;

import controller.CandidatController;
import controller.NoteCondidatController;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class FrameImportNoteEcritCandidat extends JFrame {
	private JTable tbl_candidats;

	/**
	 * Create the frame.
	 */
	public FrameImportNoteEcritCandidat() {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 874, 395);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier", "Note Test ecrit"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_candidats = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_candidats);
		
		JButton btn_importer = new JButton("Importer les notes");
		btn_importer.setBounds(659, 21, 225, 36);
		getContentPane().setLayout(null);
		getContentPane().add(btn_importer);
		
		JButton btn_enregistrer = new JButton("Enregistrer");
		btn_enregistrer.setBounds(703, 474, 181, 36);
		getContentPane().add(btn_enregistrer);
		
		JButton btn_imprimer = new JButton("Imprimer");
		btn_imprimer.setBounds(512, 474, 181, 36);
		getContentPane().add(btn_imprimer);
		
		JLabel lblNewLabel = new JLabel("Importation les notes de test ecrit des candidats");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 23, 467, 25);
		getContentPane().add(lblNewLabel);
		
		// Clicked on importer
		btn_importer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String path = "";
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int x = jf.showOpenDialog(btn_importer);
		        if (x == JFileChooser.APPROVE_OPTION) {
		        	File file = jf.getSelectedFile();
		        	try {
		        		LinkedList<Candidat> candidats = NoteCondidatController.importNoteEcriteCandidats(file);
		        		for(Candidat candidat: candidats) {
		        			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_ecrit()};         
		        			tblCandidatModel.addRow(data);
		        		}
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			}
		});
		
		// Clicked on enregistrer
		btn_enregistrer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				HashMap<String, Double> candidats = new HashMap<String, Double>();
				for (int i = 0; i < tbl_candidats.getRowCount(); i++) {
					System.out.println(tbl_candidats.getValueAt(i, 0).toString() + " - " + tbl_candidats.getValueAt(i, 9).toString());
					candidats.put(tbl_candidats.getValueAt(i, 0).toString(), Double.parseDouble(tbl_candidats.getValueAt(i, 9).toString()));
				}
				
				try {
					NoteCondidatController.updateNoteCandidats(candidats);
					JOptionPane.showMessageDialog(null, "Les notes condidats est bien modifié !");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
	}
}
