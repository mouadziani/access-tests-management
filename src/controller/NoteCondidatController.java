package controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import helpers.SingletonConnection;
import model.Candidat;

public class NoteCondidatController {
	
	public static LinkedList<Candidat> importNoteEcriteCandidats(File file) throws Exception {
	    LinkedList<Candidat> candidats = new LinkedList<>();
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook wb = new HSSFWorkbook(fs);
	    HSSFRow row = null;
	    HSSFCell cell = null;
	    int totalRows;
	    int totalCells;
	    HSSFSheet sheet = wb.getSheetAt(0);
        totalRows = sheet.getPhysicalNumberOfRows();
        if(totalRows>=1)
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        else
            return null;

        for(int j = 1; j < totalRows; j++) {
            row = sheet.getRow(j);
            String numCurrentCandidat = String.valueOf(row.getCell(0).getNumericCellValue());
            double noteCurrentCandidat = Double.parseDouble(row.getCell(1).getStringCellValue());
            if(setNoteEcritToCandidat(numCurrentCandidat, noteCurrentCandidat).getNum() != null) {
            	candidats.add(setNoteEcritToCandidat(numCurrentCandidat, noteCurrentCandidat));
            }
        }
        
        return candidats;
	}
	
	public static Candidat setNoteEcritToCandidat(String numCandidat, Double noteCandidat) throws SQLException {
		Candidat candidat = new Candidat();
		String stmt = "SELECT * FROM candidats WHERE num = ?";
		PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(1, numCandidat);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			candidat = new Candidat(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(7), rs.getString(9), rs.getDouble(10));
			candidat.setNote_test_ecrit(noteCandidat);
		}
		
		return candidat;
	}
	
	public static void updateNoteCandidats(HashMap<String, Double> candidats) throws SQLException {
		PreparedStatement ps = null;
		for(String numCandidat: candidats.keySet()) {
			String stmt = "UPDATE candidats SET note_test_ecrit  = ? WHERE num = ?";
			ps = SingletonConnection.getConnection().prepareStatement(stmt);
			ps.setString(2, numCandidat);
			ps.setDouble(1, candidats.get(numCandidat));
			ps.executeUpdate();
		}
		ps.close();
	}
	
	public static void setNoteOraleToCandidat(String numCandidat, Double noteCandidat) throws SQLException {
		PreparedStatement ps = null;
		String stmt = "UPDATE candidats SET note_test_orale  = ? WHERE num = ?";
		ps = SingletonConnection.getConnection().prepareStatement(stmt);
		ps.setString(2, numCandidat);
		ps.setDouble(1, noteCandidat);
		int result = ps.executeUpdate();
		System.out.println(result);
		ps.close();
	}
	
}
