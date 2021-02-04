package it.eurogas2001.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import it.eurogas2001.Application;
import it.eurogas2001.Utils;
import it.eurogas2001.components.fileinformation.InformationClass;
import it.eurogas2001.exceptions.EmptyFieldException;
import it.eurogas2001.exceptions.FolderNotCreatedException;
import it.eurogas2001.exceptions.GenericException;
import it.eurogas2001.exceptions.InvalidCapException;
import it.eurogas2001.operations.createfiles.TableFromExcelFrame;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

@SuppressWarnings("serial")
public class ManualInsertPanel extends JPanel {

	private Application application;
	
	private List<InformationClass> allAdded;
	private JTextArea allAddedTextArea;
	
	private JTextField nameField;
	private JTextField addressField;
	private JTextField townField;
	private JTextField provinceField;
	private JTextField capField;
	
	private JComboBox<String> monthsBox;
	private JComboBox<String> typesBox;
	
	private JDatePickerImpl datePicker;
	
	
	public ManualInsertPanel(Application application) {
		this.application = application;
		
		JLabel label = new JLabel(" > Inserimento Manuale");
		label.setForeground(Utils.EG2001_CYAN);
		label.setFont(label.getFont().deriveFont(Utils.FONT_SIZE));
		label.setBorder(new LineBorder(Color.GRAY));

		allAdded = new ArrayList<>();
		allAddedTextArea = new JTextArea();
		allAddedTextArea.setEditable(false);
		
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.NORTH);
		this.add(createCenterPanel(), BorderLayout.CENTER);
		this.add(createSouthPanel(), BorderLayout.SOUTH);
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(createLeftPanel());
		panel.add(createRightPanel());
		
