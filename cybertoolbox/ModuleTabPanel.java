/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModulePanelTab.java
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

public class ModuleTabPanel extends JPanel {
	
	World		mWorld;
	String		mClassString;

	private World getWorld() {
		return mWorld;
	}
	
	public String getClassString() {
		return mClassString;
	}
	
	public ModuleTabPanel(World world, String classString) {
		mWorld = world;
		mClassString = classString;
		setBackground(Color.white);
		setLayout(new BorderLayout());
		setToolTipText("ModulePanel");
	}

	private boolean isRect(int x0, int y0, int x1, int y1, int x, int y) {
		if (x0 <= x && x <= x1 && y0 <= y && y <= y1)
			return true;
		else
			return false;
	}

	public ModuleType getModuleType(int x, int y) {
		int x0 = 0;
		for (ModuleType modType = getWorld().getModuleTypes(getClassString()); modType != null; modType = modType.next(getClassString())) {
			Image icon = modType.getIcon();
			int y0 = (getHeight() - icon.getHeight(null)) / 2;
			int x1 = x0 + icon.getWidth(null);
			int y1 = y0 + icon.getHeight(null);
			if (isRect(x0, y0, x1, y1, x, y) == true)
				return modType;			
			x0 += icon.getWidth(null);
		}
		return null;
	}
	
	public void paint(Graphics g) {
		int x = 0;
		for (ModuleType modType = getWorld().getModuleTypes(getClassString()); modType != null; modType = modType.next(getClassString())) {
			Image icon = modType.getIcon();
			MediaTracker mt = new MediaTracker(this);
			mt.addImage (icon, 0);
			try { mt.waitForAll(); }
			catch (InterruptedException e) {
				Debug.warning(e.getMessage() + "in ModulePanelTab::paint()");
			}
			if (mt.isErrorAny()) {
				Debug.warning("Couldn't load " + modType.getIconFileName() + "in ModulePanelTab::paint()");
			}
			int y = (getHeight() - icon.getHeight(null)) / 2;
			g.drawImage(icon, x, y, null);
			x += icon.getWidth(null);
		}
	}

	public String getToolTipText(MouseEvent e) {
		ModuleType modType = getModuleType(e.getX(), e.getY());
		if (modType != null) {
			//Debug.message("setToolTipText = " + modType.getName());
			return modType.getName();
		}
		return null;
	}
}
