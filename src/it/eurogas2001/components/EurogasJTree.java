package it.eurogas2001.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import it.eurogas2001.ApplicationWindow;

public class EurogasJTree {
	
	private JTree tree;
	
	
	public EurogasJTree(ApplicationWindow applicationWindow) {
		
		tree = new JTree(createRoot());
		
		tree.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
//				System.out.println("Released");
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println("Pressed");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
//				System.out.println("Exited");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
//				System.out.println("Entered");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println("Clicked");
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
		        if(selRow != -1) {
		            if(e.getClickCount() == 2) {
		            	TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			        	
			        	applicationWindow.changeView(((DefaultMutableTreeNode) selPath.getLastPathComponent()).toString());
		            }
		        }
			}
		});
	}
	
	private DefaultMutableTreeNode createRoot() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Operazioni su File");
		
		node.add(createReadFromExcelNode());
		node.add(createManualInsertNode());
		node.add(createPrintFiles());
		return node;
	}
	

	private DefaultMutableTreeNode createReadFromExcelNode() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Leggi dati da File Excel");
		
		return node;
	}
	
	private DefaultMutableTreeNode createManualInsertNode() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Inserimento Manuale");
		
		return node;
	}
	
	private DefaultMutableTreeNode createPrintFiles() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Stampa File");
		
		return node;
	}
	
	public JTree getTree() {
		return tree;
	}
}
