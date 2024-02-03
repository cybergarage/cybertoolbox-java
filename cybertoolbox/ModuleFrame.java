/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModuleFrame.java
*
******************************************************************/

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;
import vrml.util.*;

public class ModuleFrame extends JFrame implements WorldConstants, MouseListener {
	
	World		mWorld;
	JTabbedPane	mTabbedPane;

	public World getWorld() {
		return mWorld;
	}
	
	public ModuleFrame(World world) {
		mWorld = world;

		getContentPane().setLayout(new BorderLayout());
		setSize(32*(19-1)+120, 100);
		//setLocation(screenSize.width/2 - WIDTH/2, screenSize.height/2 - HEIGHT/2);
//		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		JPanel panel = new JPanel();
		panel.setName("Module");
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		
		mTabbedPane = new JTabbedPane();
		panel.add(mTabbedPane, BorderLayout.CENTER);

		createModuleTabs(world, mTabbedPane);

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

		setResizable(false);

		show();
	
		validate();
		repaint();
        //panel.requestDefaultFocus();

		createTabListener();
	}

	public void processWindowEvent(WindowEvent e) {
	}

	private void createModuleTabs(World world, JTabbedPane tabbedPane) {
		String className[] = {
				"String", 
				"Math", 
				"Numeric",
				"Filter",
				"Boolean",
				"Geom",
				"Object", 
				"Light", 
				"Material", 
				"View",
				"Interp",
				"Misc"};
			
		for (int n=0; n<className.length; n++) {
			JPanel panel = new ModuleTabPanel(world, className[n]);
			tabbedPane.addTab(className[n], null, panel);
			panel.addMouseListener(this);
		}
	}
	
    void createTabListener() {
	// add listener to know when we've been shown
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
	/*
                JTabbedPane tab = (JTabbedPane) e.getSource();
                int index = tab.getSelectedIndex();
                Component currentPage = tab.getComponentAt(index);
		RepaintManager repaintManager = 
                    RepaintManager.currentManager(instance);

		if(!repaintManager.isDoubleBufferingEnabled()) {
		  repaintManager.setDoubleBufferingEnabled(true);
		}

		if(previousPage == debugGraphicsPanel) {
		    ((DebugGraphicsPanel)debugGraphicsPanel).resetAll();
		}
*/
            }
        };
        mTabbedPane.addChangeListener(changeListener);
    }

	////////////////////////////////////////////////
	//	mouse
	////////////////////////////////////////////////
	
	private ModuleType mSelectedModuleType	= null;
	
	public void setSelectedModuleType(ModuleType moduleType) {
		mSelectedModuleType = moduleType;
	}

	public ModuleType getSelectedModuleType() {
		return mSelectedModuleType;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private ModuleTabPanel getSelectedModuleTabPanel() {
		return (ModuleTabPanel)mTabbedPane.getSelectedComponent();
	}
	
	private ModuleType getModuleTypeForLocation(int x, int y) {
		return getSelectedModuleTabPanel().getModuleType(x, y);
	}
	
	public void mousePressed(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		ModuleType selectedModuleType = getModuleTypeForLocation(e.getX(), e.getY());

		if (selectedModuleType != null) {
			getWorld().setOperationMode(WORLD_OPETATION_MODE_MODULETYPE_DRAGGED);
			setSelectedModuleType(selectedModuleType);
		}
		else {
			getWorld().setOperationMode(WORLD_OPETATION_MODE_NONE);
			setSelectedModuleType(null);
		}
	}

	public void mouseReleased(MouseEvent e) {
		Debug.message("ModuleFrame.moduleRelesed");
		if (getWorld().getOperationMode() == WORLD_OPETATION_MODE_MODULETYPE_DRAGGED) {
			Point screenPos = getLocationOnScreen();
			int smx = e.getX() + screenPos.x;
			int smy = e.getY() + screenPos.y;
			DiagramFrame dgmFrame = getWorld().getDiagramFrame(smx, smy);
			Debug.message("\tdgmFrame = " + dgmFrame);
			if (dgmFrame != null) {
				if (dgmFrame.getGridSnap() == true) {
					smx -= smx % dgmFrame.getGridSize();
					smy -= smy % dgmFrame.getGridSize();
				}
				Debug.message("\t(x, y) = " + smx + ", " + smy);
				dgmFrame.addModuleOnScreen(getSelectedModuleType(), smx, smy);
				dgmFrame.getMainComponent().resetComponentSize();
			}
			getWorld().setOperationMode(WORLD_OPETATION_MODE_NONE);
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
