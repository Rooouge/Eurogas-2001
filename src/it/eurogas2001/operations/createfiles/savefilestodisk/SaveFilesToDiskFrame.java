package it.eurogas2001.operations.createfiles.savefilestodisk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import it.eurogas2001.Application;
import it.eurogas2001.GenericFrameListener;
import it.eurogas2001.Utils;
import it.eurogas2001.components.fileinformation.InformationClass;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createtxtdocs.TxtGPL;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createtxtdocs.TxtMetano;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createworddocs.WordGPL;
import it.eurogas2001.operations.createfiles.savefilestodisk.filetypes.createworddocs.WordMetano;

public class SaveFilesToDiskFrame {
	
	private Application application;
	
	private String pathForSave = null;
	private String type;
	private String date;
	private String month;	//Scadenza
	
	private JFrame frame;
	
	private List<InformationClass> rowsFromFile;
	
	
	public SaveFilesToDiskFrame(Application application, List<JFrame> previousFrames, List<InformationClass> list, String month, String type, String date) {
		this.application = application;
		this.type = type;
		this.date = date;
		this.month = month;
		this.rowsFromFile = list;
		
		if(new File(Utils.genericPath).exists())
			pathForSave = Utils.genericPath + month + "/";
		else
			pathForSave = "./File Word/" + month + "/";
		
		JComboBox<String> outputFormatsBox = new JComboBox<>(Utils.availableFormats);
		outputFormatsBox.setSelectedIndex(1);
		
		TitledBorder formatBorder = new TitledBorder("Formato");
		formatBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel formatPanel = new JPanel(new BorderLayout());
		formatPanel.setBorder(formatBorder);
		formatPanel.add(outputFormatsBox, BorderLayout.CENTER);
		
		JButton nextButton = new JButton("Prosegui");
		nextButton.addActionListener((e) -> {
			previousFrames.add(frame);
			saveFiles(outputFormatsBox.getSelectedIndex(), previousFrames);
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(nextButton);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(formatPanel, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		frame = new JFrame();
		frame.setTitle("Eurogas 2001 - Save Files");
		frame.setIconImage(application.icon);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new GenericFrameListener(application, frame));
		
		frame.setContentPane(panel);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.pack();
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);		
	}
	
	public void saveFiles(int outputFormatId, List<JFrame> previousFrames) {
		int createdFiles = 0;
		try {
			for(InformationClass row : rowsFromFile) {
				switch (this.type) {
				case "METANO":
					switch (outputFormatId) {
					case 0:
						new TxtMetano(pathForSave, row);
						createdFiles++;
						break;
					case 1:
						new WordMetano(pathForSave, row, date, month);
						createdFiles++;
						break;
					default:
						break;
					}
					break;
				case "GPL":
					switch (outputFormatId) {
					case 0:
						new TxtGPL(pathForSave, row);
						createdFiles++;
						break;
					case 1:
						new WordGPL(pathForSave, row, date, month);
						createdFiles++;
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
			
			showDialog(createdFiles, previousFrames);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	private void showDialog(int createdFiles, List<JFrame> previousFrames) {
		JOptionPane.showMessageDialog(
				application.getAllFrames().get(application.getAllFrames().size()-1), 
				("Creati " + createdFiles + " file"), 
				"Eurogas 2001 - Save File", 
				JOptionPane.INFORMATION_MESSAGE
		);
		
		JFrame f = previousFrames.get(previousFrames.size()-1);
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}
	
}
