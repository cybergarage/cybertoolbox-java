/******************************************************************
*
*	Simple VRML Viewer for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	PerspectiveViewJava3D.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import vrml.*;
import vrml.node.*;
import vrml.j3d.*;

public class PerspectiveViewJava3D extends JFrame implements Constants, MouseListener, MouseMotionListener, Runnable {

	static final public int	OPERATION_MODE_PICK	=	0;
	static final public int	OPERATION_MODE_WALK	=	1;
	static final public int	OPERATION_MODE_PAN	=	2;
	static final public int	OPERATION_MODE_ROT	=	3;
	
	private World							mWorld					= null;					
	private SceneGraphJ3dObject		mSceneGraphObject		= null;
	private Thread							mThread					= null;
	private PerspectiveViewToolBar	mToolBar					= null;
	private int								mOperationMode			= OPERATION_MODE_WALK;
	
	public void setWorld(World world) {
		mWorld = world;
	}

	public World getWorld() {
		return mWorld;
	}

	public SceneGraph getSceneGraph() {
		return getWorld().getSceneGraph();
	}

	public SceneGraphJ3dObject getSceneGraphJ3dObject() {
		return mSceneGraphObject;
	}
		
	public PerspectiveViewToolBar getToolBar() {
		return mToolBar;
	}
	
	public PerspectiveViewJava3D(World world){
		super("");

		setWorld(world);

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		
		getContentPane().setLayout(new BorderLayout());
		//setLayout(new BorderLayout());

		mToolBar = new PerspectiveViewToolBar(this, world);
		getContentPane().add("North", mToolBar);
		//add("North", mToolBar);

		Canvas3D c = new Canvas3D(null);
		getContentPane().add("Center", c);
		//add("Center", c);

		c.addMouseListener(this);
		c.addMouseMotionListener(this);

		mSceneGraphObject = new SceneGraphJ3dObject(c, getSceneGraph());
		getSceneGraph().setObject(mSceneGraphObject);

		setSize(320,320);
		show();
		
		setOperationMode(OPERATION_MODE_WALK);
		
		startThread();
	}

	public void processWindowEvent(WindowEvent e) {
	}
		
	public void startThread() {
		if (mThread == null) {
			mThread = new Thread(this);
			mThread.start();
		}
		getSceneGraphJ3dObject().start(getSceneGraph());
	}

	public void stopThread() {
		if (mThread != null) {
			mThread.stop();
			mThread = null;
		}
		getSceneGraphJ3dObject().stop(getSceneGraph());
	}

	////////////////////////////////////////////////
	//	Operation Mode
	////////////////////////////////////////////////
	
	public void setOperationMode(int mode) {
		mOperationMode = mode;
	}

	public int getOperationMode() {
		return mOperationMode;
	}
		
	////////////////////////////////////////////////
	// Popup Menu
	////////////////////////////////////////////////
	
	public class PopupMenu extends JPopupMenu {
		private String menuString[] = {
			"Start View",
			"XY Plane View",
			"XZ Plane View",
			"YZ Plane View",
			"",
			"Wireframe",
			"Shading",
		};
		
		public PopupMenu() {
			for (int n=0; n<menuString.length; n++) {
				if (0 < menuString[n].length()) {
					JMenuItem menuItem = new JMenuItem(menuString[n]);
					menuItem.addActionListener(new PopupMenuAction());
					add(menuItem);
				}
				else
					addSeparator();
			}
		}
		
		private class PopupMenuAction extends AbstractAction {
  		 	public void actionPerformed(ActionEvent e) {
  	 			Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
				for (int n=0; n<menuString.length; n++) {
					if (menuString[n].equals(e.getActionCommand()) == true) {
						switch (n) {
						case 0:	getSceneGraph().resetViewpoint(); break;
						case 1:	getSceneGraph().resetViewpointAlongZAxis(); break;
						case 2:	getSceneGraph().resetViewpointAlongYAxis(); break;
						case 3:	getSceneGraph().resetViewpointAlongXAxis(); break;
						case 5: getSceneGraph().setRenderingMode(SceneGraph.RENDERINGMODE_LINE); break;
						case 6: getSceneGraph().setRenderingMode(SceneGraph.RENDERINGMODE_FILL); break;
						}
						repaint();
						break;
					}
				}
			}
		}
	}
	
	////////////////////////////////////////////////
	//	mouse
	////////////////////////////////////////////////

	private ShapeNode	mSelectedShapeNode	= null;
	
	private void setSelectedShapeNode(ShapeNode shapeNode) {
		mSelectedShapeNode = shapeNode;
	}

	private ShapeNode getSelectedShapeNode() {
		return mSelectedShapeNode;
	}
	
	private int	mStartMouseX = 0;
	private int	mStartMouseY = 0;
	private int	mMouseX = 0;
	private int	mMouseY = 0;
	private int	mMouseButton = 0;
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		Debug.message("PerspectiveViewJava3D::mousePressed : " + e.getModifiers());
		
		mMouseButton = e.getModifiers();
		
		if (e.getModifiers() == e.BUTTON1_MASK) {
			if (getOperationMode() == OPERATION_MODE_PICK) {
				ShapeNode shapeNode = getSceneGraphJ3dObject().pickShapeNode(getSceneGraph(), e.getX(), e.getY());
				if (shapeNode != null)
					getSceneGraph().shapePressed(shapeNode, e.getX(), e.getY());
				setSelectedShapeNode(shapeNode);
			}
		}

		if (e.getModifiers() == e.BUTTON3_MASK) {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false); 
			PopupMenu popupMenu = new PopupMenu();
			popupMenu.show(this, e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		Debug.message("PerspectiveViewJava3D::mouseReleased : " + e.getModifiers());
		
		if (e.getModifiers() == e.BUTTON1_MASK)
			mMouseButton = 0;
		
		if (e.getModifiers() == e.BUTTON1_MASK) {
			if (getOperationMode() == OPERATION_MODE_PICK) {
				ShapeNode shapeNode = getSelectedShapeNode();
				if (shapeNode != null)
					getSceneGraph().shapeReleased(shapeNode, e.getX(), e.getY());
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public int getMouseX() {
		return mMouseX;
	}

	public int getMouseY() {
		return mMouseY;
	}

	public int getMouseButton() {
		return mMouseButton;
	}

	////////////////////////////////////////////////
	//	Viewpoint
	////////////////////////////////////////////////

	public void updateViewpoint() {

		// get mouse infomations
		int		mx = getMouseX();
		int		my = getMouseY();
		int		mbutton = getMouseButton();
		
		if (mbutton != MouseEvent.BUTTON1_MASK) 
			return;

		float	width2 = (float)getWidth() / 2.0f;
		float	height2 = (float)getHeight() /2.0f;

		float	vector[]		= {0.0f, 0.0f, 0.0f};
		float	axisRot[]	= {0.0f, 0.0f, 0.0f};

		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;
			
		NavigationInfoNode navInfo = sg.getNavigationInfoNode();
		if (navInfo == null)
			navInfo = sg.getDefaultNavigationInfoNode();
			
		float speed = navInfo.getSpeed();
		 
		switch (getOperationMode()) {
		case OPERATION_MODE_WALK:
			vector[Z]	= ((float)my - height2) / height2 * 0.1f * speed;
			axisRot[Y]	= -((float)mx - width2) / width2 * 0.02f;
			break;			
		case OPERATION_MODE_PAN:
			vector[X]	= ((float)mx - width2) / width2 * 0.1f * speed;
			vector[Y]	= -((float)my - height2) / height2 * 0.1f * speed;
			break;			
		case OPERATION_MODE_ROT:
			axisRot[X]	= -((float)my - height2) / height2 * 0.02f;
			axisRot[Z]	= -(((float)mx - width2) / width2 * 0.02f);
			break;			
		}
					
		ViewpointNode viewNode = sg.getViewpointNode();
		if (viewNode == null)
			viewNode = sg.getDefaultViewpointNode();
		
		float viewOrienataion[] = new float[4];
		for (int n=0; n<3; n++) {
			if (axisRot[n] != 0.0f) {
				viewOrienataion[0] = viewOrienataion[1] = viewOrienataion[2] = 0.0f;
				viewOrienataion[n] = 1.0f;
				viewOrienataion[3] = axisRot[n];
				viewNode.addOrientation(viewOrienataion);
			}
		}	

		float viewFrame[][] = new float[3][3];
		viewNode.getFrame(viewFrame);
		for (int n=X; n<=Z; n++) {
			viewFrame[n][X] *= vector[n];
			viewFrame[n][Y] *= vector[n];
			viewFrame[n][Z] *= vector[n];
			viewNode.addPosition(viewFrame[n]);
		}
	}

	////////////////////////////////////////////////
	//	runnable
	////////////////////////////////////////////////

	public void run() {
		while (true) {
			updateViewpoint();
			repaint();
			try {
				mThread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
}
