package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import helpers.SingletonConnection;
import model.Candidat;
import model.Professeur;

public class ProfController {

	public static LinkedList<Professeur> getAllProfs() throws SQLException {
		LinkedList<Professeur> professeurs = new LinkedList<>();
		String stmt = "SELECT * FROM professeurs ORDER BY nom";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Professeur professeur = new Professeur(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			professeurs.add(professeur);
		} 
		return professeurs;
	}
	
	public static Professeur getProfesseur(Integer id) throws SQLException {
		Professeur professeur = null;
		String stmt = "SELECT * FROM professeurs WHERE id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			professeur = new Professeur(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
		} 
		return professeur;
	}
	
	public static boolean addProfesseur(Professeur professeur) throws Exception {
		String stmt = "INSERT INTO professeurs (nom, prenom, email, password) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(1, professeur.getNom());
		ps.setString(2, professeur.getPrenom());
		ps.setString(3, professeur.getEmail());
		ps.setString(4, professeur.getPassword());
		Integer count = ps.executeUpdate();
		while(count != 0) {
			return true;
		} 
		return false;
	}
	
	public static boolean updateProfesseur(Professeur professeur) throws Exception {
		String stmt = "UPDATE professeurs SET nom = ?, ?, prenom = ?,  email = ?, password = ? WHERE id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		Integer count = ps.executeUpdate(stmt);
		while(count != 0) {
			return true;
		}
		
		return false;
	}
	
	public static boolean deleteProfesseur(Integer id) throws Exception {
		String stmt = "DELETE FROM professeurs WHERE id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		Integer count = ps.executeUpdate(stmt);
		while(count != 0) {
			return true;
		}
		return false;
	}
	
	public static LinkedList<Professeur> getProfsHasNoJury() throws SQLException {
		LinkedList<Professeur> professeurs = new LinkedList<>();
		String stmt = "SELECT * FROM professeurs WHERE jury_id IS NULL";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Professeur professeur = new Professeur(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			professeur.setId(rs.getInt(1));
			professeurs.add(professeur);
		} 
		return professeurs;
	}
	
	public static LinkedList<Professeur> getProfsHasJury() throws SQLException {
		LinkedList<Professeur> professeurs = new LinkedList<>();
		String stmt = "SELECT * FROM professeurs WHERE jury_id IS NOT NULL";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Professeur professeur = new Professeur(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			professeurs.add(professeur);
		} 
		return professeurs;
	}
	
	public static LinkedList<Professeur> getProfsJury(int idJury) throws SQLException {
		LinkedList<Professeur> professeurs = new LinkedList<>();
		String stmt = "SELECT * FROM professeurs WHERE jury_id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setInt(1, idJury);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Professeur professeur = new Professeur(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			professeur.setId(rs.getInt(1));
			professeurs.add(professeur);
		} 
		return professeurs;
	}
	
	public static void setProfToJury(int idProf, int idJury) throws SQLException {
		String stmt;
		PreparedStatement ps = null;
		stmt = "UPDATE professeurs SET jury_id = ? WHERE id = ?";
		ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setInt(1, idJury);
		ps.setInt(2, idProf);
		ps.executeUpdate();
		ps.close();
	}
	
	public static void removeProfFromJury(int idProf) throws SQLException {
		String stmt;
		PreparedStatement ps = null;
		stmt = "UPDATE professeurs SET jury_id = NULL WHERE id = ?";
		ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setInt(1, idProf);
		ps.executeUpdate();
		ps.close();
	}
	
}
