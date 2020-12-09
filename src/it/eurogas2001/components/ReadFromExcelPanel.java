package it.eurogas2001.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import it.eurogas2001.Application;
import it.eurogas2001.Utils;
import it.eurogas2001.components.views.ExcelView;
import it.eurogas2001.exceptions.FolderNotCreatedException;
import it.eurogas2001.exceptions.FolderNotExistsException;
import it.eurogas2001.exceptions.GenericException;
import it.eurogas2001.exceptions.NoFileSelectedException;
import it.eurogas2001.operations.createfiles.TableFromExcelFrame;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


@SuppressWarnings("serial")
public class ReadFromExcelPanel extends JPanel {

	private Application application;
	
	private JLabel currentFileLabel;
	
	private JFileChooser fileChooser;
	
	private JComboBox<String> monthsBox;
	private JComboBox<String> typesBox;
	
	private JDatePickerImpl datePicker;
	
	
	public ReadFromExcelPanel(Application application) {
		this.application = application;
		JLabel titleLabel = new JLabel(" > Leggi dati da file Excel");
		titleLabel.setForeground(Utils.EG2001_CYAN);
		titleLabel.setFont(titleLabel.getFont().deriveFont(Utils.FONT_SIZE));
		titleLabel.setBorder(new LineBorder(Color.GRAY));
			
		BorderLayout centerPanelLayout = new BorderLayout();
		centerPanelLayout.setVgap(10);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(centerPanelLayout);
		centerPanel.add(createFileChooserPanel(), BorderLayout.NORTH);
		centerPanel.add(createInformationsPanel(), BorderLayout.CENTER);

		JButton nextButton = new JButton("Prosegui");
		nextButton.addActionListener((e) -> {
			try {
				File file;
				try {
					file = fileChooser.getSelectedFile();
					if(file == null)
						throw new NullPointerException();
				} catch(NullPointerException nullPointerException) {
					throw new NoFileSelectedException();
				}
				
				
				String date = setDate();
				if(date == null)
					throw new GenericException(date, "is null");
				
				String selectedMonth = (String) monthsBox.getSelectedItem() + " 2021";
				
				String selectedType = (String) typesBox.getSelectedItem();
				if(selectedType == null)
					throw new GenericException(selectedType, "is null");
				
//				System.out.println("Date  : " + date);
//				System.out.println("Month : " + selectedMonth);
//				System.out.println("Type  : " + selectedType);
				
				new TableFromExcelFrame(application, file, selectedMonth, selectedType, date, null);
			} catch (GenericException exception) {
				JOptionPane.showMessageDialog(
					application.getLastFrame(), 
					exception.getMessage(), 
					"Error: " + exception.getName(), 
					JOptionPane.ERROR_MESSAGE
				);
			} catch (Throwable finalException) {
				finalException.printStackTrace();
			}
		});
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(nextButton);
		
		this.setLayout(new BorderLayout());
		this.setBackground(null);
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private JPanel createInformationsPanel() {
		typesBox = new JComboBox<>(Utils.types);
		
		TitledBorder typeBorder = new TitledBorder("Tipo");
		typeBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel typePanel = new JPanel();
		typePanel.setBorder(typeBorder);
		typePanel.add(typesBox);
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(year, month, day);
		model.setSelected(true);
		JDatePanelImpl datePanelImpl = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanelImpl);
		datePicker.getJFormattedTextField().addCaretListener((e) -> {
			setSelectedMonth();
		});
		
		
		TitledBorder dateBorder = new TitledBorder("Data");
		dateBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel datePanel = new JPanel();
		datePanel.setBorder(dateBorder);
		datePanel.add(datePicker);
		
		monthsBox = new JComboBox<>(Utils.months);
		setSelectedMonth();
		
		TitledBorder expiryBorder = new TitledBorder("Scadenza");
		expiryBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel monthsPanel = new JPanel();
		monthsPanel.setBorder(expiryBorder);
		monthsPanel.add(monthsBox);
		
		
		TitledBorder openFolderBorder = new TitledBorder("Apri Cartella");
		openFolderBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JButton openInExplorerButton = new JButton(new ImageIcon(getClass().getResource("/icons/open_folder.png")));
		openInExplorerButton.setFocusable(false);
		openInExplorerButton.addActionListener((e) -> {
			String path;
			if(new File(Utils.genericPath).exists())
				path = Utils.genericPath + "/";
			else
				path = "./File Word/";
			
			try {
				File dir;
				try {
					dir = new File(path);
				} catch (NullPointerException nullPointerException) {
					throw new FolderNotExistsException();
				}
				
				if(!dir.exists()) 
					dir.mkdirs();
				
				if(!dir.isDirectory())
					throw new FolderNotCreatedException();
				
				Desktop.getDesktop().open(dir);
			} catch (GenericException exception) {
				JOptionPane.showMessageDialog(
					application.getLastFrame(), 
					exception.getMessage(), 
					"Error: " + exception.getName(), 
					JOptionPane.ERROR_MESSAGE
				);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		});
		
		JPanel openButtonPanel = new JPanel(new FlowLayout());
		openButtonPanel.setBorder(openFolderBorder);
		openButtonPanel.add(openInExplorerButton);
		
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(typePanel);
		panel.add(datePanel);
		panel.add(monthsPanel);
		panel.add(openButtonPanel);
		
		return panel;
	}

	private void setSelectedMonth() {
		try {
			int nextMonth = Integer.parseInt(Utils.convertPickerMonthToString(datePicker.getJFormattedTextField().getText()));
			if(nextMonth == 12)
				nextMonth = 0;
			
			monthsBox.setSelectedIndex(nextMonth);
		} catch (IndexOutOfBoundsException i) {
			
		} catch (NumberFormatException n) {
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private JPanel createFileChooserPanel() {
		JButton openButton = new JButton("Apri File");
		openButton.setIcon(new ImageIcon(getClass().getResource("/icons/excel_icon.png")));
		openButton.addActionListener((e) -> {
			fileChooser = createFileChooser();
			
			int returnValue = fileChooser.showOpenDialog(this);
			
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				String fileName = fileChooser.getSelectedFile().getName();
				
				currentFileLabel.setText(" File selezionato: " + fileName);
				
				for(String type : Utils.types) {
					if(type.equals(fileName.substring(0, type.length())))
						typesBox.setSelectedItem(type);
				}
			}
		});
		
		currentFileLabel = new JLabel(" File selezionato: ");
		
		TitledBorder border = new TitledBorder("File");
		border.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(border);
		panel.add(openButton, BorderLayout.WEST);
		panel.add(currentFileLabel, BorderLayout.CENTER);
		
		return panel;		
	}	
	
	private JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("./working_folder/File Excel"));
		fileChooser.setFileFilter(new ExcelFilter());
		fileChooser.setFileView(new ExcelView());
		
		return fileChooser;
	}
	
	private String setDate() {
		String dateFromPicker = datePicker.getJFormattedTextField().getText();
		String day;
		
		try {
			day = "" + Integer.parseInt(dateFromPicker.substring(0, 2));
		} catch (NumberFormatException e) {
			day = "0" + Integer.parseInt(dateFromPicker.substring(0, 1));
		}
			
		return day + "/" + Utils.convertPickerMonthToString(dateFromPicker) + "/" + dateFromPicker.substring(dateFromPicker.length()-4);
	}
	
	
	private class ExcelFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if(f.isDirectory())
				return true;
			
			String extension = Utils.getExtension(f);
			if(extension.equals(null))
				return false;
			
			if(extension.equals(Utils.xls))
				return true;
			
			return false;
		}

		@Override
		public String getDescription() {
			return "Excel (.xls)";
		}
		
	}
}
