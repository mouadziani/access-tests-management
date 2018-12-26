package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import GUI.FramAffectationNoteOrale;
import GUI.FramCandidatsPassOrale;
import GUI.FramCandidatsPasseEcrite;
import GUI.FramCandidatsPreselectione;
import GUI.FramGestionJuries;
import GUI.FramGestionProfs;
import GUI.FramImportCandidats;
import GUI.FramMain;
import GUI.FramResultats;
import GUI.FrameImportNoteEcritCandidat;
import GUI.Login;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		FramResultats main = new FramResultats();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		main.setLocation(dim.width/2-main.getSize().width/2, dim.height/2-main.getSize().height/2);
		main.setVisible(true);
	}

}
