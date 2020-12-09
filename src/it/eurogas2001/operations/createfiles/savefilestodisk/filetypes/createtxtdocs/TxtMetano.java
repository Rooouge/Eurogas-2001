package it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createtxtdocs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import it.eurogas2001.Utils;
import it.eurogas2001.components.fileinformation.InformationClass;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.EG2001File;

public class TxtMetano extends EG2001File {
	
	public TxtMetano(String pathForSave, InformationClass row) {
		File dir = new File(pathForSave);
		if(!dir.exists()) dir.mkdirs();
		
		row.name = Utils.removeInvalidCharacters(row.name);
		
		file = new File(pathForSave + "[METANO - " + row.index + "]" + row.name + ".txt");
		
		int i = 1;
		while(file.exists()) {
			i++;
			file = new File(pathForSave + "[METANO - " + row.index + "]" + row.name + "(" + i + ").txt");
		}
		
		try (
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		){
			String s = 
					"[METANO - " + row.index + "] " 
					+ row.name + ", "
					+ row.address + ", "
					+ row.town + ", "
					+ row.province + ", "
					+ row.cap;
			
			writer.write(s);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