		return panel;
	}
	
	private JPanel createLeftPanel() {
		TitledBorder panelBorder = new TitledBorder("Dati");
		panelBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JButton addButton = new JButton("Aggiungi");
		addButton.addActionListener(e -> add() );
		JButton refreshButton = new JButton(new ImageIcon(getClass().getResource("/icons/refresh.png")));
		refreshButton.addActionListener(e -> {
			nameField.setText("");
			addressField.setText("");
			townField.setText("");
			provinceField.setText("");
			capField.setText("");
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(addButton);
		buttonPanel.add(refreshButton);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(panelBorder);
		panel.add(createAddInfoPanel(), BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	public void add() {
		try {
			String name = nameField.getText().toUpperCase();
			String address = addressField.getText().toUpperCase();
			String town = townField.getText().toUpperCase();
			String province = provinceField.getText().toUpperCase();
			int cap = Integer.parseInt(capField.getText());
			
			if(name == null || name.equals(""))
				throw new EmptyFieldException("Nome");
			if(address == null || address.equals(""))
				throw new EmptyFieldException("Indirizzo");
			if(town == null || town.equals(""))
				throw new EmptyFieldException("Città");
			if(province == null || province.equals(""))
				throw new EmptyFieldException("Provincia");
			if(cap < 10000)
				throw new InvalidCapException();
			
			
			allAdded.add(new InformationClass(allAdded.size()+1, name, address, town, province, cap));
			
			updateTextArea();
			
			nameField.setText("");
			addressField.setText("");
			townField.setText("");
			provinceField.setText("");
			capField.setText("");
		} catch(GenericException g) {
			JOptionPane.showMessageDialog(
				application.getLastFrame(), 
				g.getMessage(), 
				g.getName(), 
				JOptionPane.ERROR_MESSAGE
			);
		} catch(NumberFormatException n) {
			InvalidCapException ice = new InvalidCapException();
			JOptionPane.showMessageDialog(
				application.getLastFrame(), 
				ice.getMessage(), 
				ice.getName(), 
				JOptionPane.ERROR_MESSAGE
			);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel createAddInfoPanel() {
		nameField = new JTextField();
		addressField = new JTextField();
		townField = new JTextField();
		provinceField = new JTextField();
		capField = new JTextField();
		
		GridLayout layout = new GridLayout(5, 2);
		layout.setVgap(5);
		
		JPanel panel = new JPanel(layout);
		
		panel.add(new JLabel("Nome : "));
		panel.add(nameField);
		panel.add(new JLabel("Indirizzo : "));
		panel.add(addressField);
		panel.add(new JLabel("Città : "));
		panel.add(townField);
		panel.add(new JLabel("Provincia : "));
		panel.add(provinceField);
		panel.add(new JLabel("Cap : "));
		panel.add(capField);
		
		setTownTextFieldListener();
		
		return panel;
	}

	private JPanel createRightPanel() {
		TitledBorder panelBorder = new TitledBorder("Lista");
		panelBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(panelBorder);
		
		JScrollPane scrollPane = new JScrollPane(allAddedTextArea);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}
	
	private void updateTextArea() {
		if(allAddedTextArea.getText().length() == 0 || allAddedTextArea.getText() == null) {
			for(int i = 0; i < allAdded.size(); i++) {
				if(i == 0)
					allAddedTextArea.setText(allAdded.get(i).index + " - " + allAdded.get(i).name);
				else 
					allAddedTextArea.setText(allAddedTextArea.getText() + "\n" + allAdded.get(i).index + " - " + allAdded.get(i).name);
			}
		}
		else 
			allAddedTextArea.setText(allAddedTextArea.getText() + "\n" + allAdded.get(allAdded.size()-1).index + " - " + allAdded.get(allAdded.size()-1).name);
		
		this.repaint();
	}
	
	private JPanel createSouthPanel() {
		JButton nextButton = new JButton("Prosegui");
		nextButton.addActionListener(e -> {
			try {
				String date = setDate();
				
				new TableFromExcelFrame(application, allAdded, (String) monthsBox.getSelectedItem() + " 2021", (String) typesBox.getSelectedItem(), date);
			} catch (GenericException exception) {
				JOptionPane.showMessageDialog(
					application.getLastFrame(), 
					exception.getMessage(), 
					"Error: " + exception.getName(), 
					JOptionPane.ERROR_MESSAGE
				);
			} catch (Exception finalException) {
				finalException.printStackTrace();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(nextButton);
		
		
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(createInformationsPanel(), BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createInformationsPanel() {
		typesBox = new JComboBox<>(Utils.TYPES);
		
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
		datePicker.getJFormattedTextField().addCaretListener(e -> setSelectedMonth() );
		
		
		TitledBorder dateBorder = new TitledBorder("Data");
		dateBorder.setTitleColor(Utils.EG2001_CYAN);
		
		JPanel datePanel = new JPanel();
		datePanel.setBorder(dateBorder);
		datePanel.add(datePicker);
		
		monthsBox = new JComboBox<>(Utils.MONTHS);
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
		openInExplorerButton.addActionListener(e -> {
			String path;
			if(new File(Utils.GENERIC_PATH).exists())
				path = Utils.GENERIC_PATH + "/";
			else
				path = "./File Word/";
			
			try {
				File dir = new File(path);
				
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
		} catch (IllegalArgumentException i) {
			//Empty
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private void setTownTextFieldListener() {
		String capFilePath = new File(".").getAbsolutePath() + "/resources/files/cap.xls";
		
		try (
			FileInputStream fis = new FileInputStream(capFilePath);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
		) {
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			if(sheet != null)
				townField.getDocument().addDocumentListener(new TownFieldListener(sheet));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class TownFieldListener implements DocumentListener{
		private static final int TOWN_LISTED = 2;
		private static final int CELLS_BETWEEN_TOWN_NAMES = 3;
		
		
		private HSSFSheet sheet;
		
		public TownFieldListener(HSSFSheet sheet) {
			this.sheet = sheet;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {			
			for(Row row : sheet) {
				for(int i = 0; i < TOWN_LISTED; i++) {
					Cell cell = row.getCell(i*CELLS_BETWEEN_TOWN_NAMES);
					
					if(cell != null && (townField.getText().equalsIgnoreCase(cell.getStringCellValue()))) {
//						System.out.println("Reading cell (" + cell.getRowIndex() + "," + cell.getColumnIndex() + ") = " + cell.getStringCellValue());
						int cap = (int) row.getCell(cell.getColumnIndex() + 1).getNumericCellValue();
						String province = row.getCell(cell.getColumnIndex() + 2).getStringCellValue();
						
						capField.setText("" + cap);
						provinceField.setText(province);
						
						return;
					}
				}
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changedUpdate(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			changedUpdate(e);
		}
	}
}
