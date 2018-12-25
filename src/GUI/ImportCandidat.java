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
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class ImportCandidat extends JFrame {
	private JTable tbl_candidats;

	/**
	 * Create the frame.
	 */
	public ImportCandidat() {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		
		JButton btn_importer = new JButton("Importer les condidats");
		btn_importer.setBounds(659, 21, 225, 36);
		getContentPane().setLayout(null);
		getContentPane().add(btn_importer);
		
		JButton btn_enregistrer = new JButton("Enregistrer");
		btn_enregistrer.setBounds(703, 424, 181, 36);
		getContentPane().add(btn_enregistrer);
		
		JButton btn_imprimer = new JButton("Imprimer");
		btn_imprimer.setBounds(512, 424, 181, 36);
		getContentPane().add(btn_imprimer);
		
		JButton btn_supprime = new JButton("Supprimer l'element selectionn\u00E9");
		btn_supprime.setBounds(10, 424, 213, 36);
		getContentPane().add(btn_supprime);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 874, 340);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_candidats = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_candidats);
		
		JLabel lblNewLabel = new JLabel("Importation des candidats");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 23, 318, 25);
		getContentPane().add(lblNewLabel);
		
		/* ==== Actions ==== */
		// Clicked on importer
		btn_importer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = "";
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int x = jf.showOpenDialog(btn_importer);
		        if (x == JFileChooser.APPROVE_OPTION) {
		        	File file = jf.getSelectedFile();
		        	try {
						ArrayList<Candidat> candidats = CandidatController.importCandidats(file);
						
						for(Candidat candidat: candidats) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier()};         
							tblCandidatModel.addRow(data);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			}
		});
		
		// Clicked on supprimer
		btn_supprime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int answer = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer ?");
		        if(answer == 0) {
		        	// remove selected condidats from table
		        	DefaultTableModel model = (DefaultTableModel) tbl_candidats.getModel();
		        	int[] rows = tbl_candidats.getSelectedRows();
		        	for(int i=0; i< rows.length; i++) {
		        		model.removeRow(rows[i]-i);
		        	}
		        	JOptionPane.showMessageDialog(null, "Les condidats selectinne est bien supprimé !");
		        }
			}
		});
		
		// Clicked on enregistrer
		btn_enregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					ArrayList<Candidat> candidats = new ArrayList<>();
					for (int count = 0; count < tbl_candidats.getRowCount(); count++) {
						Candidat candidat = new Candidat(tbl_candidats.getValueAt(count, 0).toString(), 
														 tbl_candidats.getValueAt(count, 1).toString(), 
														 tbl_candidats.getValueAt(count, 2).toString(), 
														 tbl_candidats.getValueAt(count, 4).toString(), 
														 tbl_candidats.getValueAt(count, 3).toString(), 
														 tbl_candidats.getValueAt(count, 6).toString(), 
														 tbl_candidats.getValueAt(count, 5).toString(), 
														 tbl_candidats.getValueAt(count, 7).toString(), 
														 Double.parseDouble(tbl_candidats.getValueAt(count, 8).toString()));
						candidats.add(candidat);
					}
					
					CandidatController.saveCandidats(candidats);
					JOptionPane.showMessageDialog(null, "Les condidats est bien enregistrés !");
				
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
	}
}
