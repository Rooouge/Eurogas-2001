package it.eurogas2001;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GenericContentPane extends JPanel {

	public static final Color textColor = new Color(230, 230, 230);
	
	private JPanel centerPanel;
	
	public GenericContentPane() {
		ImageIcon logo = new ImageIcon(getClass().getResource("/images/logo 240x120.png"));
		
		JLabel emailsLabel = new JLabel("info@eurogas2001.it   silvia@eurogas2001.it");
		emailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailsLabel.setForeground(textColor);
		emailsLabel.setBackground(new Color(0, 100, 160));
		
		JLabel firstAddressLabel = new JLabel("Via Emilio Lepido, 200/A - Loc. Il Moro     0521 1681226");
		firstAddressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		firstAddressLabel.setForeground(textColor);
		firstAddressLabel.setBackground(new Color(0, 100, 160));
		
		JLabel secondAddressLabel = new JLabel("Via Milazzo, 42/A     0521 1680431");
		secondAddressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		secondAddressLabel.setForeground(textColor);
		secondAddressLabel.setBackground(new Color(0, 100, 160));
		
		TitledBorder border = new TitledBorder("Eurogas 2001 Srl");
		border.setTitleFont(border.getTitleFont().deriveFont(15f));
		border.setTitleColor(textColor);
		
		GridLayout layout = new GridLayout(3, 1);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBorder(border);
		informationPanel.setLayout(layout);
		informationPanel.add(emailsLabel);
		informationPanel.add(firstAddressLabel);
		informationPanel.add(secondAddressLabel);
		informationPanel.setBackground(new Color(0, 100, 160));
		
		
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		westPanel.setBackground(new Color(0, 100, 160));
		westPanel.add(new JLabel(logo), BorderLayout.NORTH);
		westPanel.add(informationPanel, BorderLayout.SOUTH);
		
		/*
		 * this
		 */
		this.setLayout(new BorderLayout());
		
		this.add(westPanel, BorderLayout.WEST);
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public JPanel getCenterPanel() {
		return centerPanel;
	}
	
}
