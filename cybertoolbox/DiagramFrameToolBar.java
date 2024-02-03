/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DiagramFrameToolBar.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DiagramFrameToolBar extends ToolBar implements WorldConstants {

	private DiagramFrame mDiagramFrame = null;
	
    public DiagramFrameToolBar (DiagramFrame diagramFrame) {
		setDiagramFrame(diagramFrame);
		String dir = WORLD_IMAGE_TOOLBAR_DIAGRAM_DIRECTORY;
		String sep = System.getProperty("file.separator");
		addToolBarButton("Cut", dir + sep + "cut.gif", new CutAction());
		addToolBarButton("Copy", dir + sep + "copy.gif", new CopyAction());
		addToolBarButton("Paste", dir + sep + "paste.gif", new PasteAction());
		addSeparator();
		addToolBarButton("Undo", dir + sep + "undo.gif", new UndoAction());
		addSeparator();
		addToolBarButton("Config", dir + sep + "config.gif", new ConfigAction());
	}

	//////////////////////////////////////////////////
	// Diagram Frame
	//////////////////////////////////////////////////

	public void	setDiagramFrame(DiagramFrame diagramFrame) {
		mDiagramFrame = diagramFrame;
	}
	
	public DiagramFrame getDiagramFrame() {
		return mDiagramFrame;
	}

	private class CutAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getDiagramFrame().cut();
		}
	}
	
	private class CopyAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getDiagramFrame().copy();
		}
	}
	
	private class PasteAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getDiagramFrame().paste();
		}
	}
	
	private class UndoAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getDiagramFrame().undo();
		}
	}

	private class ConfigAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			DiagramFrame	dgmFrame		= getDiagramFrame();
			Diagram			dgm			= new Diagram(dgmFrame.getWorld(), dgmFrame.getDiagramNode());
			DialogDiagram	dgmDialog	= new DialogDiagram(dgmFrame.getFrame(), dgm, dgmFrame.getLineStyle());
			
			if (dgmDialog.doModal() == Dialog.OK_OPTION) {
				String dgmName = dgmDialog.getName();
				if (dgmName != null) {
					dgm.setName(dgmName);
					dgmFrame.setTitle(dgmName);
					
					WorldTreeNode treeNode = (WorldTreeNode)dgm.getTransformNode().getData();
					if (treeNode != null)
						treeNode.setText(dgmName);
				}
				dgmFrame.setLineStyle(dgmDialog.getDataflowLineStyle());
			}			
		}
	}
}
