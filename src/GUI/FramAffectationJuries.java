package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class FramAffectationJuries extends JFrame {

	private JPanel contentPane;
	private JTextField txt_nom_jury;
	private JTable tbl_juries;
	/**
	 * Create the frame.
	 */
	public FramAffectationJuries() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(214, 254, -201, -64);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 73, 293, 377);
		contentPane.add(scrollPane_1);
		
		JButton btn_ajouter = new JButton("Ajouter");
		btn_ajouter.setBounds(203, 36, 100, 26);
		contentPane.add(btn_ajouter);
		
		txt_nom_jury = new JTextField();
		txt_nom_jury.setBounds(10, 36, 183, 26);
		contentPane.add(txt_nom_jury);
		txt_nom_jury.setColumns(10);
		
		JLabel lblJury = new JLabel("Les juries");
		lblJury.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblJury.setHorizontalAlignment(SwingConstants.LEFT);
		lblJury.setBounds(10, 11, 75, 14);
		contentPane.add(lblJury);
		
		String[] colMedHdr = {"#", "Nom"};
	    DefaultTableModel tblJurieModel = new DefaultTableModel(colMedHdr, 0);
		
		tbl_juries = new JTable(tblJurieModel);
		
		scrollPane.setViewportView(tbl_juries);
	}
}
