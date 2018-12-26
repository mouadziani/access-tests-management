package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.xml.soap.Node;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.ConditionFilterData;

import com.itextpdf.text.pdf.PdfWriter;

import helpers.SingletonConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
			candidat.setId(rs.getInt(1));
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
		double pourcentageLMD = 0;
		double pourcentageNonLMD = 0;
		double pourcentageISTA = 0;
		double pourcentageCP = 0;
		while(rs.next()) {
			pourcentageLMD = rs.getDouble("porc_ecrit_lmd");
			pourcentageNonLMD = rs.getDouble("porc_ecrit_non_lmd");
			pourcentageISTA = rs.getDouble("porc_ecrit_ista");
			pourcentageCP = rs.getDouble("porc_ecrit_cp");
		} 
		LinkedList<Candidat> candidatsLMD = new LinkedList<>();
		LinkedList<Candidat> candidatsNonLMD = new LinkedList<>();
		LinkedList<Candidat> candidatsIsta = new LinkedList<>();
		LinkedList<Candidat> candidatsCP = new LinkedList<>();
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

		Collections.sort(candidatsLMD);
		Collections.sort(candidatsNonLMD);
		Collections.sort(candidatsIsta);
		Collections.sort(candidatsCP);
		
		int nbLmd = (int)((nbCandidats * pourcentageLMD) / 100);
		int nbNonLmd = (int)((nbCandidats * pourcentageNonLMD) / 100);
		int nbIsta = (int)((nbCandidats * pourcentageISTA) / 100);
		int nbCP = (int)((nbCandidats * pourcentageCP) / 100);
		
		for(Candidat candidat: getFirstNCandidats(candidatsLMD, nbLmd)) {
			candidats.add(candidat);
		}
		
		for(Candidat candidat: getFirstNCandidats(candidatsNonLMD, nbNonLmd)) {
			candidats.add(candidat);
		}
		
		for(Candidat candidat: getFirstNCandidats(candidatsIsta, nbIsta)) {
			candidats.add(candidat);
		}
		
		for(Candidat candidat: getFirstNCandidats(candidatsCP, nbCP)) {
			candidats.add(candidat);
		}
		
		return candidats;
	}
	
	public static LinkedList<Candidat> getFirstNCandidats(LinkedList<Candidat> candidats, int nbr) {
		 LinkedList<Candidat> firstNCandidats = new LinkedList<Candidat>();
		 nbr = (candidats.size() < nbr) ? candidats.size() : nbr;
		 for(int i = 0; i < nbr; i++) {
			 firstNCandidats.add(candidats.get(i));
		 }
		 return firstNCandidats;
	 }
	 
	public static void savePassedTestCandidats(ArrayList<String> numCandidates) throws SQLException {
		 // Initialise pass_ecrite for all candidate with false
		 String stmt = "UPDATE candidats SET passe_ecrit  = 0";
		 PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		 ps.executeUpdate();
		 ps.close();
		 
		 // Set true of pass_ecrite just for candidates collection 				 
		 for(String numCandidat: numCandidates) {
			 stmt = "UPDATE candidats SET passe_ecrit = 1 WHERE num = ?";
			 ps = SingletonConnection.getConnection().prepareStatement(stmt);
			 ps.setString(1, numCandidat);
			 ps.executeUpdate();
		 }
		 
		 ps.close();
	}
	 
	public static void exportCandidats(LinkedList<Candidat> candidats, String path) throws FileNotFoundException, DocumentException {
	  Document document = new Document();
	  PdfPTable table = new PdfPTable(9);
	  // Generate header
	  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	  table.addCell("N° Candidat");
	  table.addCell("Nom");
	  table.addCell("Prenom");
	  table.addCell("Ville");
	  table.addCell("Etablissement");
	  table.addCell("Type de diplome");
	  table.addCell("Diplome");
	  table.addCell("Specialité");
	  table.addCell("Note dossier");
	  table.setHeaderRows(1);
	  PdfPCell[] cells = table.getRow(0).getCells(); 
	  for (int j = 0; j < cells.length; j++) {
	     cells[j].setBackgroundColor(BaseColor.GRAY);
	  }
	  
	  for (Candidat candidat: candidats){
		  table.addCell(candidat.getNum());
		  table.addCell(candidat.getNom());
		  table.addCell(candidat.getPrenom());
		  table.addCell(candidat.getVille());
		  table.addCell(candidat.getEtablissement());
		  table.addCell(candidat.getType_diplome());
		  table.addCell(candidat.getDiplome());
		  table.addCell(candidat.getSpecialite());
		  table.addCell(String.valueOf(candidat.getNote_dossier()));
	  }
	  
	  PdfWriter.getInstance(document, new FileOutputStream(path));
	  document.open();
	  document.add(table);
	  document.close();
	}
	
	public static LinkedList<Candidat> getCandidatsCanPassingOrale() throws SQLException {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT * FROM candidats WHERE passe_orale = 1";
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
			candidat.setId(rs.getInt(1));
			candidats.add(candidat);
		} 
		return candidats;
	}
	
	public static LinkedList<Candidat> getCandidatsPassedOrale() throws SQLException {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT * FROM candidats WHERE passe_orale = 1 AND note_test_orale IS NOT NULL";
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
			candidat.setId(rs.getInt(1));
			candidats.add(candidat);
		} 
		return candidats;
	}
	
	public static LinkedList<Candidat> getCandidatsCanPassingEcrit() throws SQLException {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT * FROM candidats WHERE passe_ecrit = 1";
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
			candidat.setId(rs.getInt(1));
			candidats.add(candidat);
		} 
		return candidats;
	}
	
	public static LinkedList<Candidat> getCandidatsPassedEcrit() throws SQLException {
		LinkedList<Candidat> candidats = new LinkedList<>();
		String stmt = "SELECT * FROM candidats WHERE passe_ecrit = 1 AND note_test_ecrit IS NOT NULL";
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
			candidat.setId(rs.getInt(1));
			candidats.add(candidat);
		} 
		return candidats;
	}

}
