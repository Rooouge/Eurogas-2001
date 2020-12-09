package it.eurogas2001;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;

import it.eurogas2001.components.EurogasJTree;
import it.eurogas2001.components.ManualInsertPanel;
import it.eurogas2001.components.PrintFilesPanel;
import it.eurogas2001.components.ReadFromExcelPanel;
import it.eurogas2001.components.VoidPanel;
import it.eurogas2001.lookandfeels.LookAndFeelItem;

public class ApplicationWindow {

	private JFrame frame;
	
	private Application application;
	
	private String movementsString;
	
	private JPanel centerPanel;
	
	
	public ApplicationWindow(Application application) {
		this.application = application;
		
		frame = new JFrame();
		frame.addWindowListener(new ApplicationListener());
		frame.setTitle("Eurogas 2001");
		frame.setIconImage(application.icon);
		frame.setLayout(new BorderLayout());
		
		GenericContentPane contentPane = new GenericContentPane();
		frame.setContentPane(contentPane);
		JPanel panel = contentPane.getCenterPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createNorthPanel(), BorderLayout.NORTH);
		panel.add(createCenterPanel(), BorderLayout.CENTER);
		
		frame.setJMenuBar(createMenuBar());
		
		frame.setMinimumSize(new Dimension(800, 400));
		frame.setResizable(false);
		frame.pack();
		
		frame.setLocationByPlatform(true);
		application.addFrameToList(frame);
	}
	
	public JMenuBar createMenuBar() {
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("/images/exit.png"));
		
		JMenuItem exitMenuItem = new JMenuItem("Exit", exitIcon);
		
		exitMenuItem.addActionListener((e) -> {
			application.exit();
		});
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(exitMenuItem);		
		
		
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		LookAndFeelItem[] items = new LookAndFeelItem[infos.length];
		
		JMenu lookAndFeelsMenu = new JMenu("Change Look And Feel");
		
		for(int i = 0; i < items.length; i++) {
			items[i] = new LookAndFeelItem(infos[i], application);
			lookAndFeelsMenu.add(items[i]);
		}
		
				
		JMenu viewMenu = new JMenu("View");
		viewMenu.add(lookAndFeelsMenu);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		
		return menuBar;
	}
	
	public JPanel createNorthPanel() {
		movementsString = " > Menù ";
		JLabel movementsLabel = new JLabel(movementsString);
		movementsLabel.setFont(movementsLabel.getFont().deriveFont(Utils.FONT_SIZE));
		movementsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		movementsLabel.setForeground(Utils.EG2001_CYAN);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(movementsLabel, BorderLayout.NORTH);
		northPanel.setBorder(new LineBorder(Color.GRAY));
		
		return northPanel;
	}
	
	public JPanel createCenterPanel() {
		EurogasJTree tree = new EurogasJTree(this);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(tree.getTree(), BorderLayout.NORTH);
		centerPanel.add(new VoidPanel(), BorderLayout.CENTER);
		centerPanel.setBorder(new LineBorder(Color.GRAY));
		
		this.centerPanel = centerPanel;
		
		return centerPanel;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void changeView(String panelName) {
		if(panelName.equals("Operazioni su file")) {
			centerPanel.remove(centerPanel.getComponentCount()-1);
			centerPanel.add(new VoidPanel(), BorderLayout.CENTER);
		}
		else if(panelName.equals("Leggi dati da File Excel")) {
			centerPanel.remove(centerPanel.getComponentCount()-1);
			centerPanel.add(new ReadFromExcelPanel(application), BorderLayout.CENTER);
		}
		else if(panelName.equals("Inserimento Manuale")) {
			centerPanel.remove(centerPanel.getComponentCount()-1);
			centerPanel.add(new ManualInsertPanel(application), BorderLayout.CENTER);
		}
		else if(panelName.equals("Stampa File")) {
			centerPanel.remove(centerPanel.getComponentCount()-1);
			centerPanel.add(new PrintFilesPanel(application), BorderLayout.CENTER);
		}
		centerPanel.validate();
		frame.pack();
	}
	
	
	
	public class ApplicationListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			
		}
		
	}
	
}
