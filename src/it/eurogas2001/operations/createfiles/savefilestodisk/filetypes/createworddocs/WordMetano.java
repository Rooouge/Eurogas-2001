package it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createworddocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import it.eurogas2001.Utils;
import it.eurogas2001.components.fileinformation.InformationClass;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.EG2001File;

public class WordMetano extends EG2001File {
	
	public WordMetano(String pathForSave, InformationClass row, String date, String month) throws Throwable {
		work(pathForSave, row, date, month);
	}

	public void work(String pathForSave, InformationClass row, String date, String month) throws Throwable{
		try (
			XWPFDocument doc = new XWPFDocument();
			){
			String s;
			XWPFParagraph p = doc.createParagraph();
			XWPFRun run = p.createRun();
			
			String logoPath = new File(".").getAbsolutePath() + "/resources/images/logo 240x60.png";
			
			FileInputStream fis = new FileInputStream(logoPath);
			
			/*
			 * Adding logo
			 */
			run.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, logoPath, Units.toEMU(160), Units.toEMU(40));
	
			/*
			 * Credentials
			 */
			p = doc.createParagraph();
			run = p.createRun();
			s = "                                                                                              Spett.le/Egr. Sig/ra";
			run.setFontSize(CreateWordUtils.FONT_SIZE_12);
			run.setText(s);
			run.addCarriageReturn();
						
			run = p.createRun();
			s = "                                                                                              ";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_12);
			run.setText(s);
			run = p.createRun();
			s = row.name;
			run.setBold(true);
			run.setFontSize(Utils.adaptFontSizeToText(s, CreateWordUtils.FONT_SIZE_12));
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "                                                                                              ";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_12);
			run.setText(s);
			run = p.createRun();
			s = row.address;
			run.setBold(true);
			run.setFontSize(Utils.adaptFontSizeToText(s, CreateWordUtils.FONT_SIZE_12));
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "                                                                                              ";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_12);
			run.setText(s);
			run = p.createRun();
			s = row.cap + " " + row.town + " (" + row.province + ")";
			run.setBold(true);
			run.setFontSize(Utils.adaptFontSizeToText(s, CreateWordUtils.FONT_SIZE_12));
			run.setText(s);
			run.addCarriageReturn();
			
			/*
			 * Main text
			 */
			p = doc.createParagraph();
			run = p.createRun();
			s = "Parma, " + date;
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			s = "Oggetto: ";
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "revisione periodica bombole gas metano";
			run = p.createRun();
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			
			p = doc.createParagraph();
			run = p.createRun();
			s = "Gentile Cliente,";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "Eurogas 2001 s.r.l. pensa di farLe cosa gradita nel ricordarLe che sono in scadenza nel mese di ";
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run = p.createRun();
			s = month;
			run.setBold(true);
			run.setColor("0000ff");
			run.setText(s);
			run = p.createRun();
			s = " le bombole del gas metano installate sulla Sua vettura.";
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "Se lo riterrà opportuno potrà recarsi presso le nostre officine a disposizione per il cambio delle stesse bombole e per la eventuale revisione periodica dell'auto, oppure può contattarci direttamente presso le nostre sedi o al numero verde ";
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "800 977 974";
			run = p.createRun();
			run.setFontSize(CreateWordUtils.FONT_SIZE_12);
			run.setColor("00ff00");
			run.setBold(true);
			run.setItalic(true);
			run.setText(s);
			s = " per informazioni o prenotazioni.";
			run = p.createRun();
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			
			run.addBreak();
			
			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.CENTER);
			run = p.createRun();
			s = "Nel caso il veicolo non fosse più di sua proprietà voglia ritenere nulla la presente.";
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setBold(true);
			run.setText(s);
			
			run.addBreak();
			
			p = doc.createParagraph();
			run = p.createRun();
			s = "Restiamo a Sua disposizione per ogni eventuale od ulteriore chiarimento in merito e con l'occasione porgiamo distinti saluti.";
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			p.setSpacingAfter(1);
			
			run.addBreak();
			
			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.CENTER);
			run = p.createRun();
			s = "email:";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "   info@eurogas2001.it   silvia@eurogas2001.it";
			run = p.createRun();
			run.setColor("0000ff");
			run.setBold(true);
			run.setItalic(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			
			run.addBreak();
			run.addBreak();
			run.addBreak();
			run.addBreak();
			
			/*
			 * Adding 500
			 */
			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.CENTER);
			run = p.createRun();
			
			String car500Path = new File(".").getAbsolutePath() + "/resources/images/500.png";
			
			fis = new FileInputStream(car500Path);
			
			run.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, car500Path, Units.toEMU(100), Units.toEMU(50));
			
			run.addBreak();
			run.addBreak();
			run.addBreak();
			run.addBreak();
			run.addBreak();
			
			/*
			 * Adding EG2001 addresses
			 */
			p = doc.createParagraph();
			run = p.createRun();
			s = "Via Emilio Lepido, 200/A - ";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "Loc. Il Moro";
			run = p.createRun();
			run.setColor("ff0000");
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "                                                                                       Via Milazzo, 42/A";
			run = p.createRun();
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "43122 SAN PROSPERO - PARMA                                                                                                   43125 PARMA";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "Tel. ";
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = Utils.numeroSanProspero;
			run = p.createRun();
			run.setColor("228B22");
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = "                                                                                                                            Tel. ";
			run = p.createRun();
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			s = Utils.numeroMilazzo;
			run.setColor("228B22");
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "(Siamo circa 500 mt. prima del ponte";
			run.setColor("ff0000");
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "sul fiume Enza a sinistra direzione";
			run.setColor("ff0000");
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			run.addCarriageReturn();
			
			run = p.createRun();
			s = "Reggio Emilia)";
			run.setColor("ff0000");
			run.setBold(true);
			run.setFontSize(CreateWordUtils.FONT_SIZE_10);
			run.setText(s);
			
			/*
			 * Saving file
			 */
			File dir = new File(pathForSave);
			if(!dir.exists()) dir.mkdirs();
			
			row.name = Utils.removeInvalidCharacters(row.name);
			
			file = new File(pathForSave + "[METANO] " + row.name + " " + row.index + ".doc");
			
			int i = 1;
			while(file.exists()) {
				i++;
				file = new File(pathForSave + "[METANO] " + row.name + " " + row.index + "(" + i + ").doc");
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			doc.write(fos);
			
			fis.close();
		} catch (Throwable e) {
			throw e;
		}
	}
}
