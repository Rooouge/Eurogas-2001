package it.eurogas2001.operations.createfiles;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import it.eurogas2001.Application;
import it.eurogas2001.GenericFrameListener;
import it.eurogas2001.Utils;
import it.eurogas2001.Utils.Months;
import it.eurogas2001.components.fileinformation.InformationClass;
import it.eurogas2001.exceptions.FolderNotCreatedException;
import it.eurogas2001.exceptions.GenericException;
import it.eurogas2001.operations.createfiles.savefilestodisk.SaveFilesToDiskFrame;
import it.eurogas2001.operations.printfiles.PrintFilesFrame;

public class TableFromExcelFrame {

	public static final String[] COLUMNS = {"#", "Nome", "Indirizzo", "Città", "Provincia", "CAP"};

	private JFrame frame;
	
	private File file;
	
	private List<InformationClass> informationClasses;
	
	private JTable table;
	private Model tableModel;
	private JPanel tablePanel;
	private TableRowSorter<Model> sorter;
	
	private JTextField searchBar;
	
	//Scadenza
	private String month;
	
	
	public TableFromExcelFrame(Application application, List<InformationClass> list, String month, String type, String date) {
		this(application, null, month, type, date, list);
	}
	
	public TableFromExcelFrame(Application application, File file, String month, String type, String date, List<InformationClass> informationClasses) {
		this.file = file;
		this.month = month;
		if(informationClasses != null)
			this.informationClasses = informationClasses;
		
		frame = new JFrame();
		
		JButton saveButton = new JButton(new ImageIcon(getClass().getResource("/icons/save.png")));
		saveButton.setFocusable(false);
		saveButton.addActionListener(e -> new SaveFilesToDiskFrame(application, createFramesList(), createOutputList(), month, type, date) );
		
		JButton printButton = new JButton(new ImageIcon(getClass().getResource("/icons/print.png")));
		printButton.setFocusable(false);
		printButton.addActionListener(e -> {
			String pathForSave;
			if(new File(Utils.GENERIC_PATH).exists())
				pathForSave = Utils.GENERIC_PATH + this.month + "/";
			else
				pathForSave = "./File Word/" + this.month + "/";
			
			File dir = new File(pathForSave);
			if(dir.exists() && dir.listFiles() != null && dir.listFiles().length != 0) {
				List<InformationClass> outputList = createOutputList();
				File[] files = new File[outputList.size()];
				
				for(int i = 0; i < files.length; i++) {
					files[i] = Utils.createFileFromExcelRowData(outputList.get(i), pathForSave, type);
				}
								
				try {
					new PrintFilesFrame(application, createFramesList(), files);
				} catch (GenericException exception) {
					JOptionPane.showMessageDialog(
						application.getLastFrame(), 
						exception.getMessage(), 
						"Error: " + exception.getName(), 
						JOptionPane.ERROR_MESSAGE
					);
				}
			}
			else
				JOptionPane.showMessageDialog(
					application.getLastFrame(), 
					"Non è stato ancora salvato nessun file", 
					"Eurogas 2001 - Error", 
					JOptionPane.ERROR_MESSAGE
				);
		});
		
		JButton openInExplorerButton = new JButton(new ImageIcon(getClass().getResource("/icons/open_folder.png")));
		openInExplorerButton.setFocusable(false);
		openInExplorerButton.addActionListener(e -> {
			String path;
			if(new File(Utils.GENERIC_PATH).exists())
				path = Utils.GENERIC_PATH + this.month + "/";
			else
				path = "./File Word/" + this.month + "/";
			
			try {
				File outputDir = new File(path);
				
				if(!outputDir.exists()) 
					outputDir.mkdirs();
				
				if(!outputDir.isDirectory())
					throw new FolderNotCreatedException();
				
				Desktop.getDesktop().open(outputDir);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		});
		
		JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/icons/delete.png")));
		deleteButton.setFocusable(false);
		deleteButton.addActionListener(e -> {
			int response = JOptionPane.showConfirmDialog(
					frame, 
					"Hai selezionato " + table.getSelectedRowCount() + " righe: vuoi cancellarle?",
					"Eurogas 2001",
					JOptionPane.OK_CANCEL_OPTION);
			
			if(response == JOptionPane.OK_OPTION) {
				for(int row : table.getSelectedRows()) {
					searchBar.setText("");
					informationClasses.remove(row);
					updateTablePanelCount(sorter.getViewRowCount());
					frame.repaint();
				}
			}
		});		
		
		JToolBar toolBar = new JToolBar();
		toolBar.add(saveButton);
		toolBar.add(openInExplorerButton);
		toolBar.add(printButton);
//		toolBar.add(deleteButton);	//doesn't work
		
		createTablePanel();
		
		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(2).setPreferredWidth(300);
		columnModel.getColumn(3).setPreferredWidth(200);
		columnModel.getColumn(4).setPreferredWidth(50);
		columnModel.getColumn(5).setPreferredWidth(100);
		
		JScrollPane scrollPane = new JScrollPane(table);
		int scrollPaneWidth = 0;
		for(int i = 0; i < columnModel.getColumnCount(); i++) {
			scrollPaneWidth += columnModel.getColumn(i).getPreferredWidth();
		}
		scrollPane.setPreferredSize(new Dimension(scrollPaneWidth + 18, 500));
		
			
		tablePanel.add(scrollPane);		
		
		
		frame.setTitle("Eurogas 2001 - Input From Excel File");
		frame.setIconImage(application.icon);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new GenericFrameListener(application, frame));
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(tablePanel, BorderLayout.CENTER);
		panel.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	private JPanel createSouthPanel() {
		searchBar = new JTextField();
		searchBar.getDocument().addDocumentListener(new SearchBarListener());
		
		TitledBorder searchBorder = new TitledBorder("Ricerca");
		searchBorder.setTitleColor(Utils.EG2001_CYAN);
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBorder(searchBorder);
		searchPanel.add(searchBar, BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(searchPanel, BorderLayout.NORTH);
		
		return panel;
	}
	
	private void createTablePanel() {
		if(informationClasses == null) {
			try (
				FileInputStream fis = new FileInputStream(file);
				HSSFWorkbook workbook = new HSSFWorkbook(fis);
			) {		
				HSSFSheet sheet = workbook.getSheetAt(0);
				
				informationClasses = new ArrayList<>();
				
				readFromSheet(sheet);			
				
				table = new JTable();		
				tableModel = new Model();
				table.setModel(tableModel);
				addToTable();
				
				tablePanel = new JPanel();
				TitledBorder border = new TitledBorder(" " + month + " - Totale: 0 righe ");
				border.setTitleColor(Utils.EG2001_CYAN);
				tablePanel.setBorder(border);
				
				updateTablePanelCount(table.getRowCount());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			table = new JTable();		
			tableModel = new Model();
			table.setModel(tableModel);
			addToTable();
			
			tablePanel = new JPanel();
			TitledBorder border = new TitledBorder(" " + month + " - Totale: 0 righe ");
			border.setTitleColor(Utils.EG2001_CYAN);
			tablePanel.setBorder(border);
			
			updateTablePanelCount(table.getRowCount());
		}
	}
	
	private void updateTablePanelCount(int newValue) {
		((TitledBorder)tablePanel.getBorder()).setTitle(" " + month + " - Totale: " + newValue + " righe ");
		
		frame.repaint();
	}
	
	private void readFromSheet(HSSFSheet sheet) {
		for(Row row : sheet) {
			if(row.getRowNum() != 0) {
				String name = row.getCell(32).getStringCellValue();
				if(name == null || name.equals(""))
					name = row.getCell(19).getStringCellValue();
				
				String address = row.getCell(33).getStringCellValue();
				if(address == null || address.equals(""))
					address = row.getCell(20).getStringCellValue();
				
				String town = row.getCell(35).getStringCellValue();
				if(town == null || town.equals(""))
					town = row.getCell(22).getStringCellValue();
				
				String province = row.getCell(34).getStringCellValue();
				if(province == null || province.equals(""))
					province = row.getCell(21).getStringCellValue();
				
				int cap = -1;
				try {
					cap = Integer.parseInt(row.getCell(23).getStringCellValue());
				} catch (Exception e) {
					//Empty
				}
				
//				System.out.println(
//						row.getRowNum() + " ... " +
//						name + " ... " + 
//						address	+ " ... " +
//						town + " ... " + 
//						province + " ... " + 
//						cap);
				
				/*
				 * con barre
				 * int expiryMonth = Integer.parseInt(row.getCell(27).getStringCellValue().substring(3, 5));
				 */
				
				int expiryMonth = Integer.parseInt(row.getCell(27).getStringCellValue().substring(4, 6));
				
				if(expiryMonth == Months.valueOf(month.substring(0, month.length() - 5)).getIndex())
					informationClasses.add(new ExcelRowData(row.getRowNum(), name, address, town, province, cap));
			
			}
		}
	}
	
	private void addToTable() {
		for(int i = 0; i < informationClasses.size(); i++) {
			InformationClass row = informationClasses.get(i);
			
			table.setValueAt(i+1, i, 0);
			table.setValueAt(row.name, i, 1);
			table.setValueAt(row.address, i, 2);
			table.setValueAt(row.town, i, 3);
			table.setValueAt(row.province, i, 4);
			table.setValueAt(row.cap, i, 5);
		}
	}
	
	private ArrayList<JFrame> createFramesList() {
		ArrayList<JFrame> list = new ArrayList<>();
		
		list.add(frame);
		
		return list;
	}
	
	private ArrayList<InformationClass> createOutputList() {
		ArrayList<InformationClass> outputList = new ArrayList<>();
		
		for(InformationClass row : informationClasses) {
			boolean condition = row.address.contains(searchBar.getText().toUpperCase()) ||
			row.name.contains(searchBar.getText().toUpperCase()) ||
			row.province.contains(searchBar.getText().toUpperCase()) ||
			row.town.contains(searchBar.getText().toUpperCase());
			
			if(condition) {
				outputList.add(new InformationClass(row.index, row.name, row.address, row.town, row.province, row.cap));
			}
		}
		
		return outputList;
	}
	
	
	private class Model implements TableModel {
		private List<TableModelListener> listeners = new ArrayList<>();
		
		@Override
		public int getRowCount() {
			return informationClasses.size();
		}
		
		@Override
		public int getColumnCount() {
			return 6;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			InformationClass excelRowData = informationClasses.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return excelRowData.index;
			case 1:
				return excelRowData.name;
			case 2:
				return excelRowData.address;
			case 3:
				return excelRowData.town;
			case 4:
				return excelRowData.province;
			case 5:
				return excelRowData.cap;
			default:
				return null;
			}
		}
		
		@Override
		public void addTableModelListener(TableModelListener l) {
			listeners.add(l);
		}
		
		@Override
		public void removeTableModelListener(TableModelListener l) {
			listeners.remove(l);
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 4:
				return int.class;
			default:
				return String.class;
			}
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			return COLUMNS[columnIndex];
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			InformationClass excelRowData = informationClasses.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				excelRowData.index = (int) value;
				break;
			case 1:
				excelRowData.name = (String) value;
				break;
			case 2:
				excelRowData.address = (String) value;
				break;
			case 3:
				excelRowData.town = (String) value;
				break;
			case 4:
				excelRowData.province = (String) value;
				break;
			case 5:
				excelRowData.cap = (int) value;
				break;
			default:
				break;
			}
		}
	}
	
	
	private class SearchBarListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			update();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			String text = searchBar.getText();
			
			if(text.trim().length() == 0)
				sorter.setRowFilter(null);
			else
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			
			updateTablePanelCount(sorter.getViewRowCount());
		}
		
		private void update() {
			String text = searchBar.getText();
			
			if(text.trim().length() == 0)
				sorter.setRowFilter(null);
			else
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
						
			updateTablePanelCount(sorter.getViewRowCount());
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
