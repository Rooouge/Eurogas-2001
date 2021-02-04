package it.eurogas2001.components.views;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

import it.eurogas2001.Utils;

public class ExcelView extends FileView {
	@Override
	public String getTypeDescription(File f) {
		String extension = Utils.getExtension(f);
		String type = null;
		
		if(extension.equals(Utils.XLS))
			type = "Excel (.xls)";
		
		return type;
	}
	
	@Override
	public Icon getIcon(File f) {
		Icon icon = null;
		String extension = Utils.getExtension(f);
		
		if(extension == null)
			return null;
			
		if(extension.equals(Utils.XLS))
			icon = new ImageIcon(getClass().getResource("/icons/excel_icon.png"));
		
		return icon;
	}
}
