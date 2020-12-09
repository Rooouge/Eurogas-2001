package it.eurogas2001.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.eurogas2001.Utils;

@SuppressWarnings("serial")
public class VoidPanel extends JPanel {

	public VoidPanel() {
		JLabel voidLabel = new JLabel("---");
		voidLabel.setForeground(Utils.EG2001_CYAN);
		voidLabel.setFont(voidLabel.getFont().deriveFont(Utils.FONT_SIZE));
		voidLabel.setBorder(new LineBorder(Color.GRAY));
		
		JPanel voidPanel = new JPanel();
//		voidPanel.setBackground(Utils.BACKGROUND_COLOR);
		
		this.setLayout(new BorderLayout());
		this.add(voidLabel, BorderLayout.NORTH);
		this.add(voidPanel, BorderLayout.CENTER);
	}
}
