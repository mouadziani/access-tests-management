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
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class FramCandidatsPassOrale extends JFrame {
	private JTable tbl_candidats;
	private JTextField txt_nb_candidats;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramCandidatsPassOrale() throws Exception {
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
		scrollPane.setBounds(10, 109, 874, 360);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Ville", "Etablissement", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier", "Note test ecrit"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_candidats = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_candidats);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filterer les condidats", TitledBorder.LEADING, TitledBorder.TOP, null, UIManager.getColor("Button.foreground")));
		panel.setBounds(10, 11, 874, 87);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Saisir le nombre limite des candidats pour pass\u00E9 le test orale");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(44, 40, 329, 14);
		panel.add(lblNewLabel);
		
		JButton btn_filtrer = new JButton("Filtrer");
		btn_filtrer.setBounds(655, 33, 196, 30);
		panel.add(btn_filtrer);
		
		txt_nb_candidats = new JTextField();
		txt_nb_candidats.setBounds(383, 33, 246, 30);
		panel.add(txt_nb_candidats);
		txt_nb_candidats.setColumns(10);
		
		JButton btn_enregister = new JButton("Enregistrer");		
		btn_enregister.setBounds(732, 480, 152, 30);
		getContentPane().add(btn_enregister);
		
		JButton btn_imprimer = new JButton("Exporter en PDF");
		btn_imprimer.setBounds(570, 480, 152, 30);
		getContentPane().add(btn_imprimer);
		
		JButton btn_supprimer = new JButton("Supprim\u00E9 l'element selectionn\u00E9e");
		btn_supprimer.setBounds(10, 480, 204, 30);
		getContentPane().add(btn_supprimer);

		// Initialise table candidats
		LinkedList<Candidat> candidats = CandidatController.getCandidatsPassedEcrit();
		for(Candidat candidat: candidats) {
			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_orale()};         
			tblCandidatModel.addRow(data);
		}
		
		/* === Actions === */	
		// Clicked on filtrer
		btn_filtrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(txt_nb_candidats.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Vuilez remplie le champ pour filteré !");
					} else {
						LinkedList<Candidat> filtredCandidats = CandidatController.getCandidatsTestOraleParNbr(Integer.parseInt(txt_nb_candidats.getText()));
						tblCandidatModel.setRowCount(0);
						for(Candidat candidat: filtredCandidats) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getVille(), candidat.getEtablissement(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_orale()};         
							tblCandidatModel.addRow(data);
						}
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		// Clicked on enregistrer
		btn_enregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ArrayList<String> numCandidates = new ArrayList<>();
					for (int i = 0; i < tbl_candidats.getRowCount(); i++) {
						numCandidates.add(tbl_candidats.getValueAt(i, 0).toString());
						CandidatController.savePassedTestOraleCandidats(numCandidates);
					}
					JOptionPane.showMessageDialog(null, "Les condidats est bien enregistrés !");
				
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
		// Clicked on supprimer
		btn_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		// Clicked on imprimer 
		btn_imprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser dialog = new JFileChooser();
		            int dialogResult = dialog.showSaveDialog(null);
		            if (dialogResult==JFileChooser.APPROVE_OPTION){
		                String filePath = dialog.getSelectedFile().getPath();
		                LinkedList<Candidat> candidats = new LinkedList<>();
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
						CandidatController.exportCandidats(candidats, filePath);
		            }
				} catch (FileNotFoundException | DocumentException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
}



