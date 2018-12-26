package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import helpers.SingletonConnection;
import model.Candidat;
import model.Jury;
import model.Professeur;

public class JuryController {
	
	public static boolean addJury(Jury jury) throws Exception {
		String stmt = "INSERT INTO juries (nom) VALUES (?)";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(1, jury.getNom());
		Integer count = ps.executeUpdate();
		while(count != 0) {
			return true;
		} 
		return false;
	}
	
	public static boolean updateJury(Jury jury) throws Exception {
		String stmt = "UPDATE juries SET nom = ? WHERE id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(1, jury.getNom());
		ps.setInt(1, jury.getId());
		Integer count = ps.executeUpdate();
		while(count != 0) {
			return true;
		}
		return false;
	}
	
	public static boolean deleteJury(Integer id) throws Exception {
		String stmt = "DELETE FROM juries WHERE id = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setInt(1, id);
		Integer count = ps.executeUpdate();
		while(count != 0) {
			return true;
		}
		return false;
	}
	
	public static void setJuriesToCandidats() throws SQLException {
		LinkedList<Candidat> candidats = CandidatController.getCandidatsCanPassingOrale();
		LinkedList<Jury> juries = getAllJuries();
		int j = 0;
		for (int i = 0; i < juries.size(); i++) {
			LinkedList<Candidat> partCandidats = new LinkedList<>();
			for(int d = j; d < candidats.size() / juries.size(); d++) {
				partCandidats.add(candidats.get(d));
				j++;
			}
			
			setJuryToCandidats(partCandidats, juries.get(i).getId());
		}
	}
	
	public static void setJuryToCandidats(LinkedList<Candidat> candidats, int idJury) throws SQLException {
		String stmt;
		PreparedStatement ps = null;
		for(Candidat candidat: candidats) {
			stmt = "UPDATE candidats SET jury_id = ? WHERE id = ?";
			ps = SingletonConnection.getConnection().prepareStatement(stmt);
			ps.setInt(1, idJury);
			ps.setInt(2, candidat.getId());
			int result = ps.executeUpdate();
			System.out.println(result + " -------- ");
		}
	}
	
	public static LinkedList<Jury> getAllJuries() throws SQLException {
		LinkedList<Jury> juries = new LinkedList<>();
		String stmt = "SELECT * FROM Juries";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		System.out.println("her");
		while(rs.next()) {
			Jury jury = new Jury(rs.getString(2));
			jury.setId(rs.getInt(1));
			juries.add(jury);
			System.out.println(rs.getInt(1));
		}
		ps.close();	
		return juries;
	}

}
