package it.eurogas2001.operations.printfiles;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.eurogas2001.Application;
import it.eurogas2001.GenericFrameListener;
import it.eurogas2001.Utils;
import it.eurogas2001.exceptions.EmptyFolderException;
import it.eurogas2001.exceptions.FolderNotExistsException;
import it.eurogas2001.exceptions.GenericException;

public class PrintFilesFrame {

	private JFrame frame;
	
	private Application application;
	private File[] files;
	private Desktop desktop;
	
	public PrintFilesFrame(Application application, String month) {
		this.application = application;
		desktop = Desktop.getDesktop();
		
//		setFrame();
		
		try {
			String path;
			if(new File(Utils.GENERIC_PATH).exists())
				path = Utils.GENERIC_PATH + month + "/";
			else
				path = "./File Word/" + month + "/";
			
			File dir = new File(path);
			
			if(!dir.exists()) 
				throw new FolderNotExistsException();
						
			int files = printFiles(dir);
			
			if(files == 0)
				throw new EmptyFolderException();
			
			showDialog(files, null);
		} catch (GenericException e) {
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public PrintFilesFrame(Application application, ArrayList<JFrame> previousFrames, File[] files) {
		this.application = application;
		this.files = files;
		desktop = Desktop.getDesktop();
		
//		setFrame();
		
		try {
			printFiles();
			
			showDialog(files.length, previousFrames);
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(
					application.getAllFrames().get(application.getAllFrames().size()-1), 
					"Print files - Error",
					"Nessun file selezionato!",
					JOptionPane.ERROR_MESSAGE);
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void setFrame() {
		//...
		
		frame = new JFrame();
		frame.setTitle("Eurogas 2001 - Print Files");
		frame.setIconImage(application.icon);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new GenericFrameListener(application, frame));
		
		frame.pack();
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);	
	}
	
	private void printFiles() throws Throwable {
		System.out.println("Printing " + files.length + " files");
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			System.out.println((i+1) + "/" + files.length + "   " + file.getName());
			
			desktop.print(file);
			TimeUnit.SECONDS.sleep(15);
		}
	}
	
	private int printFiles(File dir) throws Throwable {
		if(!dir.isDirectory())
			throw new IllegalArgumentException("Path " + dir.getAbsolutePath() + " is not a folder");
		
		files = dir.listFiles();
		
		System.out.println("Printing " + files.length + " files");
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			System.out.println((i+1) + "/" + files.length + "   " + file.getName());
			
			desktop.print(file);
			TimeUnit.SECONDS.sleep(15);
		}
		
		return files.length;
	}
		
	public void showDialog(int createdFiles, List<JFrame> previousFrames) {
		if(createdFiles == 1) 
			JOptionPane.showMessageDialog(
					application.getAllFrames().get(application.getAllFrames().size()-1), 
					("Stampato " + createdFiles + " file"), 
					"Eurogas 2001 - Print File", 
					JOptionPane.INFORMATION_MESSAGE
			);
		else
			JOptionPane.showMessageDialog(
					application.getAllFrames().get(application.getAllFrames().size()-1), 
					("Stampati " + createdFiles + " file"), 
					"Eurogas 2001 - Print Files", 
					JOptionPane.INFORMATION_MESSAGE
			);
		
		if(previousFrames != null) {
			JFrame f = previousFrames.get(previousFrames.size()-1);
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}
