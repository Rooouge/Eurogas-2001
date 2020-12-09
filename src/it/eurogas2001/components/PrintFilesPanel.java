package it.eurogas2001.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import it.eurogas2001.Application;
import it.eurogas2001.Utils;
import it.eurogas2001.exceptions.GenericException;
import it.eurogas2001.operations.printfiles.PrintFilesFrame;

@SuppressWarnings("serial")
public class PrintFilesPanel  extends JPanel {

	private JComboBox<String> monthsBox;
	private JComboBox<String> yearsBox;
	
	public PrintFilesPanel(Application application) {
		JLabel label = new JLabel(" > Stampa File");
		label.setForeground(Utils.EG2001_CYAN);
		label.setFont(label.getFont().deriveFont(Utils.FONT_SIZE));
		label.setBorder(new LineBorder(Color.GRAY));
		
		FlowLayout centerPanelLayout = new FlowLayout();
		centerPanelLayout.setVgap(10);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(centerPanelLayout);
		centerPanel.add(createCenterPanel());
		
		JButton nextButton = new JButton("Prosegui");
		nextButton.addActionListener((e) -> {
			try {
				new PrintFilesFrame(application, monthsBox.getSelectedItem() + " " + yearsBox.getSelectedItem());
			} catch (GenericException exception) {
				JOptionPane.showMessageDialog(
					application.getLastFrame(), 
					exception.getMessage(), 
					"Error: " + exception.getName(), 
					JOptionPane.ERROR_MESSAGE
				);
			}
		});
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(nextButton);
		
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();
		
		String[] years = new String[10];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		monthsBox = new JComboBox<>(Utils.months);
		if(month == 11)
			monthsBox.setSelectedIndex(0);
		else
			monthsBox.setSelectedIndex(month + 1);
		
		for(int i = 0; i < years.length; i++) {
			years[i] = "" + (year + i);
		}
		
		yearsBox = new JComboBox<String>(years);
		
		TitledBorder expiryBorder = new TitledBorder("Scadenza");
		expiryBorder.setTitleColor(Utils.EG2001_CYAN);
		
		panel.setBorder(expiryBorder);
		
		panel.setLayout(new GridLayout(2, 0));
		panel.add(monthsBox);
		panel.add(yearsBox);
		
		return panel;
	}
}
