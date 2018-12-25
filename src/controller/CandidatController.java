package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import helpers.SingletonConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Candidat;

public class CandidatController {
	
	public static ArrayList<Candidat> importCandidats(File file) throws FileNotFoundException, IOException, SQLException {
	    ArrayList<Candidat> candidats = new ArrayList<Candidat>();
	    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook wb = new HSSFWorkbook(fs);
	    HSSFRow row = null;
	    HSSFCell cell = null;
	    int totalRows;
	    int totalCells;
	    String[] diplomeTypes = {"LMD", "Non LMD", "ISTA", "CPGE"}; 
	    for(int i = 0; i < 4; i++) {
	        HSSFSheet sheet = wb.getSheetAt(i);
	        totalRows = sheet.getPhysicalNumberOfRows();
	        if(totalRows>=1)
	            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
	        else
	            return null;

	        for(int j = 1; j < totalRows; j++) {
	            row = sheet.getRow(j);
	            Candidat candidat = new Candidat(
	            		Double.toString(row.getCell(0).getNumericCellValue()), 
	            		row.getCell(1).getStringCellValue(), 
	            		row.getCell(2).getStringCellValue(), 
	            		row.getCell(3).getStringCellValue(), 
	            		row.getCell(4).getStringCellValue(), 
	            		row.getCell(5).getStringCellValue(), 
	            		diplomeTypes[i], 
	            		row.getCell(6).getStringCellValue(), 
	            		(double)row.getCell(7).getNumericCellValue());
	            candidats.add(candidat);
	        }
	    }

	    return candidats;
	}
	
	public static void saveCandidats(ArrayList<Candidat> candidats) throws SQLException {
		String stmt = "INSERT INTO `access_tests_managment_db`.`candidats` (`num`, `nom`, `prenom`, `etablissement`, `ville`, `type_diplome`, `diplome`, `specialite`, `note_dossier`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		for(Candidat candidat: candidats) {
			PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
			ps.setString(1, candidat.getNum());
			ps.setString(2, candidat.getNom());
			ps.setString(3, candidat.getPrenom());
			ps.setString(4, candidat.getEtablissement());
			ps.setString(5, candidat.getVille());
			ps.setString(6, candidat.getType_diplome());
			ps.setString(7, candidat.getDiplome());
			ps.setString(8, candidat.getSpecialite());
			ps.setDouble(9, candidat.getNote_dossier());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	public static LinkedList<Candidat> getAllCandidats() throws SQLException {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT * FROM candidats ORDER BY nom";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Candidat candidat = new Candidat(rs.getString(2), 
											 rs.getString(3), 
											 rs.getString(4), 
											 rs.getString(5), 
											 rs.getString(6), 
											 rs.getString(8), 
											 rs.getString(7), 
											 rs.getString(9), 
											 rs.getDouble(10));
			candidats.add(candidat);
		} 
		return candidats;
	}
	
	public static ArrayList<String> getDiplomes() throws SQLException {
		ArrayList<String> diplomes = new ArrayList<>();
		String stmt = "SELECT DISTINCT(diplome) FROM candidats";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		diplomes.add("<< Tous >>");
		while(rs.next()) {
			diplomes.add(rs.getString(1));
		} 
		return diplomes;
	}
	
	public static ArrayList<String> getSpecialites() throws SQLException {
		ArrayList<String> specialites = new ArrayList<>();
		String stmt = "SELECT DISTINCT(specialite) FROM candidats";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		specialites.add("<< Tous >>");
		while(rs.next()) {
			specialites.add(rs.getString(1));
		} 
		return specialites;
	}
	
	public static ArrayList<String> getDiplomeTypes() throws SQLException {
		ArrayList<String> diplomeTypes = new ArrayList<>();
		String stmt = "SELECT DISTINCT(type_diplome) FROM candidats";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		diplomeTypes.add("<< Tous >>");
		while(rs.next()) {
			diplomeTypes.add(rs.getString(1));
		} 
		return diplomeTypes;
	}
	
	public static LinkedList<Candidat> getCandidatsTestEcriteParNbr(int nbCandidats) throws Exception {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT porc_ecrit_non_lmd, porc_ecrit_lmd, porc_ecrit_ista, porc_ecrit_cp FROM parametrages LIMIT 1";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			double pourcentageLMD = rs.getDouble("porc_ecrit_lmd");
			double pourcentageNonLMD = rs.getDouble("porc_ecrit_non_lmd");
			double pourcentageISTA = rs.getDouble("porc_ecrit_ista");
			double pourcentageCP = rs.getDouble("porc_ecrit_cp");
		} 
		LinkedList candidatsLMD = new LinkedList<>();
		LinkedList candidatsNonLMD = new LinkedList<>();
		LinkedList candidatsIsta = new LinkedList<>();
		LinkedList candidatsCP = new LinkedList<>();
		for(Candidat candidat: getAllCandidats()) {
			if(candidat.getType_diplome().equals("LMD") ) {
				candidatsLMD.add(candidat);
			} else if (candidat.getType_diplome().equals("Non LMD")) {
				candidatsNonLMD.add(candidat);
			} else if (candidat.getType_diplome().equals("ISTA")) {
				candidatsIsta.add(candidat);
			} else if (candidat.getType_diplome().equals("CPGE")) {
				candidatsCP.add(candidat);
			}
		}
		return candidats;
	}

}
