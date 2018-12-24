package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import GUI.Login;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Login main = new Login();
		main.setVisible(true);
	}

}
