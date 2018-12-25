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

public class FramMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FramMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Importer les candidats preselectionn\u00E9e");
		btnNewButton.setBounds(45, 38, 330, 37);
		contentPane.add(btnNewButton);
		
		JButton btnConsulterLesCandidats = new JButton("Consulter les candidats preselectionn\u00E9e");
		btnConsulterLesCandidats.setBounds(45, 86, 330, 37);
		contentPane.add(btnConsulterLesCandidats);
		
		JButton btnConsulterLesCandidats_1 = new JButton("Consulter les candidats pour accepter pour passer l'ecrit");
		btnConsulterLesCandidats_1.setBounds(45, 134, 330, 37);
		contentPane.add(btnConsulterLesCandidats_1);
		
		JButton btnImporterLesNotes = new JButton("Importer les notes  du test ecrit des candiats");
		btnImporterLesNotes.setBounds(45, 182, 330, 37);
		contentPane.add(btnImporterLesNotes);
		
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
	}
}
