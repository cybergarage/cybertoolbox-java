/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File: DiagramFrame.java
*
******************************************************************/

import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.route.*;
import vrml.util.*;

/******************************************************************
*
*	DiagramFrameScrollPane
*
******************************************************************/

class DiagramScrollPane extends JScrollPane {

	public DiagramScrollPane(Component view) {
		super();
		setViewportView(view);
		//setRowHeaderView(verticalRule);
		//setColumnHeaderView(horizontalRule);
	}

}

/******************************************************************
*
*	DiagramFrame
*
******************************************************************/

public class DiagramFrame extends LinkedListNode implements ScrollPaneConstants, Runnable {

	private Frame				mFrame			= null;
	private TransformNode	mDiagramNode	= null;
	private World				mWorld			= null;
	private DiagramPanel		mMainComponent	= null;
	
	public DiagramFrame(World world, TransformNode dgmNode) {
		setDiagramNode(dgmNode);
		setWorld(world);
	
	
		Diagram dgm = new Diagram(world, dgmNode);
		
		//mFrame = new JFrame(dgm.getName());
		mFrame = new Frame(dgm.getName());
		
		mFrame.setLayout(new BorderLayout());
		//mFrame.getContentPane().setLayout(new BoxLayout(mFrame.getContentPane(), BoxLayout.Y_AXIS));
		//mFrame.getContentPane().setLayout(new BorderLayout());

		mFrame.add("North", new DiagramFrameToolBar(this));
		//mFrame.getContentPane().add("North", new DiagramFrameToolBar(this));
		//mFrame.getContentPane().add(new DiagramFrameToolBar(this));
				
		mMainComponent = new DiagramPanel(this, world, dgmNode);
		DiagramScrollPane dgmScrollPane = new DiagramScrollPane(mMainComponent);
		mFrame.add("Center", dgmScrollPane);
		//mFrame.getContentPane().setLayout(new BorderLayout());
		//mFrame.getContentPane().add("Center", dgmScrollPane);
		//mFrame.getContentPane().add(dgmScrollPane);
																				
		mFrame.addWindowListener( 
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Diagram diagram = new Diagram(getWorld(), getDiagramNode());
					Frame frame = getFrame();
					diagram.setPosition(frame.getLocation().x, frame.getLocation().y);
					diagram.setWidth(frame.getSize().width);
					diagram.setHeight(frame.getSize().height);
					remove();
					frame.dispose();
				}
			}
		);
		
		mFrame.addKeyListener(mMainComponent);
		
		mFrame.setLocation(dgm.getXPosition(), dgm.getYPosition());
		mFrame.setSize(dgm.getWidth(), dgm.getHeight());
		mFrame.show();
	}

	//////////////////////////////////////////////////
	// repaint
	//////////////////////////////////////////////////

	public void repaint() {
		getFrame().repaint();
	}
		
	//////////////////////////////////////////////////
	// World
	//////////////////////////////////////////////////

	public void setWorld(World world) {
		mWorld = world;
	}

	public World getWorld() {
		return mWorld;
	}

	//////////////////////////////////////////////////
	// Frame
	//////////////////////////////////////////////////

	public void setFrame(Frame frame) {
		mFrame = frame;
	}

	public Frame getFrame() {
		return mFrame;
	}

	public void setTitle(String title) {
		mFrame.setTitle(title);
	}

	//////////////////////////////////////////////////
	// Size
	//////////////////////////////////////////////////

	public int getWidth() {
		return mFrame.getBounds().width;
	}

	public int getHeight() {
		return mFrame.getBounds().height;
	}

	public Dimension getSize() {
		return mFrame.getSize();
	}

	//////////////////////////////////////////////////
	// Diagram Node
	//////////////////////////////////////////////////

	public void	setDiagramNode(TransformNode node) {
		mDiagramNode = node;
	}
	
	public TransformNode getDiagramNode() {
		return mDiagramNode;
	}

	//////////////////////////////////////////////////
	// Main Component
	//////////////////////////////////////////////////

	public DiagramPanel getMainComponent() {
		return mMainComponent;
	}

	public Point getMainComponentLocationOnScreen() {
		return mFrame.getLocation();
	}

	public int getMainComponentWidth() {
		return mMainComponent.getWidth();
	}

	public int getMainComponentHeight() {
		return mMainComponent.getHeight();
	}

	public boolean getGridSnap() { 
		return getMainComponent().getGridSnap(); 
	}

	public boolean getGridDisplay() { 
		return getMainComponent().getGridDisplay(); 
	}

	public int getGridSize() { 
		return getMainComponent().getGridSize(); 
	}

	public void setLineStyle(int style) {
		getMainComponent().setLineStyle(style); 
	}
	
	public int getLineStyle() { 
		return getMainComponent().getLineStyle(); 
	}

	//////////////////////////////////////////////////
	// Add Module
	//////////////////////////////////////////////////

	public void addModule(ModuleType moduleType, int localx, int localy) {
		Debug.message("DiagramFrame::addModule = " + localx + ", " + localy);
		Module module = new Module(getWorld(), moduleType);
		module.setPosition(localx, localy);
		Diagram dgm = new Diagram(getWorld(), getDiagramNode());
		dgm.addModule(module);
		addUndoAction(new DiagramAddModuleUndoAction(module.getScriptNode()));
		DiagramPanel diagramPanel = getMainComponent();
		diagramPanel.resetComponentSize();	
		diagramPanel.repaint();
	}

	public void addModuleOnScreen(ModuleType moduleType, int screenx, int screeny) {
		Point componentScreenPos = getMainComponentLocationOnScreen();
		int localx = screenx - componentScreenPos.x;
		int localy = screeny - componentScreenPos.y;
		addModule(moduleType, localx, localy);
	}

	//////////////////////////////////////////////////
	// Undo
	//////////////////////////////////////////////////
	
	private Undo mUndo = new Undo(50);

	public void addUndoAction(UndoObject undoObject) {
		mUndo.add(undoObject);
	}
	
	public void undo() {
		if (mUndo.undo() == false)
			new MessageBeep();
		getMainComponent().repaint();
	}

	//////////////////////////////////////////////////
	// Cut / Copy / Paste
	//////////////////////////////////////////////////

	public void cut() {
		getMainComponent().cut();
	}
	
	public void copy() {
		getMainComponent().copy();
	}
	
	public void paste() {
		getMainComponent().paste();
	}
	
	//////////////////////////////////////////////////
	// UndoAction
	//////////////////////////////////////////////////
	
	private class DiagramAddModuleUndoAction implements UndoObject {
		ScriptNode			mModuleNode = null;
		public DiagramAddModuleUndoAction(ScriptNode moduleNode) {
			mModuleNode = moduleNode;
		}
		public void undo() {
			Diagram diagram = new Diagram(getWorld(), getDiagramNode()); 
			Module module = new Module(getWorld(), mModuleNode);
			diagram.removeModule(module);
		}
	}

	//////////////////////////////////////////////////
	// next
	//////////////////////////////////////////////////

	public DiagramFrame next() {
		return (DiagramFrame)getNextNode();
	}

	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////

	private Thread mThreadObject = null;
	
	public void setThreadObject(Thread obj) {
		mThreadObject = obj;
	}

	public Thread getThreadObject() {
		return mThreadObject;
	}

	public void run() {
		while (true) {
			getMainComponent().repaint();
			Thread threadObject = getThreadObject();
			if (threadObject != null) { 
//				threadObject.yield();
				try {
					threadObject.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void start() {
		Thread threadObject = getThreadObject();
		if (threadObject == null) {
			threadObject = new Thread(this);
			setThreadObject(threadObject);
			threadObject.start();
		}
	}
	
	public void stop() {
		Thread threadObject = getThreadObject();
		if (threadObject != null) {
			//threadObject.destroy();
			threadObject.stop();
			setThreadObject(null);
		}
	}
}

