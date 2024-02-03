/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTreeToolBar.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import vrml.*;
import vrml.node.*;
import vrml.route.*;

public class WorldTreeToolBar extends ToolBar implements WorldConstants, EventTypeConstants {

	private WorldTree mWorldTree = null;
	
	private WorldTree getWorldTree() {
		return mWorldTree;
	}
	
	private World getWorld() {
		return mWorldTree.getWorld();
	}

	private SceneGraph getSceneGraph() {
		return getWorld().getSceneGraph();
	}
		
	public WorldTreeToolBar (WorldTree worldTree) {
		mWorldTree = worldTree;
	
		String dir = WORLD_IMAGE_TOOLBAR_WORLDTREE_DIRECTORY;
		String sep = System.getProperty("file.separator");
		
		
		addToolBarButton("New World", dir + sep + "simulation_new.gif", new NewSceneGraphAction());
		addToolBarButton("Load World", dir + sep + "simulation_load.gif", new LoadSceneGraphAction());
		JButton button = addToolBarButton("Save World", dir + sep + "simulation_save.gif", new SaveSceneGraphAction());
		//button.setEnabled(false);
		
		addSeparator();
		addToolBarButton("Go Simulation", dir + sep + "simulation_go.gif", new StartSimulationAction());
		addToolBarButton("Stop Simulation", dir + sep + "simulation_stop.gif", new StopSimulationAction());
		addSeparator();
		addToolBarButton("New Node", dir + sep + "node_new.gif", new NodeNewAction());
		addToolBarButton("New Route", dir + sep + "route_new.gif", new RouteNewAction());
		addToolBarButton("New Event", dir + sep + "event_new.gif", new EventNewAction());
		addToolBarButton("New Diagram", dir + sep + "diagram_new.gif", new DiagramNewAction());
		addSeparator();
		addToolBarButton("About", dir + sep + "about.gif", new AboutAction());
	}
		
	private class NewSceneGraphAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getWorld().stopSimulation();
			
