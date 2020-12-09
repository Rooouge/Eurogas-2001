package it.eurogas2001.lookandfeels;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import it.eurogas2001.Application;

@SuppressWarnings("serial")
public class LookAndFeelItem extends JMenuItem {
	
	public LookAndFeelItem(LookAndFeelInfo info, Application application) {
		super(info.getName());
		
		this.addActionListener((e) -> {	
			try {
				for(JFrame frame : application.getAllFrames()) {
					UIManager.setLookAndFeel(info.getClassName());
					
					SwingUtilities.updateComponentTreeUI(frame);
					
					frame.pack();
				}
			} catch (Throwable t) {
				t.printStackTrace();
				
				Toolkit.getDefaultToolkit().beep();
			}
		});	
	}
	
}
