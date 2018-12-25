package controller;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Candidat;

public class CandidatController {
	
	public static ArrayList<Candidat> importCandidats(File file) throws FileNotFoundException, IOException {
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

}
