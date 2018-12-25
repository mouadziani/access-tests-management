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
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ImportCandidat extends JFrame {
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ImportCandidat() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		setTitle("Importation des candidats");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 798, 468);
						
		JButton btn_importer = new JButton("Importer les condidats");
		btn_importer.setBounds(601, 21, 181, 36);
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
					} catch (EncryptedDocumentException | IOException e) {
						e.printStackTrace();
					}
		        }
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(btn_importer);
		
		JButton btn_enregistrer = new JButton("Enregistrer");
		btn_enregistrer.setBounds(601, 392, 181, 36);
		getContentPane().add(btn_enregistrer);
		
		JButton btn_imprimer = new JButton("Imprimer");
		btn_imprimer.setBounds(410, 392, 181, 36);
		getContentPane().add(btn_imprimer);
		
		JButton btn_supprime = new JButton("Supprimer l'element selectionn\u00E9");
		btn_supprime.setBounds(10, 392, 213, 36);
		getContentPane().add(btn_supprime);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 772, 308);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
