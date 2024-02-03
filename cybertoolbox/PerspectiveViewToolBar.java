/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	PerspectiveViewToolBar.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import vrml.*;
import vrml.node.*;
import vrml.util.*;
import vrml.j3d.*;

public class PerspectiveViewToolBar extends ToolBar implements Constants, WorldConstants {

	private World mWorld = null;
	private Frame mFrame = null;
	
	private void setFrame(Frame frame) {
		mFrame = frame;
	}
	
	private Frame getFrame() {
		return mFrame;
	}

	private void setWorld(World world) {
		mWorld = world;
	}
	
	private World getWorld() {
		return mWorld;
	}

	private SceneGraph getSceneGraph() {
		return getWorld().getSceneGraph();
	}
		
    public PerspectiveViewToolBar(Frame frame, World world) {
		setFrame(frame);
		setWorld(world);
		String dir = WORLD_IMAGE_TOOLBAR_PERSPECTIVEVIEW_DIRECTORY;
		String sep = System.getProperty("file.separator");
		addToolBarButton("Pick", dir + sep + "operation_pick.gif", new OperationPickAction());
		addToolBarButton("Walk", dir + sep + "operation_walk.gif", new OperationWalkAction());
		addToolBarButton("Pan", dir + sep + "operation_pan.gif", new OperationPanAction());
		addToolBarButton("Rot", dir + sep + "operation_rot.gif", new OperationRotAction());
		addSeparator();
		addToolBarButton("Reset Viewpoint", dir + sep + "reset_viewpoint.gif", new ResetViewpointAction());
		addToolBarButton("Headlight", dir + sep + "headlight.gif", new HeadlightAction());
		addSeparator();
		addToolBarButton("Wireframe", dir + sep + "rendering_wire.gif", new RenderingWireAction());
		addToolBarButton("Shading", dir + sep + "rendering_shade.gif", new RenderingShadeAction());
		addSeparator();
		addToolBarButton("Config", dir + sep + "config.gif", new ConfigAction());
	}

	private class OperationPickAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			((PerspectiveViewJava3D)getFrame()).setOperationMode(PerspectiveViewJava3D.OPERATION_MODE_PICK);
			Debug.message("Operation mode = PICK");
		}
	}

	private class OperationWalkAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			((PerspectiveViewJava3D)getFrame()).setOperationMode(PerspectiveViewJava3D.OPERATION_MODE_WALK);
			Debug.message("Operation mode = WALK");
		}
	}

	private class OperationPanAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			((PerspectiveViewJava3D)getFrame()).setOperationMode(PerspectiveViewJava3D.OPERATION_MODE_PAN);
			Debug.message("Operation mode = PAN");
		}
	}

	private class OperationRotAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			((PerspectiveViewJava3D)getFrame()).setOperationMode(PerspectiveViewJava3D.OPERATION_MODE_ROT);
			Debug.message("Operation mode = ROT");
		}
	}
	
	private class ResetViewpointAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false); 
			PopupMenu popupMenu = new PopupMenu();
			Component comp = (Component)e.getSource();
			Dimension size = comp.getSize();
			popupMenu.show(comp, size.width/2, size.height/2);
			getFrame().repaint();
		}
		
		public void repaintFrame() {
			getFrame().repaint();
		}
		
		public class PopupMenu extends JPopupMenu {
			private String menuString[] = {
				"Start View",
				"XY Plane View",
				"XZ Plane View",
				"YZ Plane View",
			};
		
			public PopupMenu() {
				for (int n=0; n<menuString.length; n++) {
					JMenuItem menuItem = new JMenuItem(menuString[n]);
					menuItem.addActionListener(new PopupMenuAction());
					add(menuItem);
				}
			}
		
			private class PopupMenuAction extends AbstractAction {
  			 	public void actionPerformed(ActionEvent e) {
  	 				Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
					SceneGraph sg = getSceneGraph();
					for (int n=0; n<menuString.length; n++) {
						if (menuString[n].equals(e.getActionCommand()) == true) {
							switch (n) {
							case 0: sg.resetViewpoint(); break;
							case 1: sg.resetViewpointAlongZAxis(); break;
							case 2: sg.resetViewpointAlongYAxis(); break;
							case 3: sg.resetViewpointAlongXAxis(); break;
							}
							repaintFrame();
							break;
						}
					}
				}
			}
		}
	}

	private class HeadlightAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getSceneGraph().setHeadlightState(!getSceneGraph().isHeadlightOn());
			Debug.message("PerspectiveViewToolBar::HeadlightAction : headlight = " + getSceneGraph().isHeadlightOn());
		}
	}

	private class RenderingWireAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getSceneGraph().setRenderingMode(SceneGraph.RENDERINGMODE_LINE);
			Debug.message("Rendring mode = wire frame");
		}
	}

	private class RenderingShadeAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getSceneGraph().setRenderingMode(SceneGraph.RENDERINGMODE_FILL);
			Debug.message("Rendring mode = shading");
		}
	}

	private class ConfigAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			DialogPerspectiveView	pvDialog	= new DialogPerspectiveView(getFrame(), getWorld());
			
			if (pvDialog.doModal() == Dialog.OK_OPTION) {
				getSceneGraph().setRenderingMode(pvDialog.getRenderingStyle());
				getSceneGraph().setHeadlightState(pvDialog.isHeadlightOn());
				float speed = pvDialog.getNavigationSpeed();
				if (0 < speed)
					getSceneGraph().setNavigationSpeed(speed);
				else
					new MessageBeep();
			}			
		}
	}
}

