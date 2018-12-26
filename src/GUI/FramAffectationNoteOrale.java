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
import controller.NoteCondidatController;
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

public class FramAffectationNoteOrale extends JFrame {
	private JTable tbl_candidats;
	private JTextField txt_note;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FramAffectationNoteOrale() throws Exception {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 478);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 104, 812, 332);
		getContentPane().add(scrollPane);
		
		String[] colMedHdr = {"N\u00B0  Candidat", "Nom", "Prenom", "Type diplome", "Diplome", "Sp\u00E9cialit\u00E9", "Note dossier", "Note Test ecrit", "Note Test orale"};
	    DefaultTableModel tblCandidatModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_candidats = new JTable(tblCandidatModel);
		
		scrollPane.setViewportView(tbl_candidats);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Affectation du note", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 812, 82);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Candidat");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 34, 60, 17);
		panel.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Note orale");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(291, 34, 88, 17);
		panel.add(lblEmail);
		
		txt_note = new JTextField();
		txt_note.setColumns(10);
		txt_note.setBounds(389, 31, 201, 30);
		panel.add(txt_note);
		
		JComboBox cmb_candidat = new JComboBox();
		cmb_candidat.setBounds(80, 31, 201, 30);
		panel.add(cmb_candidat);
		
		JButton btn_affecter = new JButton("Affecter");
		btn_affecter.setBounds(614, 29, 182, 30);
		panel.add(btn_affecter);

		// Initialise table candidats
		LinkedList<Candidat> candidats = CandidatController.getCandidatsPassedOrale();
		for(Candidat candidat: candidats) {
			Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_ecrit(), candidat.getNote_test_orale()};         
			tblCandidatModel.addRow(data);
		}
		// Initialise num candidats
		ArrayList<String> numCandidats = CandidatController.getNumCandidats();
		cmb_candidat.setModel(new DefaultComboBoxModel(numCandidats.toArray()));
		
		/* === Actions === */
		btn_affecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(txt_note.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Vuilez saisie la note !");
					} else {
						double note = Double.parseDouble(txt_note.getText());
						String numCandidat = (String) cmb_candidat.getSelectedItem();
						NoteCondidatController.setNoteOraleToCandidat(numCandidat, note);
						// Reload table candiats
						LinkedList<Candidat> passiedOralCandidats = CandidatController.getCandidatsPassedOrale();
						tblCandidatModel.setRowCount(0);
						for(Candidat candidat: passiedOralCandidats) {
							Object[] data = {candidat.getNum(), candidat.getNom(), candidat.getPrenom(), candidat.getType_diplome(), candidat.getDiplome(), candidat.getSpecialite(), candidat.getNote_dossier(), candidat.getNote_test_ecrit(), candidat.getNote_test_orale()};         
							tblCandidatModel.addRow(data);
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}
}




