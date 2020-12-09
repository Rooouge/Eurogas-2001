package it.eurogas2001;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Application implements Runnable {

	public Image icon = new ImageIcon(getClass().getResource("/images/logo 480 back.png")).getImage();
	
	private ApplicationWindow applicationWindow;
	
	private List<JFrame> allFrames;
	
	
	@Override
	public void run() {
		allFrames = new ArrayList<JFrame>();
		
		applicationWindow = new ApplicationWindow(this);
		applicationWindow.show();
	}
	
	
	
	public boolean areYouSure() {
		int response = JOptionPane.showConfirmDialog(applicationWindow.getFrame(), 
				"Are you sure?", 
				"Eurogas 2001", 
				JOptionPane.OK_CANCEL_OPTION);
		
		return response == JOptionPane.OK_OPTION;
	}
	
	public void exit() {
		if(areYouSure())
			System.exit(0);
	}

	public void addFrameToList(JFrame newFrame) {
		allFrames.add(newFrame);
	}
	
	public void removeFrameFromList(JFrame frame) {
		frame.setVisible(false);
		allFrames.remove(frame);
	}
	
	public List<JFrame> getAllFrames() {
		return allFrames;
	}
	
	public JFrame getLastFrame() {
		return allFrames.get(allFrames.size()-1);
	}
}