			int result = JOptionPane.showConfirmDialog(getWorldTree(), "Are you sure you want to clear all scenegraph objects ?", "", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				getWorld().stopPerspectiveViewThread();				
				getWorldTree().clear();
				getWorld().clearSceneGraph();
				getWorldTree().reset();
				getWorld().startPerspectiveViewThread();				
			}
		}
    }

	public class VRMLFileFilter extends FileFilter {
		final static String wrl = "wrl";
		public boolean accept(File f) {
			if(f.isDirectory())
				return true;
			String s = f.getName();
			int i = s.lastIndexOf('.');
			if(i > 0 &&  i < s.length() - 1) {
				String extension = s.substring(i+1).toLowerCase();
				if (wrl.equals(extension) == true) 
					return true;
				else
					return false;
			}
			return false;
		}
		public String getDescription() {
			return "VRML Files (*.wrl)";
		}
	}

	private class LoadSceneGraphAction extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			getWorld().stopSimulation();
			
			String userDir = System.getProperty("user.dir");
			Debug.message("currentDirectoryPath = " + userDir);
			JFileChooser filechooser = new JFileChooser(new File(userDir));
			filechooser.addChoosableFileFilter(new VRMLFileFilter());
			if(filechooser.showOpenDialog(getWorldTree()) == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				if (file != null) {
					if (file.isDirectory() == false) {
						getWorld().stopPerspectiveViewThread();				
						getWorld().addSceneGraph(file);
						getWorldTree().reset();
						getWorld().startPerspectiveViewThread();				
					}
				}
			}
		}
	}

    private class SaveSceneGraphAction extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
			getWorld().stopSimulation();
			
			String userDir = System.getProperty("user.dir");
			Debug.message("currentDirectoryPath = " + userDir);
			JFileChooser filechooser = new JFileChooser(new File(userDir));
			filechooser.addChoosableFileFilter(new VRMLFileFilter());
			if(filechooser.showSaveDialog(getWorldTree()) == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				if (file != null) {
					if (file.isDirectory() == false) {
						//getWorld().getSceneGraph().print();
						getWorld().stopPerspectiveViewThread();				
						getWorld().saveSceneGraph(file);
						getWorld().startPerspectiveViewThread();				
					}
				}
			}
		}
	}

    private class StartSimulationAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
			getWorld().startSimulation();
		}
	}

    private class StopSimulationAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
			getWorld().stopSimulation();
		}
	}

    private class NodeNewAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
			getWorld().stopPerspectiveViewThread();				
			getWorld().stopSimulation();
			
			WorldTreeNode treeNode = getWorldTree().getSelectedTreeNode();
			Node parentNode = getWorldTree().findSceneGraphNode(treeNode);
			if (parentNode == null) {
				new MessageBeep();
				return;
			}

			DialogWorldNewNode dialog = new DialogWorldNewNode(getWorldTree(), parentNode);
			if (dialog.doModal() == Dialog.OK_OPTION) { 
				Node childNode = dialog.getSelectedNode();
				if (childNode != null)
					addNewNode(parentNode, childNode);
			}
			
			getWorld().startPerspectiveViewThread();				
		}
		
		private boolean addNewNode(Node parentNode, Node childNode) {
			if (parentNode == getSceneGraph().getRootNode())
				getSceneGraph().addNode(childNode);
			else
				parentNode.addChildNode(childNode);
			getWorldTree().addSceneGraphNodeTreeNode(childNode, parentNode);
			getSceneGraph().initialize();
			return true;
		}
	}

	private class RouteNewAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getWorld().stopPerspectiveViewThread();				
			getWorld().stopSimulation();
			
			DialogRoute dialog = new DialogRoute(getWorldTree(), getWorld());
			if (dialog.doModal() == Dialog.OK_OPTION) { 
				Route route = dialog.getRoute();
				if (route != null) {
					if (getSceneGraph().addRoute(route) != null)
						getWorldTree().addRouteTreeNode(route);
					else
						new MessageBeep();
				}
			}
			
			getWorld().startPerspectiveViewThread();				
		}
	}

	private class EventNewAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			getWorld().stopPerspectiveViewThread();				
			getWorld().stopSimulation();
			
			DialogWorldNewEvent dialog = new DialogWorldNewEvent(getWorldTree(), getWorld());
			if (dialog.doModal() == Dialog.OK_OPTION) {
				if (addNewEvent(dialog) == false)
					new MessageBeep();
			}
			
			getWorld().startPerspectiveViewThread();				
		}
		
		private boolean addNewEvent(DialogWorldNewEvent dialog) {
			Debug.message("EventNewAction::addNewEvent");
			int					eventType	= dialog.getSelectedEventType();
			DialogEventPanel	eventPanel	= dialog.getEventPanel(eventType);

			Debug.message("\teventType  = " + eventType);			
			Debug.message("\teventPanel = " + eventPanel);			
			
			EventType	newEventType	= null;
			String		newEventOption	= null;
			
			switch (eventType) {
			case EVENTTYPE_CLOCK: // CLOCK
				newEventType = getWorld().getEventType(EVENTTYPE_CLOCK);
				break;
			case EVENTTYPE_TIMER: // TIMER
				newEventType = getWorld().getEventType(EVENTTYPE_TIMER);
				break;
			case EVENTTYPE_PICKUP: // PICKUP
				newEventType = getWorld().getEventType(EVENTTYPE_PICKUP);
				break;
			case EVENTTYPE_DRAG: // Drag
				newEventType = getWorld().getEventType(EVENTTYPE_DRAG);
				break;
			case EVENTTYPE_AREA: // Area
				newEventType = getWorld().getEventType(EVENTTYPE_AREA);
				break;
			}
			
			newEventOption = eventPanel.getOptionString();
			
			if (newEventType == null || newEventOption == null) 
				return false;

			Event	newEvent = new Event(getWorld(), newEventType, newEventOption);
			
			if (getWorld().addEventNode(newEvent.getNode()) == false) 
				return false;
				
			getWorld().getWorldTree().addEventTreeNode(newEvent);
			
			return true;			
		}
	}

	private class DiagramNewAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			getWorld().stopPerspectiveViewThread();				
			getWorld().stopSimulation();
			
			DialogWorldNewDiagram dialog = new DialogWorldNewDiagram(getWorldTree(), getWorld());
			if (dialog.doModal() == Dialog.OK_OPTION) {
				int eventIndex = dialog.getEventIndex();
				String diagramName = dialog.getDiagramName();
				if (addNewDiagram(eventIndex, diagramName) == false) 
					new WarningMessageDialog(getWorldTree(), "The same diagram is already created !!");
			}
			
			getWorld().startPerspectiveViewThread();				
		}

		private boolean addNewDiagram(int eventIndex, String diagramName) {
			if (diagramName == null || diagramName.length() <= 0) {
				int nDiagram = 0;
				diagramName = null;
				while (diagramName == null) {
					diagramName = "diagram" + nDiagram;
					if (getWorld().getDiagramNode(diagramName) != null)
						diagramName = null;
					nDiagram++;
				}
			}

			Node eventNode = getWorld().getEventNode(eventIndex);
			TransformNode	transform = getWorld().addDiagram(diagramName, eventNode);
			if (eventNode !=null && transform != null) {
				Event	event = new Event(getWorld(), eventNode);
				Diagram	diagram = new Diagram(getWorld(), transform);
				getWorldTree().addDiagramTreeNode(event, diagram);
				getWorld().createDiagramFrame(transform);
			}
			else 
				return false;
							
			return true;
		}
	}

	private class AboutAction extends AbstractAction {
   	public void actionPerformed(ActionEvent e) {
			DialogWorldAbout dialog = new DialogWorldAbout(getWorldTree());
			dialog.doModal();
		}
	}
}
