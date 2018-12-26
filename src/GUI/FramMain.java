package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class FramMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FramMain() {
		setTitle("Tableau de bord");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 515);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Importer les candidats preselectionn\u00E9e");
		btnNewButton.setBounds(41, 89, 418, 37);
		contentPane.add(btnNewButton);
		
		JButton btnConsulterLesCandidats = new JButton("Consulter les candidats pr\u00E9selectionn\u00E9e");
		btnConsulterLesCandidats.setBounds(41, 137, 418, 37);
		contentPane.add(btnConsulterLesCandidats);
		
		JButton btnConsulterLesCandidats_1 = new JButton("Consulter les candidats pour les accepter \u00E0 passer l'ecrit");
		btnConsulterLesCandidats_1.setBounds(41, 185, 418, 37);
		contentPane.add(btnConsulterLesCandidats_1);
		
		JButton btnImporterLesNotes = new JButton("Importer les notes  du test ecrit des candidats");
		btnImporterLesNotes.setBounds(41, 233, 418, 37);
		contentPane.add(btnImporterLesNotes);
		
		JButton btnAffectationNotesOrale = new JButton("Affectation notes orale");
		btnAffectationNotesOrale.setBounds(41, 380, 418, 37);
		contentPane.add(btnAffectationNotesOrale);
		
		JLabel lblVeuillezChoisirUne = new JLabel("Veuillez choisir une action");
		lblVeuillezChoisirUne.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblVeuillezChoisirUne.setBounds(109, 35, 297, 29);
		contentPane.add(lblVeuillezChoisirUne);
		
		JButton btnGestionDesMembres = new JButton("Gestion des membres de jury");
		btnGestionDesMembres.setBounds(41, 329, 418, 37);
		contentPane.add(btnGestionDesMembres);
		
		JButton btnGestionDesProfesseurs = new JButton("Gestion des professeurs");
		btnGestionDesProfesseurs.setBounds(41, 281, 418, 37);
		contentPane.add(btnGestionDesProfesseurs);
		
		JButton btnResultatsFinal = new JButton("Resultats final");
		btnResultatsFinal.setBounds(41, 428, 418, 37);
		contentPane.add(btnResultatsFinal);
		
		/* === Actions === */
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramImportCandidats fram = new FramImportCandidats();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
				fram.setVisible(true);
			}
		});
		
		btnConsulterLesCandidats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FramCandidatsPreselectione fram;
				try {
					fram = new FramCandidatsPreselectione();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnConsulterLesCandidats_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramCandidatsPasseEcrite fram;
				try {
					fram = new FramCandidatsPasseEcrite();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnImporterLesNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameImportNoteEcritCandidat fram;
				try {
					fram = new FrameImportNoteEcritCandidat();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnAffectationNotesOrale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramAffectationNoteOrale fram;
				try {
					fram = new FramAffectationNoteOrale();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnGestionDesMembres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramGestionJuries fram;
				try {
					fram = new FramGestionJuries();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnGestionDesProfesseurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramGestionProfs fram;
				try {
					fram = new FramGestionProfs();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnResultatsFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FramResultats fram;
				try {
					fram = new FramResultats();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					fram.setLocation(dim.width/2-fram.getSize().width/2, dim.height/2-fram.getSize().height/2);
					fram.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
