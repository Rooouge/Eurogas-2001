package it.eurogas2001;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GenericFrameListener implements WindowListener {

	private Application application;
	private JFrame frame;
	
	public GenericFrameListener(Application application, JFrame frame) {
		this.application = application;
		this.frame = frame;
		
		application.addFrameToList(frame);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		application.removeFrameFromList(frame);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}

}
