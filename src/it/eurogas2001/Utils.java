package it.eurogas2001;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import it.eurogas2001.components.fileinformation.InformationClass;

public class Utils {
	
	public static final String numeroSanProspero = "0521 1681226";
	public static final String numeroMilazzo = "0521 1771410";

	public static final String genericPath = "./working_folder/File Word/";
	
	public static final float FONT_SIZE = 12f;
	
	public static final Color EG2001_CYAN = new Color(0, 100, 160);
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	public static final String xls = "xls";
	
	public static Graphics g = null;
	
	public static final String[] types = {"METANO", "GPL"};
	public static final String[] months = {
		"GENNAIO",
		"FEBBRAIO",
		"MARZO",
		"APRILE",
		"MAGGIO",
		"GIUGNO",
		"LUGLIO",
		"AGOSTO",
		"SETTEMBRE",
		"OTTOBRE",
		"NOVEMBRE",
		"DICEMBRE"
	};
	
	public static enum Months {
		GENNAIO ("GENNAIO", 1),
		FEBBRAIO ("FEBBRAIO", 2),
		MARZO ("MARZO", 3),
		APRILE ("APRILE", 4),
		MAGGIO ("MAGGIO", 5),
		GIUGNO ("GIUGNO", 6),
		LUGLIO ("LUGLIO", 7),
		AGOSTO ("AGOSTO", 8),
		SETTEMBRE ("SETTEMBRE", 9),
		OTTOBRE ("OTTOBRE", 10),
		NOVEMBRE ("NOVEMBRE", 11),
		DICEMBRE ("DICEMBRE", 12);
		
		public String name;
		public int index;
		
		Months(String name, int index) {
			this.name = name;
			this.index = index;
		}
	}
	
	public static final String[] searchTypesBox = {
		"Nome", 
		"Indirizzo", 
		"Città"
	};
	
	public static final String[] availableFormats = {
		"File .txt (File di testo)",
		"File .doc (File Word)"
	};
	
	/*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    public static String convertPickerMonthToString(String dateFromPicker) {
    	int firstIndex = dateFromPicker.indexOf("-") + 1;
    	int lastIndex = firstIndex + 3;
    	
    	switch (dateFromPicker.substring(firstIndex, lastIndex)) {
		case "gen":
			return "01";
		case "feb":
			return "02";
		case "mar":
			return "03";
		case "apr":
			return "04";
		case "mag":
			return "05";
		case "giu":
			return "06";
		case "lug":
			return "07";
		case "ago":
			return "08";
		case "set":
			return "09";
		case "ott":
			return "10";
		case "nov":
			return "11";
		case "dic":
			return "12";
		default:
			return null;
		}
    }

	public static int adaptFontSizeToText(String text, int startingFontSize) {
		float cmFont12 = 0.22f;
		float cmFont = 0.22f - (0.02f * (12 - startingFontSize));
		
		float maxWidth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567".length() * cmFont12;
		float stringWidth = text.length() * cmFont;
		
		int finalFontSize = startingFontSize;
		
		
		while(stringWidth > maxWidth) {
			cmFont-=0.02f;
			finalFontSize--;
			
			stringWidth = text.length() * cmFont;
		}
		
		return finalFontSize;
	}
	
	public static File createFileFromExcelRowData(InformationClass row, String pathForSave, String type) {
		return new File(pathForSave + "[" + type.toUpperCase() + " - " + row.index + "]" + row.name + ".doc");
	}
	
	public static String removeInvalidCharacters(String s) {
		s = s.replaceAll("[\\\\/:*?\"<>|]", "");
		return s;
	}
	
}
